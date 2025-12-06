package com.old.silence.content.code.generator.orchestrator;

import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.config.DatabaseConfig;
import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.parser.SQLParser;
import com.old.silence.content.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.content.code.generator.service.RuleProcessorService;
import com.old.silence.content.code.generator.strategy.CodeGenerationStrategy;
import com.old.silence.content.code.generator.strategy.CodeGenerationStrategyManager;
import com.old.silence.core.context.CommonErrors;

/**
 * 代码生成编排器实现
 * 整合SQL解析器、SQL分析器和API文档生成器，提供统一的代码生成流程
 *
 * @author moryzang
 */
@Service
public class DefaultCodeGenerationOrchestrator implements CodeGenerationOrchestrator {

    private final SQLParser sqlParser;
    private final ApiDocumentGeneratorService apiDocService;
    private final RuleProcessorService ruleProcessorService;
    private final CodeGenerationStrategyManager strategyManager;

    public DefaultCodeGenerationOrchestrator(SQLParser sqlParser,
                                             ApiDocumentGeneratorService apiDocService,
                                             RuleProcessorService ruleProcessorService,
                                             CodeGenerationStrategyManager strategyManager) {
        this.sqlParser = sqlParser;
        this.apiDocService = apiDocService;
        this.ruleProcessorService = ruleProcessorService;
        this.strategyManager = strategyManager;
    }

    @Override
    public ApiDocument generateFromSQL(String sql) {
        // 1. 解析SQL
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);
        if (tableInfo == null) {
            throw CommonErrors.INVALID_PARAMETER.createException("SQL解析失败：表信息为空");
        }

        // 2. 字段转换（数据库字段 -> Java字段）
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        // 3. 生成API文档
        ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
        if (apiDoc == null) {
            throw CommonErrors.INVALID_PARAMETER.createException("API文档生成失败");
        }

        return apiDoc;
    }

    @Override
    public GenerationResult generateFromDatabase(String tableName,DatabaseConfig databaseConfig, GeneratorConfig config) {
        try {
            // 1. 从数据库分析表结构
            try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(databaseConfig)) {
                TableInfo tableInfo = analyzer.analyzeTable(tableName);
                if (tableInfo == null) {
                    return GenerationResult.failure("数据库表结构分析失败：表 " + tableName + " 不存在或无法访问");
                }

                // 2. 字段转换（数据库字段 -> Java字段）
                tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

                // 3. 生成API文档
                ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
                if (apiDoc == null) {
                    return GenerationResult.failure("API文档生成失败");
                }

                // 4. 生成Markdown文档（如果配置了输出目录）
                if (config.getApiDocOutputDir() != null && !config.getApiDocOutputDir().isEmpty()) {
                    apiDocService.generateApiDocs(apiDoc, config.getApiDocOutputDir());
                }

                // 5. 生成代码（如果配置了策略）
                if (config.getStrategyType() != null) {
                    generateCodeWithStrategy(tableInfo, apiDoc, config);
                }

                return GenerationResult.success("从数据库生成成功");
            }
        } catch (Exception e) {
            return GenerationResult.failure("从数据库生成失败: " + e.getMessage());
        }
    }

    @Override
    public GenerationResult generateFromRequirement(String requirement, GeneratorConfig config) {
        return null;
    }

    @Override
    public GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config) {
        try {
            // 从API文档生成代码（需要从API文档中提取表信息）
            // 注意：这个功能需要API文档中包含足够的表结构信息
            // 目前API文档主要包含接口信息，表结构信息较少
            // 可以考虑扩展API文档模型，或者从其他来源获取表信息

            if (config.getStrategyType() != null) {
                // 这里需要根据实际情况实现
                // 可能需要从API文档中提取表信息，或者要求传入TableInfo
                return GenerationResult.failure("从API文档生成代码功能待实现");
            }

            return GenerationResult.success("从API文档处理成功");
        } catch (Exception e) {
            return GenerationResult.failure("从API文档生成失败: " + e.getMessage());
        }
    }

    /**
     * 使用策略生成代码
     */
    private void generateCodeWithStrategy(TableInfo tableInfo, ApiDocument apiDoc, GeneratorConfig config) {
        try {
            // 根据配置选择策略
            CodeGenerationStrategy strategy = strategyManager.getStrategy(config.getStrategyType());
            if (strategy == null) {
                throw new IllegalArgumentException("未找到策略: " + config.getStrategyType());
            }

            // 生成各层级代码
            for (CodeGenerationStrategy.CodeLayer layer : CodeGenerationStrategy.CodeLayer.values()) {
                if (strategy.supports(layer)) {
                    strategy.generateCode(tableInfo, apiDoc, config, layer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("代码生成失败: " + e.getMessage(), e);
        }
    }
}