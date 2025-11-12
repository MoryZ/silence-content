package com.old.silence.code.generator.parser;

import com.old.silence.code.generator.model.TableInfo;

import java.util.List;

/**
 * SQL解析器
 * 支持解析CREATE TABLE、ALTER TABLE等SQL语句，提取表结构信息
 *
 * @author moryzang
 */
public interface SQLParser {

    /**
     * 解析CREATE TABLE语句
     *
     * @param sql CREATE TABLE SQL语句
     * @return 表信息
     */
    TableInfo parseCreateTable(String sql);

    /**
     * 解析ALTER TABLE语句
     *
     * @param sql ALTER TABLE SQL语句
     * @return 表信息（增量更新）
     */
    TableInfo parseAlterTable(String sql);

    /**
     * 从SQL文件读取并解析
     *
     * @param filePath SQL文件路径
     * @return 表信息列表
     */
    List<TableInfo> parseFromFile(String filePath);

    /**
     * 验证SQL语句是否有效
     *
     * @param sql SQL语句
     * @return 是否有效
     */
    boolean isValidSQL(String sql);

    /**
     * 提取表名
     *
     * @param sql SQL语句
     * @return 表名
     */
    String extractTableName(String sql);
}

