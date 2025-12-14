/**
 * 代码生成的核心数据模型
 *
 * <p>本包定义了代码生成过程中使用的所有核心实体类和数据模型。
 *
 * <h3>主要模型：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.model.TableInfo} - 表结构信息</li>
 *   <li>{@link com.old.silence.content.code.generator.model.ColumnInfo} - 列信息</li>
 *   <li>{@link com.old.silence.content.code.generator.model.ApiDocument} - API 文档</li>
 *   <li>{@link com.old.silence.content.code.generator.model.CodeFileSpec} - 代码文件规格（元数据）</li>
 * </ul>
 *
 * <h3>数据流向：</h3>
 * <pre>
 * DatabaseConfig → TableInfo → ApiDocument → CodeFileSpec → 生成文件
 * </pre>
 *
 * <h3>关键特性：</h3>
 * <ul>
 *   <li>TableInfo 包含表的完整结构信息（列、索引、主键、外键等）</li>
 *   <li>ApiDocument 定义了业务接口（CRUD、搜索等端点）</li>
 *   <li>CodeFileSpec 存储文件生成的元数据（模板名、输出路径、文件名后缀等）</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.model;
