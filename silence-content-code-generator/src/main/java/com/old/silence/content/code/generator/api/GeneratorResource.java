package com.old.silence.content.code.generator.api;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.dto.BatchGenerationRequest;
import com.old.silence.content.code.generator.dto.BatchGenerationResult;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.content.code.generator.service.RuleProcessorService;
import com.old.silence.content.code.generator.service.SpringCodeGeneratorService;

/**
 * 代码生成器资源接口
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class GeneratorResource {

    private static final Logger log = LoggerFactory.getLogger(GeneratorResource.class);

    private final ApiDocumentGeneratorService apiDocumentGeneratorService;
    private final SpringCodeGeneratorService springCodeGeneratorService;
    private final RuleProcessorService ruleProcessorService;

    public GeneratorResource(ApiDocumentGeneratorService apiDocumentGeneratorService,
                             SpringCodeGeneratorService springCodeGeneratorService,
                             RuleProcessorService ruleProcessorService) {
        this.apiDocumentGeneratorService = apiDocumentGeneratorService;
        this.springCodeGeneratorService = springCodeGeneratorService;
        this.ruleProcessorService = ruleProcessorService;
    }

    /**
     * 批量生成代码（标准CRUD接口）
     * 支持指定表名列表，如果不指定则生成所有表
     * API文档会从表结构自动生成标准的CRUD接口
     *
     * @param request 批量生成请求
     * @return 生成结果
     */
    @PostMapping("/generate/batch")
    public ResponseEntity<BatchGenerationResult> generateBatch(@RequestBody BatchGenerationRequest request) {
        // 参数验证
        if (request.getConfig() == null) {
            return ResponseEntity.badRequest()
                    .body(BatchGenerationResult.failure("配置信息不能为空"));
        }

        if (!validateConfig(request.getConfig())) {
            return ResponseEntity.badRequest()
                    .body(BatchGenerationResult.failure("配置信息不完整，请检查数据库连接和输出目录配置"));
        }

        try {
            BatchGenerationResult result = generateAPI(request.getConfig(), request.getTableNames());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("批量生成代码失败", e);
            return ResponseEntity.internalServerError()
                    .body(BatchGenerationResult.failure("批量生成失败: " + e.getMessage()));
        }
    }

    /**
     * 使用自定义API文档生成代码（支持复杂接口）
     * 适用于标准CRUD无法满足需求的场景
     *
     * @param request 批量生成请求，必须包含customApiDocs
     * @return 生成结果
     */
    @PostMapping("/generate/batch-with-custom-api")
    public ResponseEntity<BatchGenerationResult> generateBatchWithCustomApi(@RequestBody BatchGenerationRequest request) {
        // 参数验证
        if (request.getConfig() == null) {
            return ResponseEntity.badRequest()
                    .body(BatchGenerationResult.failure("配置信息不能为空"));
        }

        if (!validateConfig(request.getConfig())) {
            return ResponseEntity.badRequest()
                    .body(BatchGenerationResult.failure("配置信息不完整，请检查数据库连接和输出目录配置"));
        }

        if (request.getCustomApiDocs() == null || request.getCustomApiDocs().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(BatchGenerationResult.failure("自定义API文档不能为空"));
        }

        try {
            BatchGenerationResult result = generateAPIWithCustomDoc(request.getConfig(), request.getCustomApiDocs());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("使用自定义API文档批量生成代码失败", e);
            return ResponseEntity.internalServerError()
                    .body(BatchGenerationResult.failure("批量生成失败: " + e.getMessage()));
        }
    }

    /**
     * 验证配置
     */
    private boolean validateConfig(GeneratorConfig config) {
        return StringUtils.hasText(config.getDbUrl())
                && StringUtils.hasText(config.getUsername())
                && StringUtils.hasText(config.getBasePackage())
                && (StringUtils.hasText(config.getServiceOutputDir())
                || StringUtils.hasText(config.getInterfaceOutputDir())
                || StringUtils.hasText(config.getConsoleOutputDir()));
    }

    /**
     * 生成API代码的核心方法
     *
     * @param config     生成配置
     * @param tableNames 要生成的表名列表，为null或空则生成所有表
     * @return 生成结果
     */
    private BatchGenerationResult generateAPI(GeneratorConfig config, java.util.List<String> tableNames) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer()) {
            Map<String, String> allTables = analyzer.getTablesWithComments();

            // 过滤表名
            Map<String, String> tablesToGenerate = (tableNames == null || tableNames.isEmpty())
                    ? allTables
                    : allTables.entrySet().stream()
                    .filter(entry -> tableNames.contains(entry.getKey()))
                    .collect(java.util.stream.Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

            if (tablesToGenerate.isEmpty()) {
                return BatchGenerationResult.failure("未找到要生成的表");
            }

            log.info("🚀 开始生成API，共 {} 张表", tablesToGenerate.size());

            tablesToGenerate.forEach((tableName, tableComment) -> {
                try {
                    doGenerate(analyzer, tableName, config);
                    successCount.incrementAndGet();
                    log.info("✅ 表 {} ({}) 生成成功", tableName, tableComment);
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    log.error("❌ 表 {} ({}) 生成失败: {}", tableName, tableComment, e.getMessage(), e);
                }
            });

            return BatchGenerationResult.success(
                    tablesToGenerate.size(),
                    successCount.get(),
                    failureCount.get()
            );

        } catch (Exception e) {
            log.error("批量生成过程出错", e);
            return BatchGenerationResult.failure("生成过程出错: " + e.getMessage());
        }
    }

    /**
     * 为单个表生成代码（标准CRUD模式）
     *
     * @param analyzer  SQL分析器
     * @param tableName 表名
     * @param config    生成配置
     */
    private void doGenerate(SQLAnalyzer analyzer, String tableName, GeneratorConfig config) throws Exception {
        log.info("📋 开始处理表: {}", tableName);

        // 1. 分析表结构
        TableInfo tableInfo = analyzer.analyzeTable(tableName);
        if (tableInfo == null) {
            throw new IllegalStateException("表结构分析失败：" + tableName);
        }
        log.debug("表结构分析完成，共 {} 个字段", tableInfo.getColumnInfos().size());

        // 2. 转换数据库字段到Java字段
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        // 3. 自动生成标准CRUD接口文档
        log.info("🔄 自动生成标准CRUD接口文档");
        ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
        if (apiDoc == null) {
            throw new IllegalStateException("接口文档生成失败：" + tableName);
        }

        // 4. 生成Markdown文档（如果配置了输出目录）
        if (StringUtils.hasText(config.getApiDocOutputDir())) {
            apiDocumentGeneratorService.generateApiDocs(apiDoc, config.getApiDocOutputDir());
            log.debug("接口文档生成完成，共 {} 个接口", apiDoc.getEndpoints().size());
        }

        // 5. 生成代码
        SpringCodeGenerator codeGenerator = new SpringCodeGenerator(
                config.getPersistencePackage(),
                config.getUseLombok()
        );
        springCodeGeneratorService.generateCode(codeGenerator, tableInfo, apiDoc, config);

        log.debug("代码生成完成");
    }

    /**
     * 使用自定义API文档为单个表生成代码（独立方法，无耦合）
     *
     * @param analyzer     SQL分析器
     * @param tableName    表名
     * @param config       生成配置
     * @param customApiDoc 自定义API文档
     */
    private void doGenerateWithCustomApi(SQLAnalyzer analyzer, String tableName, GeneratorConfig config, 
                                         ApiDocument customApiDoc) throws Exception {
        log.info("📋 开始处理表: {} (使用自定义API文档)", tableName);

        // 1. 分析表结构
        TableInfo tableInfo = analyzer.analyzeTable(tableName);
        if (tableInfo == null) {
            throw new IllegalStateException("表结构分析失败：" + tableName);
        }
        log.debug("表结构分析完成，共 {} 个字段", tableInfo.getColumnInfos().size());

        // 2. 转换数据库字段到Java字段
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        // 3. 使用自定义接口文档
        log.info("🎯 使用自定义API文档，共 {} 个接口", customApiDoc.getEndpoints().size());
        
        // 确保tableName一致
        if (!tableName.equals(customApiDoc.getTableName())) {
            log.warn("⚠️  自定义API文档的tableName({})与实际表名({})不一致，已自动修正", 
                     customApiDoc.getTableName(), tableName);
            customApiDoc.setTableName(tableName);
        }

        // 4. 生成Markdown文档（如果配置了输出目录）
        if (StringUtils.hasText(config.getApiDocOutputDir())) {
            apiDocumentGeneratorService.generateApiDocs(customApiDoc, config.getApiDocOutputDir());
            log.debug("接口文档生成完成，共 {} 个接口", customApiDoc.getEndpoints().size());
        }

        // 5. 生成代码
        SpringCodeGenerator codeGenerator = new SpringCodeGenerator(
                config.getPersistencePackage(),
                config.getUseLombok()
        );
        springCodeGeneratorService.generateCode(codeGenerator, tableInfo, customApiDoc, config);

        log.debug("代码生成完成");
    }

    /**
     * 使用自定义API文档生成代码
     *
     * @param config        生成配置
     * @param customApiDocs 自定义API文档映射 (tableName -> ApiDocument)
     * @return 生成结果
     */
    private BatchGenerationResult generateAPIWithCustomDoc(GeneratorConfig config, 
                                                           Map<String, ApiDocument> customApiDocs) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer()) {
            log.info("🚀 开始使用自定义API文档生成代码，共 {} 张表", customApiDocs.size());

            customApiDocs.forEach((tableName, apiDoc) -> {
                try {
                    // 验证表是否存在
                    Map<String, String> allTables = analyzer.getTablesWithComments();
                    if (!allTables.containsKey(tableName)) {
                        throw new IllegalArgumentException("表不存在：" + tableName);
                    }

                    String tableComment = allTables.get(tableName);
                    doGenerateWithCustomApi(analyzer, tableName, config, apiDoc);
                    successCount.incrementAndGet();
                    log.info("✅ 表 {} ({}) 使用自定义API文档生成成功，接口数: {}", 
                             tableName, tableComment, apiDoc.getEndpoints().size());
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    log.error("❌ 表 {} 使用自定义API文档生成失败: {}", tableName, e.getMessage(), e);
                }
            });

            return BatchGenerationResult.success(
                    customApiDocs.size(),
                    successCount.get(),
                    failureCount.get()
            );

        } catch (Exception e) {
            log.error("使用自定义API文档批量生成过程出错", e);
            return BatchGenerationResult.failure("生成过程出错: " + e.getMessage());
        }
    }


}
