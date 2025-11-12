package com.old.silence.code.generator.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.old.silence.code.generator.model.TableInfo;

/**
 * SQL解析器使用示例
 * 演示如何使用SQLParser解析SQL语句
 *
 * @author moryzang
 */
@Component
public class SQLParserExample {

    private static final Logger log = LoggerFactory.getLogger(SQLParserExample.class);

    private final SQLParser sqlParser;

    public SQLParserExample(SQLParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    /**
     * 示例：解析CREATE TABLE语句
     */
    public void exampleParseCreateTable() {
        String sql = """
            CREATE TABLE `user` (
                `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                `username` VARCHAR(50) NOT NULL COMMENT '用户名',
                `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
                `password` VARCHAR(255) NOT NULL COMMENT '密码',
                `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
                `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                `updated_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                PRIMARY KEY (`id`),
                UNIQUE KEY `uk_username` (`username`),
                UNIQUE KEY `uk_email` (`email`),
                KEY `idx_status` (`status`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
            """;

        try {
            TableInfo tableInfo = sqlParser.parseCreateTable(sql);
            
            log.info("表名: {}", tableInfo.getTableName());
            log.info("表注释: {}", tableInfo.getComment());
            log.info("字段数量: {}", tableInfo.getColumnInfos().size());
            log.info("主键: {}", tableInfo.getPrimaryKeys());
            log.info("索引数量: {}", tableInfo.getIndexes().size());
            
            // 打印字段信息
            tableInfo.getColumnInfos().forEach(column -> {
                log.info("字段: {} ({}) - {}", 
                    column.getOriginalName(), 
                    column.getType(), 
                    column.getComment());
            });
            
        } catch (Exception e) {
            log.error("解析失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 示例：从文件解析多个表
     */
    public void exampleParseFromFile() {
        String filePath = "db/silence-content.sql";
        
        try {
            java.util.List<TableInfo> tableInfos = sqlParser.parseFromFile(filePath);
            log.info("从文件解析到 {} 个表", tableInfos.size());
            
            tableInfos.forEach(tableInfo -> {
                log.info("表: {} - {}", tableInfo.getTableName(), tableInfo.getComment());
            });
            
        } catch (Exception e) {
            log.error("从文件解析失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 示例：验证SQL语句
     */
    public void exampleValidateSQL() {
        String validSQL = "CREATE TABLE test (id INT PRIMARY KEY)";
        String invalidSQL = "SELECT * FROM";
        
        log.info("有效SQL: {}", sqlParser.isValidSQL(validSQL));
        log.info("无效SQL: {}", sqlParser.isValidSQL(invalidSQL));
    }

    /**
     * 示例：提取表名
     */
    public void exampleExtractTableName() {
        String sql = "CREATE TABLE `user` (id INT)";
        String tableName = sqlParser.extractTableName(sql);
        log.info("提取的表名: {}", tableName);
    }
}

