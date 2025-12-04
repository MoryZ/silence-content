package com.old.silence.content.code.generator.orchestrator;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

/**
 * 代码生成编排器
 * 统一管理代码生成的整个流程：SQL解析 -> 表结构分析 -> API文档生成 -> 代码生成
 *
 * @author moryzang
 */
public interface CodeGenerationOrchestrator {

    /**
     * 从SQL输入生成代码
     *
     * @param sql    SQL语句（CREATE TABLE等）
     * @param config 生成配置
     * @return 生成结果
     */
    GenerationResult generateFromSQL(String sql, GeneratorConfig config);

    /**
     * 从数据库表生成代码
     *
     * @param tableName 表名
     * @param config    生成配置
     * @return 生成结果
     */
    GenerationResult generateFromDatabase(String tableName, GeneratorConfig config);

    /**
     * 从需求文档生成代码
     *
     * @param requirement 需求
     * @param config      生成配置
     * @return 生成结果
     */
    GenerationResult generateFromRequirement(String requirement, GeneratorConfig config);

    /**
     * 从接口文档生成代码
     *
     * @param apiDoc 接口文档
     * @param config 生成配置
     * @return 生成结果
     */
    GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config);
}

