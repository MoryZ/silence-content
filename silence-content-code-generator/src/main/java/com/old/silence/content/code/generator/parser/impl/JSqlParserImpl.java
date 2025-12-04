package com.old.silence.content.code.generator.parser.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.ForeignKey;
import com.old.silence.content.code.generator.model.IndexInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.parser.SQLParser;
import com.old.silence.content.code.generator.util.NameConverterUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于JSqlParser的SQL解析器实现
 *
 * @author moryzang
 */
@Service
public class JSqlParserImpl implements SQLParser {

    private static final Logger log = LoggerFactory.getLogger(JSqlParserImpl.class);

    // 匹配CREATE TABLE语句的正则表达式
    private static final Pattern CREATE_TABLE_PATTERN = Pattern.compile(
            "(?i)CREATE\\s+TABLE\\s+(?:IF\\s+NOT\\s+EXISTS\\s+)?(?:`?([\\w]+)`?\\.)?`?([\\w]+)`?",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    public TableInfo parseCreateTable(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            
            if (statement instanceof CreateTable createTable) {
                return convertToTableInfo(createTable, sql);
            } else {
                throw new IllegalArgumentException("SQL语句不是CREATE TABLE语句: " + sql);
            }
        } catch (JSQLParserException e) {
            log.error("解析CREATE TABLE语句失败: {}", e.getMessage(), e);
            throw new IllegalArgumentException("无法解析SQL语句: " + e.getMessage(), e);
        }
    }

    @Override
    public TableInfo parseAlterTable(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            
            if (statement instanceof Alter alter) {
                // ALTER TABLE主要用于修改表结构，这里返回增量信息
                // 实际使用中可能需要与现有表信息合并
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(alter.getTable().getName());
                
                // 解析ALTER语句中的各种操作
                for (AlterExpression expr : alter.getAlterExpressions()) {
                    // 可以根据需要处理ADD COLUMN、MODIFY COLUMN等
                    log.debug("ALTER表达式类型: {}", expr.getOperation());
                }
                
                return tableInfo;
            } else {
                throw new IllegalArgumentException("SQL语句不是ALTER TABLE语句: " + sql);
            }
        } catch (JSQLParserException e) {
            log.error("解析ALTER TABLE语句失败: {}", e.getMessage(), e);
            throw new IllegalArgumentException("无法解析SQL语句: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TableInfo> parseFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            String content = Files.readString(path);
            
            // 按分号分割SQL语句
            String[] statements = content.split(";");
            List<TableInfo> tableInfos = new ArrayList<>();
            
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                
                try {
                    if (trimmed.toUpperCase().startsWith("CREATE TABLE")) {
                        TableInfo tableInfo = parseCreateTable(trimmed);
                        tableInfos.add(tableInfo);
                    }
                } catch (Exception e) {
                    log.warn("跳过无法解析的SQL语句: {}", e.getMessage());
                }
            }
            
            return tableInfos;
        } catch (IOException e) {
            log.error("读取SQL文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("读取SQL文件失败: " + filePath, e);
        }
    }

    @Override
    public boolean isValidSQL(String sql) {
        try {
            CCJSqlParserUtil.parse(sql);
            return true;
        } catch (JSQLParserException e) {
            return false;
        }
    }

    @Override
    public String extractTableName(String sql) {
        Matcher matcher = CREATE_TABLE_PATTERN.matcher(sql);
        if (matcher.find()) {
            // matcher.group(1) 是schema，matcher.group(2) 是表名
            return matcher.group(2) != null ? matcher.group(2) : matcher.group(1);
        }
        return null;
    }

