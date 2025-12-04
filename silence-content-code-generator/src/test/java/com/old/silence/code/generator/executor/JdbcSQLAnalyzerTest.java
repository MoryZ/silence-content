package com.old.silence.code.generator.executor;

import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.model.TableInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("JdbcSQLAnalyzer 数据库元数据解析测试")
class JdbcSQLAnalyzerTest {

    private MockedStatic<java.sql.DriverManager> driverManagerMock;
    private Connection connection;
    private DatabaseMetaData metaData;
    private JdbcSQLAnalyzer analyzer;

    @BeforeEach
    void setUp() throws Exception {
        connection = Mockito.mock(Connection.class);
        metaData = Mockito.mock(DatabaseMetaData.class);

        driverManagerMock = Mockito.mockStatic(java.sql.DriverManager.class);
        driverManagerMock.when(() -> java.sql.DriverManager.getConnection(anyString(), anyString(), anyString()))
            .thenReturn(connection);

        Mockito.when(connection.getCatalog()).thenReturn("test_db");
        Mockito.when(connection.getMetaData()).thenReturn(metaData);

        analyzer = new JdbcSQLAnalyzer();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (analyzer != null) {
            analyzer.close();
        }
        driverManagerMock.close();
    }

    @Test
    @DisplayName("获取所有表及注释")
    void shouldGetTablesWithComments() throws Exception {
        PreparedStatement tablesStmt = Mockito.mock(PreparedStatement.class);
        ResultSet tablesRs = Mockito.mock(ResultSet.class);

        Mockito.when(connection.prepareStatement(Mockito.startsWith("SELECT TABLE_NAME")))
            .thenReturn(tablesStmt);
        Mockito.when(tablesStmt.executeQuery()).thenReturn(tablesRs);
        Mockito.when(tablesRs.next()).thenReturn(true, false);
        Mockito.when(tablesRs.getString("TABLE_NAME")).thenReturn("content");
        Mockito.when(tablesRs.getString("TABLE_COMMENT")).thenReturn("内容表");

        var tables = analyzer.getTablesWithComments();

        assertEquals(1, tables.size());
        assertEquals("内容表", tables.get("content"));
        Mockito.verify(tablesStmt).setString(1, "test_db");
    }

    @Test
    @DisplayName("分析表结构")
    void shouldAnalyzeTable() throws Exception {
        mockPrimaryKeys("content", "id");
        mockColumns();
        mockTableComment("content", "内容表");
        mockIndexes("content");

        TableInfo tableInfo = analyzer.analyzeTable("content");

        assertNotNull(tableInfo);
        assertEquals("content", tableInfo.getTableName());
        assertEquals("内容表", tableInfo.getComment());
        assertEquals("test_db", tableInfo.getSchema());
        assertEquals(2, tableInfo.getColumnInfos().size());
        assertTrue(tableInfo.getPrimaryKeys().contains("id"));
        assertTrue(Boolean.TRUE.equals(tableInfo.getColumnInfos().get(0).getPrimaryKey()));
        assertEquals(1, tableInfo.getIndexes().size());
    }

    private void mockPrimaryKeys(String tableName, String... pks) throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(metaData.getPrimaryKeys(null, null, tableName)).thenReturn(rs);

        AtomicInteger counter = new AtomicInteger();
        Mockito.when(rs.next()).thenAnswer(invocation -> counter.getAndIncrement() < pks.length);
        Mockito.when(rs.getString("COLUMN_NAME")).thenAnswer(invocation -> {
            int index = counter.get() - 1;
            return index >= 0 && index < pks.length ? pks[index] : null;
        });
    }

    private void mockColumns() throws SQLException {
        PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(connection.prepareStatement(Mockito.contains("FROM INFORMATION_SCHEMA.COLUMNS")))
            .thenReturn(stmt);
        Mockito.when(stmt.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getString("COLUMN_NAME")).thenReturn("id", "title");
        Mockito.when(rs.getString("DATA_TYPE")).thenReturn("bigint", "varchar");
        Mockito.when(rs.getInt("CHARACTER_MAXIMUM_LENGTH")).thenReturn(20, 100);
        Mockito.when(rs.getString("IS_NULLABLE")).thenReturn("NO", "YES");
        Mockito.when(rs.getString("COLUMN_DEFAULT")).thenReturn(null, "默认标题");
        Mockito.when(rs.getString("EXTRA")).thenReturn("auto_increment", "");
        Mockito.when(rs.getString("COLUMN_COMMENT")).thenReturn("内容ID", "标题");
    }

    private void mockTableComment(String tableName, String comment) throws SQLException {
        PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(connection.prepareStatement(Mockito.contains("TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES")))
            .thenReturn(stmt);
        Mockito.when(stmt.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getString("TABLE_COMMENT")).thenReturn(comment);
    }

    private void mockIndexes(String tableName) throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(metaData.getIndexInfo(null, null, tableName, false, true)).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getString("INDEX_NAME")).thenReturn("idx_title");
        Mockito.when(rs.getShort("TYPE")).thenReturn(DatabaseMetaData.tableIndexOther);
        Mockito.when(rs.getBoolean("NON_UNIQUE")).thenReturn(true);
        Mockito.when(rs.getString("COLUMN_NAME")).thenReturn("title");
        Mockito.when(rs.getShort("ORDINAL_POSITION")).thenReturn((short) 1);
    }
}
