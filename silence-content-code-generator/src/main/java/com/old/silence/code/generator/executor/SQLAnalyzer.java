package com.old.silence.code.generator.executor;

import java.sql.SQLException;
import java.util.Map;

import com.old.silence.code.generator.model.TableInfo;

/**
 * SQL解析接口，定义从数据库读取表结构的能力。
 *
 * <p>允许针对不同数据源提供多种实现，例如基于JDBC的实现、
 * 基于缓存的实现等。</p>
 *
 * @author moryzang
 */
public interface SQLAnalyzer extends AutoCloseable {

    /**
     * 分析指定表的结构信息。
     *
     * @param tableName 表名
     * @return 表结构信息
     * @throws SQLException 数据库访问异常
     */
    TableInfo analyzeTable(String tableName) throws SQLException;

    /**
     * 获取所有表及其注释。
     *
     * @return 表名到表注释的映射
     * @throws SQLException 数据库访问异常
     */
    Map<String, String> getTablesWithComments() throws SQLException;

    /**
     * 关闭底层资源。
     *
     * @throws SQLException 数据库访问异常
     */
    @Override
    void close() throws SQLException;
}
