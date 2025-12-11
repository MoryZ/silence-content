package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.vo.GenerationResult;

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
     * @return 生成结果
     */
    ApiDocument generateFromSQL(String sql);

    /**
     * 从数据库表生成代码
     *
     * @param databaseConfig 表名
     * @return 生成结果
     */
    GenerationResult generateFromDatabase(DatabaseConfig databaseConfig, CodeGenModuleConfig codeGenModuleConfig);

    /**
     * 从需求文档生成代码
     *
     * @param requirement 需求
     * @return 生成结果
     */
    GenerationResult generateFromRequirement(String requirement);

    /**
     * 从接口文档生成代码
     *
     * @param apiDoc 接口文档
     * @return 生成结果
     */
    GenerationResult generateFromApiDocument(ApiDocument apiDoc);
}

