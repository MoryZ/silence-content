package com.old.silence.code.generator.analyzer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.ForeignKey;
import com.old.silence.code.generator.model.IndexInfo;
import com.old.silence.code.generator.model.TableInfo;

/**
 * @author moryzang
 */
public class SQLAnalyzer {


    private final Connection connection;
    private final String databaseName;

    public SQLAnalyzer(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
        this.databaseName = connection.getCatalog();
    }

    public TableInfo analyzeTable(String tableName) throws SQLException {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);
        tableInfo.setColumns(getColumns(tableName));
        tableInfo.setPrimaryKeys(getPrimaryKeys(tableName));
        tableInfo.setForeignKeys(List.of());
        tableInfo.setIndexes(getIndexes(tableName));

        tableInfo.setComment(getTableComment(tableName));
        tableInfo.setSchema(databaseName);

        return tableInfo;
    }

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
                    ColumnInfo column = new ColumnInfo();
                    column.setName(rs.getString("COLUMN_NAME"));
                    column.setType(rs.getString("DATA_TYPE"));
                    column.setLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
                    column.setNullable("YES".equals(rs.getString("IS_NULLABLE")));

                    // 判断是否自增
                    String extra = rs.getString("EXTRA");
                    column.setAutoIncrement(extra != null && extra.toLowerCase().contains("auto_increment"));

                    // 获取注释
                    String comment = rs.getString("COLUMN_COMMENT");
                    column.setComment(comment != null && !comment.trim().isEmpty() ? comment.trim() : null);

                    columns.add(column);
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

    /**
     * 关闭连接
     */
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
