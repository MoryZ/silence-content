package com.old.silence.code.generator.orchestrator;

import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;

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
     * 从接口文档生成代码
     *
     * @param apiDoc 接口文档
     * @param config 生成配置
     * @return 生成结果
     */
    GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config);

    /**
     * 生成结果
     */
    class GenerationResult {
        private boolean success;
        private String message;
        private TableInfo tableInfo;
        private ApiDocument apiDocument;

        public static GenerationResult success(String message, TableInfo tableInfo,
                                              ApiDocument apiDocument) {
            GenerationResult result = new GenerationResult();
            result.success = true;
            result.message = message;
            result.tableInfo = tableInfo;
            result.apiDocument = apiDocument;
            return result;
        }

        public static GenerationResult failure(String message) {
            GenerationResult result = new GenerationResult();
            result.success = false;
            result.message = message;
            return result;
        }

        // Getters
        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public TableInfo getTableInfo() {
            return tableInfo;
        }

        public ApiDocument getApiDocument() {
            return apiDocument;
        }

    }
}

