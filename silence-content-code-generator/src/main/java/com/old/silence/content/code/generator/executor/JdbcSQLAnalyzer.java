package com.old.silence.content.code.generator.executor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.IndexInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.NameConverterUtils;
import com.old.silence.core.util.CollectionUtils;

/**
 * 基于JDBC的SQLAnalyzer实现，直接从数据库元数据读取表结构。
 *
 * <p>该实现连接到指定数据库，读取表结构、索引、注释等信息，
 * 并转换为统一的{@link TableInfo}模型，供后续代码生成使用。</p>
 *
 * @author moryzang
 */
public class JdbcSQLAnalyzer implements SQLAnalyzer {

    private final Connection connection;
    private final String databaseName;

    /**
     * 使用生成配置中的数据库连接信息初始化Analyzer。
     * 避免硬编码，所有连接参数从{@link DatabaseConfig}获取。
     */
    public JdbcSQLAnalyzer(DatabaseConfig config) throws SQLException {
        String url = config.getDbUrl();
        String username = config.getUsername();
        String password = config.getPassword();
        this.connection = DriverManager.getConnection(url, username, password);
        this.databaseName = connection.getCatalog();
    }


    @Override
    public TableInfo analyzeTable(String tableName) throws SQLException {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);

        // 先获取主键列表
        List<String> primaryKeys = getPrimaryKeys(tableName);
        tableInfo.setPrimaryKeys(primaryKeys);

        // 获取字段信息，并设置主键标志
        List<ColumnInfo> columns = getColumns(tableName);
        // 设置字段的主键标志
        for (ColumnInfo column : columns) {
            if (primaryKeys.contains(column.getOriginalName())) {
                column.setPrimaryKey(true);
            }
        }
        tableInfo.setColumnInfos(columns);

        tableInfo.setForeignKeys(List.of());

        var indexes = getIndexes(tableName);
        tableInfo.setIndexes(indexes);

        var indexColumnSet = CollectionUtils.transformToList(indexes, IndexInfo::getColumnNames)
                .stream().flatMap(Collection::stream).collect(Collectors.toSet());
        // 反过来设置字段是否为索引
        columns.forEach(column -> {
            if (indexColumnSet.contains(column.getOriginalName()) && !column.getPrimaryKey()) {
                column.setIndexColumn(true);
            }
        });

        tableInfo.setComment(getTableComment(tableName));
        tableInfo.setSchema(databaseName);

