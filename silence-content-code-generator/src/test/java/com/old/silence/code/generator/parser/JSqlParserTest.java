package com.old.silence.code.generator.parser;

import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.parser.SQLParser;
import com.old.silence.content.code.generator.parser.impl.JSqlParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JSqlParser SQL解析测试")
class JSqlParserTest {

    private SQLParser sqlParser;

    @BeforeEach
    void setUp() {
        this.sqlParser = new JSqlParserImpl();
    }

    @Test
    @DisplayName("解析表注释")
    void testTableCommentExtraction() {
        String sql = """
            CREATE TABLE `book_content_tag`  (
              `book_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
              `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
              `created_by` varchar(100) NOT NULL,
              PRIMARY KEY (`book_id`, `tag_id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书标签关联表' ROW_FORMAT = DYNAMIC;
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        assertNotNull(tableInfo, "解析结果不应为null");
        assertEquals("book_content_tag", tableInfo.getTableName());
        assertEquals("图书标签关联表", tableInfo.getComment());
        assertEquals(3, tableInfo.getColumnInfos().size());
    }

    @Test
    @DisplayName("解析复合主键")
    void testCompositePrimaryKeyParsing() {
        String sql = """
            CREATE TABLE `book_content_tag`  (
              `book_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
              `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
              `created_by` varchar(100) NOT NULL,
              PRIMARY KEY (`book_id`, `tag_id`) USING BTREE
            ) ENGINE = InnoDB COMMENT = '图书标签关联表';
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        assertNotNull(tableInfo.getPrimaryKeys());
        assertEquals(2, tableInfo.getPrimaryKeys().size());
        assertTrue(tableInfo.getPrimaryKeys().contains("book_id"));
        assertTrue(tableInfo.getPrimaryKeys().contains("tag_id"));

        ColumnInfo bookIdColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "book_id".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(bookIdColumn, "book_id列应该存在");
        assertTrue(Boolean.TRUE.equals(bookIdColumn.getPrimaryKey()), "book_id应标记为主键");
    }

    @Test
    @DisplayName("解析列定义中的单列主键")
    void testSinglePrimaryKeyParsing() {
        String sql = """
            CREATE TABLE `user` (
              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
              `username` varchar(50) NOT NULL COMMENT '用户名',
              `email` varchar(100) NULL COMMENT '邮箱'
            ) ENGINE = InnoDB COMMENT = '用户表';
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        assertNotNull(tableInfo.getPrimaryKeys());
        assertEquals(1, tableInfo.getPrimaryKeys().size());
        assertEquals("id", tableInfo.getPrimaryKeys().get(0));

        ColumnInfo idColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "id".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(idColumn);
        assertTrue(Boolean.TRUE.equals(idColumn.getPrimaryKey()), "id应标记为主键");
        assertTrue(Boolean.TRUE.equals(idColumn.getAutoIncrement()), "id应标记为自增");
    }

    @Test
    @DisplayName("解析列注释")
    void testColumnCommentsParsing() {
        String sql = """
            CREATE TABLE `content` (
              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '内容ID',
              `title` varchar(100) NULL COMMENT '标题',
              `type` tinyint UNSIGNED NOT NULL COMMENT '类型',
              PRIMARY KEY (`id`)
            ) ENGINE = InnoDB COMMENT = '内容表';
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        ColumnInfo idColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "id".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(idColumn);
        assertEquals("内容ID", idColumn.getComment());

        ColumnInfo titleColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "title".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(titleColumn);
        assertEquals("标题", titleColumn.getComment());
    }

    @Test
    @DisplayName("解析NULL/NOT NULL与默认值")
    void testNullabilityAndDefaultValueParsing() {
        String sql = """
            CREATE TABLE `test` (
              `id` bigint NOT NULL AUTO_INCREMENT,
              `name` varchar(50) NOT NULL DEFAULT 'unknown',
              `status` tinyint NULL DEFAULT 1,
              `created_at` datetime NULL,
              PRIMARY KEY (`id`)
            ) ENGINE = InnoDB;
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        ColumnInfo nameColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "name".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(nameColumn);
        assertFalse(Boolean.TRUE.equals(nameColumn.getNullable()), "name字段应为非空");
        assertEquals("unknown", nameColumn.getDefaultValue());

        ColumnInfo statusColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "status".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(statusColumn);
        assertTrue(Boolean.TRUE.equals(statusColumn.getNullable()), "status字段应允许为空");
        assertEquals("1", statusColumn.getDefaultValue());
    }

    @Test
    @DisplayName("提取表名")
    void testExtractTableName() {
        assertEquals("user", sqlParser.extractTableName("CREATE TABLE `user` (id INT)"));
        assertEquals("user", sqlParser.extractTableName("CREATE TABLE user (id INT)"));
        assertEquals("test_table", sqlParser.extractTableName("CREATE TABLE IF NOT EXISTS `test_table` (id INT)"));
    }

    @Test
    @DisplayName("验证SQL合法性")
    void testIsValidSQL() {
        assertTrue(sqlParser.isValidSQL("CREATE TABLE test (id INT PRIMARY KEY)"));
        assertFalse(sqlParser.isValidSQL("CREATE TABLE test (id INT PRIMARY KEY"));
    }

    @Test
    @DisplayName("解析复杂示例")
    void testComplexExampleParsing() {
        String sql = """
            CREATE TABLE `book_content_tag`  (
              `book_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
              `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
              `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
              `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
              `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
              `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
              PRIMARY KEY (`book_id`, `tag_id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书标签关联表' ROW_FORMAT = DYNAMIC;
            """;

        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        assertNotNull(tableInfo);
        assertEquals("book_content_tag", tableInfo.getTableName());
        assertEquals("图书标签关联表", tableInfo.getComment());
        assertEquals(6, tableInfo.getColumnInfos().size());
        assertEquals(2, tableInfo.getPrimaryKeys().size());

        ColumnInfo bookIdColumn = tableInfo.getColumnInfos().stream()
            .filter(col -> "book_id".equals(col.getOriginalName()))
            .findFirst()
            .orElse(null);
        assertNotNull(bookIdColumn);
        assertEquals("图书ID", bookIdColumn.getComment());
        assertTrue(Boolean.TRUE.equals(bookIdColumn.getPrimaryKey()));
    }
}
