package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.dto.BatchGenerationResult;
import com.old.silence.content.code.generator.dto.CodePreviewResponse;
import com.old.silence.content.code.generator.dto.PreviewGenerationResult;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BatchGenerationService {

    private static final Logger log = LoggerFactory.getLogger(BatchGenerationService.class);

    private final ApiDocumentGeneratorService apiDocumentGeneratorService;
    private final SpringCodeGeneratorService springCodeGeneratorService;
    private final RuleProcessorService ruleProcessorService;

    public BatchGenerationService(ApiDocumentGeneratorService apiDocumentGeneratorService,
                                  SpringCodeGeneratorService springCodeGeneratorService,
                                  RuleProcessorService ruleProcessorService) {
        this.apiDocumentGeneratorService = apiDocumentGeneratorService;
        this.springCodeGeneratorService = springCodeGeneratorService;
        this.ruleProcessorService = ruleProcessorService;
    }

    public BatchGenerationResult generateAPI(GeneratorConfig config, List<String> tableNames) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(config)) {
            Map<String, String> allTables = analyzer.getTablesWithComments();

            Map<String, String> tablesToGenerate = (tableNames == null || tableNames.isEmpty())
                    ? allTables
                    : allTables.entrySet().stream()
                    .filter(entry -> tableNames.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (tablesToGenerate.isEmpty()) {
                return BatchGenerationResult.failure("未找到要生成的表");
            }

            log.info("开始生成API，共 {} 张表", tablesToGenerate.size());

            tablesToGenerate.forEach((tableName, tableComment) -> {
                try {
                    doGenerate(analyzer, tableName, config);
                    successCount.incrementAndGet();
                    log.info("表 {} ({}) 生成成功", tableName, tableComment);
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    log.error("表 {} ({}) 生成失败: {}", tableName, tableComment, e.getMessage(), e);
                }
            });

            return BatchGenerationResult.success(tablesToGenerate.size(), successCount.get(), failureCount.get());
        } catch (Exception e) {
            log.error("批量生成过程出错", e);
            return BatchGenerationResult.failure("生成过程出错: " + e.getMessage());
        }
    }

    public BatchGenerationResult generateAPIWithCustomDoc(GeneratorConfig config, Map<String, ApiDocument> customApiDocs) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(config)) {
            log.info("开始使用自定义API文档生成代码，共 {} 张表", customApiDocs.size());

            customApiDocs.forEach((tableName, apiDoc) -> {
                try {
                    Map<String, String> allTables = analyzer.getTablesWithComments();
                    if (!allTables.containsKey(tableName)) {
                        throw new IllegalArgumentException("表不存在：" + tableName);
                    }

                    String tableComment = allTables.get(tableName);
                    doGenerateWithCustomApi(analyzer, tableName, config, apiDoc);
                    successCount.incrementAndGet();
                    log.info("表 {} ({}) 使用自定义API文档生成成功，接口数: {}", tableName, tableComment, apiDoc.getEndpoints().size());
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    log.error("表 {} 使用自定义API文档生成失败: {}", tableName, e.getMessage(), e);
                }
            });

            return BatchGenerationResult.success(customApiDocs.size(), successCount.get(), failureCount.get());
        } catch (Exception e) {
            log.error("使用自定义API文档批量生成过程出错", e);
            return BatchGenerationResult.failure("生成过程出错: " + e.getMessage());
        }
    }

    private void doGenerate(SQLAnalyzer analyzer, String tableName, GeneratorConfig config) throws Exception {
        log.info("开始处理表: {}", tableName);
        TableInfo tableInfo = analyzer.analyzeTable(tableName);
        if (tableInfo == null) {
            throw new IllegalStateException("表结构分析失败：" + tableName);
        }
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
        if (apiDoc == null) {
            throw new IllegalStateException("接口文档生成失败：" + tableName);
        }

        if (StringUtils.hasText(config.getApiDocOutputDir())) {
            apiDocumentGeneratorService.generateApiDocs(apiDoc, config.getApiDocOutputDir());
        }

        SpringCodeGenerator codeGenerator = new SpringCodeGenerator(config.getOrCreateRenderConfig());
        springCodeGeneratorService.generateCode(codeGenerator, tableInfo, apiDoc, config);
    }

    private void doGenerateWithCustomApi(SQLAnalyzer analyzer, String tableName, GeneratorConfig config, ApiDocument customApiDoc) throws Exception {
        log.info("开始处理表: {} (使用自定义API文档)", tableName);

        TableInfo tableInfo = analyzer.analyzeTable(tableName);
        if (tableInfo == null) {
            throw new IllegalStateException("表结构分析失败：" + tableName);
        }
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        if (!tableName.equals(customApiDoc.getTableName())) {
            log.warn("自定义API文档的tableName({})与实际表名({})不一致，已自动修正", customApiDoc.getTableName(), tableName);
            customApiDoc.setTableName(tableName);
        }

        if (StringUtils.hasText(config.getApiDocOutputDir())) {
            apiDocumentGeneratorService.generateApiDocs(customApiDoc, config.getApiDocOutputDir());
        }

        SpringCodeGenerator codeGenerator = new SpringCodeGenerator(config.getOrCreateRenderConfig());
        springCodeGeneratorService.generateCode(codeGenerator, tableInfo, customApiDoc, config);
    }

    public PreviewGenerationResult previewFilenames(GeneratorConfig config, java.util.List<String> tableNames) {
        Map<String, List<String>> filesByModule = new HashMap<>();
        filesByModule.put("interface", new ArrayList<>());
        filesByModule.put("service", new ArrayList<>());
        filesByModule.put("console", new ArrayList<>());
        filesByModule.put("enum", new ArrayList<>());

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(config)) {
            Map<String, String> allTables = analyzer.getTablesWithComments();
            Map<String, String> tablesToGenerate = (tableNames == null || tableNames.isEmpty())
                    ? allTables
                    : allTables.entrySet().stream()
                    .filter(entry -> tableNames.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            for (String tableName : tablesToGenerate.keySet()) {
                TableInfo tableInfo = analyzer.analyzeTable(tableName);
                tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
                ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
                Map<String, com.old.silence.content.code.generator.model.ApiEndpoint> endpoints = apiDoc.getEndpoints();

                String classPrefix = toPascalCase(tableInfo.getTableName());

                // interface module
                if (StringUtils.hasText(config.getInterfaceOutputDir())) {
                    if (has(endpoints, "创建") || has(endpoints, "更新")) {
                        filesByModule.get("interface").add(classPrefix + "Command.java");
                    }
                    if (has(endpoints, "分页查询")) {
                        filesByModule.get("interface").add(classPrefix + "Query.java");
                    }
                    if (has(endpoints, "根据主键查询") || has(endpoints, "分页查询")) {
                        filesByModule.get("interface").add(classPrefix + "View.java");
                    }
                    if (!endpoints.isEmpty()) {
                        filesByModule.get("interface").add(classPrefix + "Service.java");
                        filesByModule.get("interface").add(classPrefix + "Client.java");
                    }
                }

                // service module
                if (StringUtils.hasText(config.getServiceOutputDir())) {
                    filesByModule.get("service").add(classPrefix + ".java"); // Model
                    if (has(endpoints, "创建") || has(endpoints, "更新") || has(endpoints, "根据主键查询") || has(endpoints, "分页查询")) {
                        filesByModule.get("service").add(classPrefix + "Mapper.java");
                    }
                    filesByModule.get("service").add(classPrefix + "Dao.java");
                    filesByModule.get("service").add(classPrefix + "Repository.java");
                    filesByModule.get("service").add("MyBatis" + classPrefix + "Repository.java");
                    if (!endpoints.isEmpty()) {
                        filesByModule.get("service").add(classPrefix + "Resource.java");
                    }
                }

                // console module
                if (StringUtils.hasText(config.getConsoleOutputDir())) {
                    if (has(endpoints, "创建") || has(endpoints, "更新")) {
                        filesByModule.get("console").add(classPrefix + "ConsoleCommand.java");
                        filesByModule.get("console").add(classPrefix + "CommandMapper.java");
                    }
                    if (has(endpoints, "分页查询")) {
                        filesByModule.get("console").add(classPrefix + "ConsoleQuery.java");
                        filesByModule.get("console").add(classPrefix + "QueryMapper.java");
                    }
                    if (has(endpoints, "根据主键查询") || has(endpoints, "分页查询")) {
                        filesByModule.get("console").add(classPrefix + "ConsoleView.java");
                    }
                    if (!endpoints.isEmpty()) {
                        filesByModule.get("console").add(classPrefix + "ConsoleResource.java");
                    }
                }

                // enum module
                if (StringUtils.hasText(config.getEnumOutputDir()) && hasEnumConfig(config, tableInfo.getTableName())) {
                    filesByModule.get("enum").add(classPrefix + "Enum.java");
                }
            }
        } catch (Exception e) {
            log.error("预览文件名生成失败", e);
        }

        return PreviewGenerationResult.of(filesByModule);
    }

    private static boolean has(Map<String, com.old.silence.content.code.generator.model.ApiEndpoint> endpoints, String name) {
        return endpoints != null && endpoints.containsKey(name);
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String toPascalCase(String tableName) {
        if (tableName == null || tableName.isEmpty()) return tableName;
        String[] parts = tableName.split("[_-]");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    private boolean hasEnumConfig(GeneratorConfig config, String tableName) {
        if (config.getEnumConfigs() == null || config.getEnumConfigs().isEmpty()) {
            return false;
        }
        return config.getEnumConfigs().stream()
                .anyMatch(ec -> tableName.equals(ec.getTableName()) && Boolean.TRUE.equals(ec.getGenerateEnum()));
    }

    /**
     * 预览代码内容（标准CRUD模式）
     */
    public com.old.silence.content.code.generator.dto.CodePreviewResponse previewCode(GeneratorConfig config, String tableName) {
        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(config)) {
            TableInfo tableInfo = analyzer.analyzeTable(tableName);
            if (tableInfo == null) {
                throw new IllegalStateException("表结构分析失败：" + tableName);
            }

            tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
            
            ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
            
            SpringCodeGenerator codeGenerator = new SpringCodeGenerator(config.getOrCreateRenderConfig());
            
            return springCodeGeneratorService.previewCode(codeGenerator, tableInfo, apiDoc, config);
        } catch (Exception e) {
            log.error("预览代码失败", e);
            throw new RuntimeException("预览代码失败: " + e.getMessage(), e);
        }
    }

    /**
     * 预览代码内容（自定义API模式）
     */
    public CodePreviewResponse previewCodeWithCustomApi(
            GeneratorConfig config, String tableName, ApiDocument customApiDoc) {
        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(config)) {
            TableInfo tableInfo = analyzer.analyzeTable(tableName);
            if (tableInfo == null) {
                throw new IllegalStateException("表结构分析失败：" + tableName);
            }

            tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
            
            if (!tableName.equals(customApiDoc.getTableName())) {
                customApiDoc.setTableName(tableName);
            }
            
            SpringCodeGenerator codeGenerator = new SpringCodeGenerator(config.getOrCreateRenderConfig());
            
            return springCodeGeneratorService.previewCode(codeGenerator, tableInfo, customApiDoc, config);
        } catch (Exception e) {
            log.error("预览代码失败", e);
            throw new RuntimeException("预览代码失败: " + e.getMessage(), e);
        }
    }


}
