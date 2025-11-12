package com.old.silence.code.generator.orchestrator;

import org.springframework.stereotype.Service;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.executor.SQLAnalyzer;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.parser.SQLParser;
import com.old.silence.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.code.generator.strategy.CodeGenerationStrategy;
import com.old.silence.code.generator.strategy.CodeGenerationStrategyManager;

/**
 * @author moryzang
 */
@Service
public class DefaultCodeGenerationOrchestrator implements CodeGenerationOrchestrator {

    private final SQLParser sqlParser;
    private final SQLAnalyzer sqlAnalyzer;
    private final ApiDocumentGeneratorService apiDocService;
    private final CodeGenerationStrategyManager strategyManager;

    public DefaultCodeGenerationOrchestrator(SQLParser sqlParser, SQLAnalyzer sqlAnalyzer,
                                             ApiDocumentGeneratorService apiDocService,
                                             CodeGenerationStrategyManager strategyManager) {
        this.sqlParser = sqlParser;
        this.sqlAnalyzer = sqlAnalyzer;
        this.apiDocService = apiDocService;
        this.strategyManager = strategyManager;
    }

    @Override
    public GenerationResult generateFromSQL(String sql, GeneratorConfig config) {
        // 1. 解析SQL
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);

        // 2. 生成API文档
        ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);

        // 3. 生成代码
        generateCodeWithStrategy(tableInfo, apiDoc, config);

        return GenerationResult.success("生成成功", tableInfo, apiDoc);
    }

    @Override
    public GenerationResult generateFromDatabase(String tableName, GeneratorConfig config) {
        return null;
    }

    @Override
    public GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config) {
        return null;
    }

    private void generateCodeWithStrategy(TableInfo tableInfo, ApiDocument apiDoc, GeneratorConfig config) {
        // 根据配置选择策略
        CodeGenerationStrategy strategy = strategyManager.getStrategy(config.getStrategyType());

        // 生成各层级代码
        for (CodeGenerationStrategy.CodeLayer layer : CodeGenerationStrategy.CodeLayer.values()) {
            if (strategy.supports(layer)) {
                strategy.generateCode(tableInfo, apiDoc, config, layer);
            }
        }
    }
}