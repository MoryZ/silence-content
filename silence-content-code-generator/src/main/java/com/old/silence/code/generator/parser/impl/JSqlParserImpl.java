package com.old.silence.code.generator.parser.impl;

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

import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.ForeignKey;
import com.old.silence.code.generator.model.IndexInfo;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.parser.SQLParser;
import com.old.silence.code.generator.util.NameConverterUtils;

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
            
            if (statement instanceof CreateTable) {
                CreateTable createTable = (CreateTable) statement;
                return convertToTableInfo(createTable);
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
            
            if (statement instanceof Alter) {
                Alter alter = (Alter) statement;
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
    private TableInfo convertToTableInfo(CreateTable createTable) {
        TableInfo tableInfo = new TableInfo();
        
        // 设置表名
        String tableName = createTable.getTable().getName();
        tableInfo.setTableName(tableName);
        
        // 设置schema
        if (createTable.getTable().getSchemaName() != null) {
            tableInfo.setSchema(createTable.getTable().getSchemaName());
        }
        
        // 设置表注释（从COMMENT中提取）
        String tableComment = extractTableComment(createTable);
        tableInfo.setComment(tableComment);
        
        // 解析列定义
        List<ColumnInfo> columnInfos = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();
        
        if (createTable.getColumnDefinitions() != null) {
            for (ColumnDefinition colDef : createTable.getColumnDefinitions()) {
                ColumnInfo columnInfo = convertColumnDefinition(colDef);
                columnInfos.add(columnInfo);
                
                // 检查是否为主键
                if (colDef.getColumnSpecs() != null) {
                    for (String spec : colDef.getColumnSpecs()) {
                        if (spec.toUpperCase().contains("PRIMARY KEY")) {
                            primaryKeys.add(colDef.getColumnName());
                            columnInfo.setPrimaryKey(true);
                        }
                    }
                }
            }
        }
        
        tableInfo.setColumnInfos(columnInfos);
        tableInfo.setPrimaryKeys(primaryKeys);
        
        // 解析索引
        List<IndexInfo> indexes = new ArrayList<>();
        if (createTable.getIndexes() != null) {
            for (Index index : createTable.getIndexes()) {
                IndexInfo indexInfo = convertIndex(index);
                indexes.add(indexInfo);
            }
        }
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
        
        // 设置列名
        String columnName = colDef.getColumnName();
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
        if (colDef.getColumnSpecs() != null) {
            boolean nullable = true;
            boolean autoIncrement = false;
            String comment = null;
            
            for (String spec : colDef.getColumnSpecs()) {
                String upperSpec = spec.toUpperCase();
                
                if (upperSpec.contains("NOT NULL")) {
                    nullable = false;
                }
                
                if (upperSpec.contains("AUTO_INCREMENT") || upperSpec.contains("AUTOINCREMENT")) {
                    autoIncrement = true;
                }
                
                // 提取注释 COMMENT '注释内容'
                if (upperSpec.startsWith("COMMENT")) {
                    comment = extractComment(spec);
                }
                
                // 提取默认值
                if (upperSpec.startsWith("DEFAULT")) {
                    String defaultValue = extractDefaultValue(spec);
                    columnInfo.setDefaultValue(defaultValue);
                }
            }
            
            columnInfo.setNullable(nullable);
            columnInfo.setRequired(!nullable);
            columnInfo.setAutoIncrement(autoIncrement);
            columnInfo.setComment(comment);
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
     * 提取表注释
     */
    private String extractTableComment(CreateTable createTable) {
        // JSqlParser可能不支持直接提取表注释
        // 这里可以从原始SQL中提取，或者返回null
        // 实际使用中可以通过正则表达式从原始SQL中提取
        return null;
    }

    /**
     * 从列规格中提取注释
     */
    private String extractComment(String spec) {
        // 匹配 COMMENT '注释内容' 或 COMMENT "注释内容"
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
        // 匹配 DEFAULT '值' 或 DEFAULT 值
        Pattern pattern = Pattern.compile("(?i)DEFAULT\\s+(?:['\"](.*?)['\"]|(\\S+))");
        Matcher matcher = pattern.matcher(spec);
        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        }
        return null;
    }
}

