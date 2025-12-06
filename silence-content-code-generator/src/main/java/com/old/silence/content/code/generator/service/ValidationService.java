package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.config.DatabaseConfig;
import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.dto.CodePreviewResponse;
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
    public List<TableInfo> validateStep1TableInfo(DatabaseConfig databaseConfig) {

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(databaseConfig)) {
            // 验证表是否存在
            Map<String, String> allTables = analyzer.getTablesWithComments();

            return allTables.keySet().stream().map(tableName -> {
                // 获取表信息
                TableInfo tableInfo;
                try {
                    tableInfo = analyzer.analyzeTable(tableName);
                } catch (SQLException e) {
                    log.error("失败：读取表信息出错", e);
                    throw new RuntimeException(e);
                }

                log.info("完成：表 {} 共 {} 列，{} 个索引", tableName, tableInfo.getColumnInfos().size(), tableInfo.getIndexes().size());
                return tableInfo;
            }).toList();



        } catch (SQLException e) {
            throw new RuntimeException("读取表信息失败：" + e.getMessage(), e);
        }
    }

    /**
     * 步骤2：查看生成的API文档
     */
    public ApiDocument validateStep2ApiDoc(TableInfo tableInfo) {
        // 生成或使用自定义API文档
        return apiDocumentGeneratorService.generateDocument(tableInfo);
    }

    /**
     * 步骤3：预览生成的代码（包含导入分析和排序建议）
     */
    public Step3CodePreviewResponse validateStep3PreviewCode(ApiDocument customApiDoc) {
        var tableName = customApiDoc.getTableName();

        // 调用现有的预览方法
        CodePreviewResponse basePreview = batchGenerationService.previewCodeWithCustomApi(customApiDoc);

        // 创建增强响应
        Step3CodePreviewResponse response = new Step3CodePreviewResponse();
        response.setTableName(basePreview.getTableName());
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