    /**
     * 将JSqlParser的CreateTable对象转换为TableInfo
     */
    private TableInfo convertToTableInfo(CreateTable createTable, String originalSql) {
        TableInfo tableInfo = new TableInfo();
        
        // 设置表名（去除反引号）
        String tableName = createTable.getTable().getName();
        tableName = tableName.replaceAll("^`|`$", ""); // 去除首尾的反引号
        tableInfo.setTableName(tableName);
        
        // 设置schema
        if (createTable.getTable().getSchemaName() != null) {
            tableInfo.setSchema(createTable.getTable().getSchemaName());
        }
        
        // 设置表注释（从原始SQL中提取）
        String tableComment = extractTableComment(originalSql);
        tableInfo.setComment(tableComment);
        
        // 解析列定义
        List<ColumnInfo> columnInfos = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();
        
        if (createTable.getColumnDefinitions() != null) {
            for (ColumnDefinition colDef : createTable.getColumnDefinitions()) {
                ColumnInfo columnInfo = convertColumnDefinition(colDef);
                columnInfos.add(columnInfo);
                
                // 检查是否为主键（在列定义中）
                if (colDef.getColumnSpecs() != null) {
                    String allSpecs = String.join(" ", colDef.getColumnSpecs());
                    if (allSpecs.toUpperCase().contains("PRIMARY KEY")) {
                        String colName = colDef.getColumnName().replaceAll("^`|`$", "");
                        if (!primaryKeys.contains(colName)) {
                            primaryKeys.add(colName);
                        }
                        columnInfo.setPrimaryKey(true);
                    }
                }
            }
        }
        
        // 解析索引，特别处理PRIMARY KEY索引
        List<IndexInfo> indexes = new ArrayList<>();
        if (createTable.getIndexes() != null) {
            for (Index index : createTable.getIndexes()) {
                // 检查是否是PRIMARY KEY索引
                // JSqlParser中，PRIMARY KEY可能通过getType()返回"PRIMARY KEY"或通过getName()返回"PRIMARY"
                boolean isPrimaryKey = false;
                if (index.getType() != null && index.getType().toUpperCase().contains("PRIMARY")) {
                    isPrimaryKey = true;
                } else if (index.getName() != null && "PRIMARY".equalsIgnoreCase(index.getName())) {
                    isPrimaryKey = true;
                }
                
                if (isPrimaryKey) {
                    // 处理PRIMARY KEY (col1, col2) 这种形式
                    if (index.getColumns() != null) {
                        for (net.sf.jsqlparser.statement.create.table.Index.ColumnParams columnParams : index.getColumns()) {
                            if (columnParams.getColumnName() != null) {
                                String colName = columnParams.getColumnName().replaceAll("^`|`$", "");
                                if (!primaryKeys.contains(colName)) {
                                    primaryKeys.add(colName);
                                }
                                // 更新对应列的primaryKey标志
                                columnInfos.stream()
                                    .filter(col -> col.getOriginalName() != null && col.getOriginalName().equals(colName))
                                    .forEach(col -> col.setPrimaryKey(true));
                            }
                        }
                    }
                } else {
                    // 普通索引
                    IndexInfo indexInfo = convertIndex(index);
                    indexes.add(indexInfo);
                }
            }
        }
        
        tableInfo.setColumnInfos(columnInfos);
        tableInfo.setPrimaryKeys(primaryKeys);
        tableInfo.setIndexes(indexes);
        
        // 解析外键
        List<ForeignKey> foreignKeys = new ArrayList<>();
        if (createTable.getIndexes() != null) {
            for (Index index : createTable.getIndexes()) {
                if (index instanceof ForeignKeyIndex) {
                    ForeignKeyIndex fkIndex = (ForeignKeyIndex) index;
                    ForeignKey foreignKey = convertForeignKey(fkIndex);
                    foreignKeys.add(foreignKey);
                }
            }
        }
        tableInfo.setForeignKeys(foreignKeys);
        
        return tableInfo;
    }

    /**
     * 转换列定义
     */
    private ColumnInfo convertColumnDefinition(ColumnDefinition colDef) {
        ColumnInfo columnInfo = new ColumnInfo();
        
        // 设置列名（去除反引号）
        String columnName = colDef.getColumnName();
        columnName = columnName != null ? columnName.replaceAll("^`|`$", "") : null;
        columnInfo.setOriginalName(columnName);
        
        // 设置字段名（驼峰命名）
        columnInfo.setFieldName(NameConverterUtils.toCamelCase(columnName, false));
        
        // 解析数据类型
        String dataType = colDef.getColDataType().getDataType();
        columnInfo.setType(dataType);
        
        // 解析长度
        if (colDef.getColDataType().getArgumentsStringList() != null 
                && !colDef.getColDataType().getArgumentsStringList().isEmpty()) {
            try {
                String lengthStr = colDef.getColDataType().getArgumentsStringList().get(0);
                columnInfo.setLength(Integer.parseInt(lengthStr));
            } catch (NumberFormatException e) {
                // 忽略长度解析错误
            }
        }
        
        // 解析列规格（NULL/NOT NULL, AUTO_INCREMENT等）
        // JSqlParser的getColumnSpecs()可能返回null，需要从原始SQL中解析
        boolean nullable = true;
        boolean autoIncrement = false;
        String comment = null;
        String defaultValue = null;
        
        if (colDef.getColumnSpecs() != null) {
            // 将所有规格合并成一个字符串进行分析
            String allSpecs = String.join(" ", colDef.getColumnSpecs());
            String upperSpecs = allSpecs.toUpperCase();
            
            // 检查NOT NULL
            if (upperSpecs.contains("NOT NULL")) {
                nullable = false;
            } else if (upperSpecs.contains("NULL")) {
                nullable = true;
            }
            
            // 检查AUTO_INCREMENT
            if (upperSpecs.contains("AUTO_INCREMENT") || upperSpecs.contains("AUTOINCREMENT")) {
                autoIncrement = true;
            }
            
            // 提取注释 - 从合并的规格字符串中提取
            comment = extractComment(allSpecs);
            
            // 提取默认值
            defaultValue = extractDefaultValue(allSpecs);
        }
        
        columnInfo.setNullable(nullable);
        columnInfo.setRequired(!nullable);
        columnInfo.setAutoIncrement(autoIncrement);
        columnInfo.setComment(comment);
        if (defaultValue != null) {
            columnInfo.setDefaultValue(defaultValue);
        }
        
        return columnInfo;
    }

