/**
 * 执行器和分析器
 *
 * <p>本包实现了数据源分析、SQL 解析等底层执行逻辑。
 *
 * <h3>核心组件：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.executor.SQLAnalyzer} - SQL 分析接口</li>
 *   <li>{@link com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer} - JDBC SQL 分析实现</li>
 *   <li>{@link com.old.silence.content.code.generator.executor.SpringCodeGenerator} - Spring 代码生成执行器</li>
 * </ul>
 *
 * <h3>功能说明：</h3>
 * <ul>
 *   <li>从数据库连接读取表结构信息</li>
 *   <li>解析 SQL 语句提取表和列信息</li>
 *   <li>执行模板渲染和文件生成</li>
 * </ul>
 *
 * <h3>设计特点：</h3>
 * <ul>
 *   <li>SQLAnalyzer 为接口，支持多种数据库驱动</li>
 *   <li>通过 try-with-resources 自动关闭资源</li>
 *   <li>支持增量分析和缓存</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.executor;