        return tableInfo;
    }

    @Override
    public Map<String, String> getTablesWithComments() throws SQLException {
        Map<String, String> tables = new LinkedHashMap<>();

        String sql = "SELECT TABLE_NAME, TABLE_COMMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = 'BASE TABLE' " +
                "ORDER BY TABLE_NAME";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, databaseName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    String comment = rs.getString("TABLE_COMMENT");
                    tables.put(tableName, comment);
                }
            }
        }

        return tables;
    }

    /**
     * 获取MySQL表注释
     */
    private String getTableComment(String tableName) throws SQLException {
        String sql = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, databaseName);
            stmt.setString(2, tableName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String comment = rs.getString("TABLE_COMMENT");
                    return filterMySQLComment(comment);
                }
            }
        }
        return null;
    }

    /**
     * 过滤MySQL注释（去掉表类型等无效信息）
     */
    private String filterMySQLComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            return null;
        }

        String trimmed = comment.trim();

        // 过滤掉MySQL的表类型注释
        if (trimmed.equalsIgnoreCase("InnoDB free: 0 kB") ||
                trimmed.toLowerCase().contains("innodb") ||
                trimmed.toLowerCase().contains("myisam") ||
                trimmed.equalsIgnoreCase("null")) {
            return null;
        }

        return trimmed;
    }

    /**
     * 获取表的索引信息
     */
    private List<IndexInfo> getIndexes(String tableName) throws SQLException {
        Map<String, IndexInfo> indexMap = new HashMap<>();
        DatabaseMetaData metaData = connection.getMetaData();

        try (ResultSet rs = metaData.getIndexInfo(null, null, tableName, false, true)) {
            while (rs.next()) {
                String indexName = rs.getString("INDEX_NAME");
                if (indexName == null) {
                    continue; // 跳过统计信息
                }

                short indexType = rs.getShort("TYPE");
                if (indexType == DatabaseMetaData.tableIndexStatistic) {
                    continue; // 跳过统计信息
                }

                boolean nonUnique = rs.getBoolean("NON_UNIQUE");
                String columnName = rs.getString("COLUMN_NAME");
                short ordinalPosition = rs.getShort("ORDINAL_POSITION");

                IndexInfo indexInfo = indexMap.computeIfAbsent(indexName,
                        k -> new IndexInfo(indexName, !nonUnique));

                // 确保列按正确顺序添加
                if (ordinalPosition == 1) {
                    indexInfo.getColumnNames().clear(); // 如果是第一个位置，清空重新添加
                }
                indexInfo.addColumnName(columnName);
                indexInfo.setIndexType(getIndexTypeName(indexType));
            }
        }

        return indexMap.values().stream().filter(indexInfo -> !"PRIMARY".equals(indexInfo.getIndexType()))
                .collect(Collectors.toList());
    }

    /**
     * 将索引类型代码转换为可读名称
     */
    private String getIndexTypeName(short indexType) {
        return switch (indexType) {
            case DatabaseMetaData.tableIndexStatistic -> "STATISTIC";
            case DatabaseMetaData.tableIndexClustered -> "CLUSTERED";
            case DatabaseMetaData.tableIndexHashed -> "HASHED";
            case DatabaseMetaData.tableIndexOther -> "OTHER";
            default -> "UNKNOWN";
        };
    }

    /**
     * 获取MySQL字段信息（包含注释）
     */
    private List<ColumnInfo> getColumns(String tableName) throws SQLException {
        List<ColumnInfo> columns = new ArrayList<>();

        // 使用INFORMATION_SCHEMA获取更详细的信息（包括注释）
        String sql = "SELECT " +
                "COLUMN_NAME, DATA_TYPE, COLUMN_TYPE, IS_NULLABLE, " +
                "COLUMN_DEFAULT, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, " +
                "NUMERIC_SCALE, EXTRA, COLUMN_COMMENT " +
                "FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? " +
                "ORDER BY ORDINAL_POSITION";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, databaseName);
            stmt.setString(2, tableName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setOriginalName(rs.getString("COLUMN_NAME"));
                    columnInfo.setType(rs.getString("DATA_TYPE"));
                    columnInfo.setLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH") >= 65535 ? 65535 : rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
                    columnInfo.setNullable("YES".equals(rs.getString("IS_NULLABLE")));
                    // 设置required字段（与nullable相反）
                    columnInfo.setRequired(!columnInfo.getNullable());

                    // 判断是否自增
                    String extra = rs.getString("EXTRA");
                    columnInfo.setAutoIncrement(extra != null && extra.toLowerCase().contains("auto_increment"));

                    // 获取注释
                    String comment = rs.getString("COLUMN_COMMENT");
                    columnInfo.setComment(comment != null && !comment.trim().isEmpty() ? comment.trim() : null);

                    // 获取默认值
                    String defaultValue = rs.getString("COLUMN_DEFAULT");
                    if (defaultValue != null && !defaultValue.trim().isEmpty()) {
                        columnInfo.setDefaultValue(defaultValue);
                    }


                    // 先将字段名转换为驼峰格式
                    String fieldName = NameConverterUtils.toCamelCase(columnInfo.getOriginalName(), false);
                    
                    // 如果是tinyint类型，JAVA type 按枚举处理（首字母大写）
                    if ("tinyint".equals(columnInfo.getType())) {
                        columnInfo.setEnum(true);
                        var enumName = StringUtils.capitalize(fieldName);
                        var className = NameConverterUtils.toCamelCase(tableName, true);

                        if (!enumName.startsWith(className)) {
                            enumName = className + enumName;
                        }
                        columnInfo.setFieldType(enumName);
                    } else {

                        columnInfo.setFieldType();
                    }
                    columnInfo.setFieldName(fieldName);


                    columns.add(columnInfo);
                }
            }
        }
        return columns;
    }

    private List<String> getPrimaryKeys(String tableName) throws SQLException {
        List<String> primaryKeys = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();

        try (ResultSet rs = metaData.getPrimaryKeys(null, null, tableName)) {
            while (rs.next()) {
                primaryKeys.add(rs.getString("COLUMN_NAME"));
            }
        }
        return primaryKeys;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
