package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.parser.SQLParser;

import com.old.silence.content.code.generator.vo.GenerationResult;
import com.old.silence.core.context.CommonErrors;

import java.sql.SQLException;
import java.io.IOException;

/**
 * 代码生成编排器实现
 * 整合SQL解析器、SQL分析器和API文档生成器，提供统一的代码生成流程
 *
 * @author moryzang
 */
@Service
public class DefaultCodeGenerationOrchestrator implements CodeGenerationOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(DefaultCodeGenerationOrchestrator.class);

    private final SQLParser sqlParser;
    private final ApiDocumentGeneratorService apiDocService;
    private final CodeGenerationStrategyManager strategyManager;

    public DefaultCodeGenerationOrchestrator(SQLParser sqlParser,
                                             ApiDocumentGeneratorService apiDocService,
                                             CodeGenerationStrategyManager strategyManager) {
        this.sqlParser = sqlParser;
        this.apiDocService = apiDocService;
        this.strategyManager = strategyManager;
    }

    @Override
    public ApiDocument generateFromSQL(String sql) {
        // 1. 解析SQL
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);
        if (tableInfo == null) {
            throw CommonErrors.INVALID_PARAMETER.createException("SQL解析失败：表信息为空");
        }

        // 2. 生成API文档
        ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
        if (apiDoc == null) {
            throw CommonErrors.INVALID_PARAMETER.createException("API文档生成失败");
        }

        return apiDoc;
    }

    @Override
    public GenerationResult generateFromDatabase(DatabaseConfig databaseConfig, CodeGenModuleConfig codeGenModuleConfig) {
        var tableName = databaseConfig.getTableName();
        try {
            // 1. 从数据库分析表结构
            try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(databaseConfig)) {
                TableInfo tableInfo = analyzer.analyzeTable(tableName);
                if (tableInfo == null) {
                    return GenerationResult.failure("数据库表结构分析失败：表 " + tableName + " 不存在或无法访问");
                }

                // 2. 生成API文档
                ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
                if (apiDoc == null) {
                    return GenerationResult.failure("API文档生成失败");
                }

                // 3. 生成Markdown文档（如果配置了输出目录）
                String docsOutputDir = codeGenModuleConfig.getProjectPath() + "/" + codeGenModuleConfig.getModulePath() + "/docs";
                apiDocService.generateApiDocs(apiDoc, docsOutputDir);

                // 4. 生成代码（如果配置了策略）
                generateCodeWithStrategy(tableInfo, apiDoc, codeGenModuleConfig);

                log.info("从数据库生成代码成功，表: {}", tableName);
                return GenerationResult.success("从数据库生成成功");
            }
        } catch (SQLException e) {
            log.error("数据库连接或查询失败，表: {}", tableName, e);
            return GenerationResult.failure("数据库连接失败: " + e.getMessage());
        } catch (IOException e) {
            log.error("文件I/O操作失败，表: {}", tableName, e);
            return GenerationResult.failure("文件操作失败: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("参数验证失败，表: {}", tableName, e);
            return GenerationResult.failure("参数错误: " + e.getMessage());
        } catch (Exception e) {
            log.error("代码生成过程中发生未预期的异常，表: {}", tableName, e);
            return GenerationResult.failure("代码生成失败，请查看日志获取详细信息");
        }
    }

    @Override
    public GenerationResult generateFromRequirement(String requirement) {
        return GenerationResult.failure("从需求文档生成代码功能暂未实现");
    }

    @Override
    public GenerationResult generateFromApiDocument(ApiDocument apiDoc) {
        try {
            // 从API文档生成代码（需要从API文档中提取表信息）
            // 注意：这个功能需要API文档中包含足够的表结构信息
            // 目前API文档主要包含接口信息，表结构信息较少
            // 可以考虑扩展API文档模型，或者从其他来源获取表信息


            log.info("从API文档生成代码成功");
            return GenerationResult.success("从API文档处理成功");
        } catch (IllegalArgumentException e) {
            log.error("API文档参数验证失败", e);
            return GenerationResult.failure("参数错误: " + e.getMessage());
        } catch (Exception e) {
            log.error("从API文档生成代码失败", e);
            return GenerationResult.failure("从API文档生成失败: " + e.getMessage());
        }
    }

    /**
     * 使用策略生成代码
     */
    private void generateCodeWithStrategy(TableInfo tableInfo, ApiDocument apiDoc, CodeGenModuleConfig config) {
        try {
            // 根据配置选择策略
            CodeGenerationStrategy strategy = strategyManager.getStrategy(config.getToolType());
            if (strategy == null) {
                throw new IllegalArgumentException("未找到策略: " + config.getToolType());
            }

            // 生成各层级代码
            strategy.generateCode(tableInfo, apiDoc, config);
        } catch (Exception e) {
            throw new RuntimeException("代码生成失败: " + e.getMessage(), e);
        }
    }
}