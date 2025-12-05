package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.dto.CodePreviewResponse;
import com.old.silence.content.code.generator.dto.Step1TableInfoResponse;
import com.old.silence.content.code.generator.dto.Step2ApiDocResponse;
import com.old.silence.content.code.generator.dto.Step3CodePreviewResponse;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 验证服务 - 提供步骤拆解的API
 *
 * @author moryzang
 */
@Service
public class ValidationService {

    private static final Logger log = LoggerFactory.getLogger(ValidationService.class);

    private final ApiDocumentGeneratorService apiDocumentGeneratorService;
    private final BatchGenerationService batchGenerationService;
    private final ImportAnalyzer importAnalyzer;

    public ValidationService(ApiDocumentGeneratorService apiDocumentGeneratorService,
                             BatchGenerationService batchGenerationService,
                             ImportAnalyzer importAnalyzer) {
        this.apiDocumentGeneratorService = apiDocumentGeneratorService;
        this.batchGenerationService = batchGenerationService;
        this.importAnalyzer = importAnalyzer;
    }

    /**
     * 步骤1：查看表结构信息
     */
    public Step1TableInfoResponse validateStep1TableInfo(String tableName, GeneratorConfig globalConfig) {
        log.info("步骤1：查看表结构信息，表名: {}", tableName);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(globalConfig)) {
            // 验证表是否存在
            Map<String, String> allTables = analyzer.getTablesWithComments();
            if (!allTables.containsKey(tableName)) {
                throw new IllegalArgumentException("表不存在：" + tableName);
            }

            // 获取表信息
            TableInfo tableInfo = analyzer.analyzeTable(tableName);

            // 构建响应
            Step1TableInfoResponse response = new Step1TableInfoResponse();
            response.setTableName(tableInfo.getTableName());
            response.setTableComment(tableInfo.getComment());
            response.setPrimaryKeys(tableInfo.getPrimaryKeys());
            response.setColumns(tableInfo.getColumnInfos());

            // 转换索引信息
            List<Step1TableInfoResponse.IndexInfo> indexes = tableInfo.getIndexes().stream()
                    .map(idx -> new Step1TableInfoResponse.IndexInfo(
                            idx.getIndexName(),
                            idx.getColumnNames(),
                            idx.isUnique()
                    ))
                    .collect(Collectors.toList());
            response.setIndexes(indexes);

            // 转换外键信息（如果支持）
            response.setForeignKeys(new ArrayList<>());

            log.info("步骤1完成：表 {} 共 {} 列，{} 个索引", tableName, tableInfo.getColumnInfos().size(), indexes.size());
            return response;

        } catch (SQLException e) {
            log.error("步骤1失败：读取表信息出错", e);
            throw new RuntimeException("读取表信息失败：" + e.getMessage(), e);
        }
    }

    /**
     * 步骤2：查看生成的API文档
     */
    public Step2ApiDocResponse validateStep2ApiDoc(String tableName, GeneratorConfig globalConfig, ApiDocument customApiDoc) {
        log.info("步骤2：查看API文档，表名: {}，是否自定义: {}", tableName, customApiDoc != null);

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(globalConfig)) {
            // 验证表是否存在
            Map<String, String> allTables = analyzer.getTablesWithComments();
            if (!allTables.containsKey(tableName)) {
                throw new IllegalArgumentException("表不存在：" + tableName);
            }

            String tableComment = allTables.get(tableName);

            // 生成或使用自定义API文档
            ApiDocument apiDoc;
            boolean isCustom;
            if (customApiDoc != null) {
                apiDoc = customApiDoc;
                isCustom = true;
                log.info("使用自定义API文档，接口数: {}", apiDoc.getEndpoints().size());
            } else {
                TableInfo tableInfo = analyzer.analyzeTable(tableName);
                apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
                isCustom = false;
                log.info("生成标准CRUD API文档，接口数: {}", apiDoc.getEndpoints().size());
            }

            // 构建响应
            Step2ApiDocResponse response = new Step2ApiDocResponse();
            response.setTableName(tableName);
            response.setTableComment(tableComment);
            response.setApiDocument(apiDoc);
            response.setCustomApi(isCustom);

            log.info("步骤2完成：表 {} API文档包含 {} 个接口", tableName, apiDoc.getEndpoints().size());
            return response;

        } catch (SQLException e) {
            log.error("步骤2失败：生成API文档出错", e);
            throw new RuntimeException("生成API文档失败：" + e.getMessage(), e);
        }
    }

    /**
     * 步骤3：预览生成的代码（包含导入分析和排序建议）
     */
    public Step3CodePreviewResponse validateStep3PreviewCode(String tableName, GeneratorConfig globalConfig, ApiDocument customApiDoc) {
        log.info("步骤3：预览代码，表名: {}，是否自定义API: {}", tableName, customApiDoc != null);

        // 调用现有的预览方法
        CodePreviewResponse basePreview;
        if (customApiDoc != null) {
            basePreview = batchGenerationService.previewCodeWithCustomApi(globalConfig, tableName, customApiDoc);
        } else {
            basePreview = batchGenerationService.previewCode(globalConfig, tableName);
        }

        // 创建增强响应
        Step3CodePreviewResponse response = new Step3CodePreviewResponse();
        response.setTableName(basePreview.getTableName());
        response.setTableComment(basePreview.getTableComment());
        response.setFiles(basePreview.getFiles());

        // 分析导入
        ImportAnalyzer.ImportAnalysisResult importAnalysis = importAnalyzer.analyzeImports(basePreview.getFiles());
        response.setImportSuggestions(importAnalysis.suggestions());
        response.setMissingImportWarnings(importAnalysis.warnings());

        // 添加排序建议
        List<String> sortingSuggestions = new ArrayList<>();
        sortingSuggestions.add("建议按以下顺序组织代码：常量 → 字段 → 构造器 → public方法 → protected方法 → private方法");
        sortingSuggestions.add("建议按以下顺序组织导入：java.* → javax.* → com.* → org.* → 自定义包");
        response.setSortingSuggestions(sortingSuggestions);

        log.info("步骤3完成：表 {} 共生成 {} 个文件，发现 {} 个导入建议",
                tableName,
                basePreview.getFiles().size(),
                importAnalysis.suggestions().values().stream().mapToInt(List::size).sum());

        return response;
    }
}