    /**
     * 转换索引
     */
    private IndexInfo convertIndex(Index index) {
        IndexInfo indexInfo = new IndexInfo();
        
        if (index.getName() != null) {
            indexInfo.setIndexName(index.getName());
        }
        
        // 判断是否为唯一索引
        boolean isUnique = index.getType() != null && 
                          (index.getType().toUpperCase().contains("UNIQUE") || 
                           index.getType().toUpperCase().contains("PRIMARY"));
        indexInfo.setUnique(isUnique);
        
        // 设置索引类型
        if (index.getType() != null) {
            indexInfo.setIndexType(index.getType());
        }
        
        // 设置索引列
        if (index.getColumns() != null) {
            for (net.sf.jsqlparser.statement.create.table.Index.ColumnParams columnParams : index.getColumns()) {
                if (columnParams.getColumnName() != null) {
                    indexInfo.addColumnName(columnParams.getColumnName());
                }
            }
        }
        
        return indexInfo;
    }

    /**
     * 转换外键
     */
    private ForeignKey convertForeignKey(ForeignKeyIndex fkIndex) {
        ForeignKey foreignKey = new ForeignKey();
        
        if (fkIndex.getName() != null) {
            foreignKey.setConstraintName(fkIndex.getName());
        }
        
        // 设置外键列
        if (fkIndex.getColumns() != null && !fkIndex.getColumns().isEmpty()) {
            net.sf.jsqlparser.statement.create.table.Index.ColumnParams firstColumn = fkIndex.getColumns().get(0);
            if (firstColumn.getColumnName() != null) {
                foreignKey.setColumnName(firstColumn.getColumnName());
            }
        }
        
        // 设置引用表和列
        // 注意：JSqlParser对外键的支持可能有限，这里使用反射或简化处理
        // 外键信息通常存储在ForeignKeyIndex的特定属性中
        try {
            // 尝试通过反射获取引用表信息（如果API支持）
            java.lang.reflect.Method getTableMethod = fkIndex.getClass().getMethod("getTable");
            Object tableObj = getTableMethod.invoke(fkIndex);
            if (tableObj != null) {
                java.lang.reflect.Method getNameMethod = tableObj.getClass().getMethod("getName");
                String tableName = (String) getNameMethod.invoke(tableObj);
                foreignKey.setReferencedTable(tableName);
            }
        } catch (Exception e) {
            // 如果反射失败，说明API不支持，暂时跳过
            log.debug("无法获取外键引用表信息: {}", e.getMessage());
        }
        
        return foreignKey;
    }

    /**
     * 从原始SQL中提取表注释
     * 表注释在右括号之后，ENGINE之前
     */
    private String extractTableComment(String sql) {
        if (sql == null || sql.isEmpty()) {
            return null;
        }
        
        // 找到最后一个右括号的位置（表定义结束）
        int lastBracket = sql.lastIndexOf(')');
        if (lastBracket < 0) {
            return null;
        }
        
        // 只在这之后查找COMMENT
        String afterBracket = sql.substring(lastBracket);
        
        // 匹配 COMMENT = '注释内容' 或 COMMENT '注释内容'
        // 但要确保不在列定义中（列定义中的COMMENT在右括号之前）
        Pattern pattern = Pattern.compile("(?i)COMMENT\\s*=\\s*['\"](.*?)['\"]|COMMENT\\s+['\"](.*?)['\"]");
        Matcher matcher = pattern.matcher(afterBracket);
        if (matcher.find()) {
            // group(1) 匹配 COMMENT = '...' 形式
            // group(2) 匹配 COMMENT '...' 形式
            String comment = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            // 确保注释不在引号内的其他位置（简单检查：注释应该比较短，且不在列定义中）
            if (comment != null && comment.length() < 200) {
                return comment;
            }
        }
        
        return null;
    }

    /**
     * 从列规格中提取注释
     */
    private String extractComment(String spec) {
        if (spec == null || spec.isEmpty()) {
            return null;
        }
        // 匹配 COMMENT '注释内容' 或 COMMENT "注释内容"
        // 使用非贪婪匹配，但要注意引号内的转义
        Pattern pattern = Pattern.compile("(?i)COMMENT\\s+['\"](.*?)['\"]");
        Matcher matcher = pattern.matcher(spec);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 从列规格中提取默认值
     */
    private String extractDefaultValue(String spec) {
        if (spec == null || spec.isEmpty()) {
            return null;
        }
        // 匹配 DEFAULT '值' 或 DEFAULT 值
        // 注意：DEFAULT可能后面跟着函数调用如 CURRENT_TIMESTAMP(3)，需要特殊处理
        Pattern pattern = Pattern.compile("(?i)DEFAULT\\s+(?:['\"](.*?)['\"]|(CURRENT_TIMESTAMP(?:\\([^)]*\\))?|\\S+))");
        Matcher matcher = pattern.matcher(spec);
        if (matcher.find()) {
            String value = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            // 如果匹配到的是函数调用，保留完整形式
            if (value != null && value.toUpperCase().startsWith("CURRENT_TIMESTAMP")) {
                return value;
            }
            return value;
        }
        return null;
    }
}

