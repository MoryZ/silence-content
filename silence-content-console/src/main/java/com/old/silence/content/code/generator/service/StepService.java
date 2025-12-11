package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.facade.CodeGeneratorFacade;
import com.old.silence.content.code.generator.vo.CodePreviewResponse;
import com.old.silence.content.code.generator.vo.Step3CodePreviewResponse;
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

@Service
public class StepService {

    private static final Logger log = LoggerFactory.getLogger(StepService.class);

    private final ApiDocumentGeneratorService apiDocumentGeneratorService;
    private final SpringCodeGeneratorService springCodeGeneratorService;
    private final ImportAnalyzer importAnalyzer;

    public StepService(ApiDocumentGeneratorService apiDocumentGeneratorService,
                       SpringCodeGeneratorService springCodeGeneratorService,
                       ImportAnalyzer importAnalyzer) {
        this.apiDocumentGeneratorService = apiDocumentGeneratorService;
        this.springCodeGeneratorService = springCodeGeneratorService;
        this.importAnalyzer = importAnalyzer;
    }

    public List<TableInfo> validateStep1TableInfo(DatabaseConfig databaseConfig) {

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer(databaseConfig)) {
            Map<String, String> allTables = analyzer.getTablesWithComments();

            return allTables.keySet().stream().map(tableName -> {
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

    public ApiDocument validateStep2ApiDoc(TableInfo tableInfo) {
        return apiDocumentGeneratorService.generateDocument(tableInfo);
    }

    public Step3CodePreviewResponse validateStep3PreviewCode(ApiDocument customApiDoc, List<CodeGenModuleConfig> codeGenModuleConfigs) {
        var tableName = customApiDoc.getTableName();

        CodeGenerator codeGenerator = CodeGeneratorFacade.ofDefault();
        CodePreviewResponse basePreview = springCodeGeneratorService.previewCode(codeGenerator, customApiDoc, codeGenModuleConfigs);

        Step3CodePreviewResponse response = new Step3CodePreviewResponse();
        response.setTableName(basePreview.getTableName());
        response.setFiles(basePreview.getFiles());

        ImportAnalyzer.ImportAnalysisResult importAnalysis = importAnalyzer.analyzeImports(basePreview.getFiles());
        response.setImportSuggestions(importAnalysis.suggestions());
        response.setMissingImportWarnings(importAnalysis.warnings());

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
