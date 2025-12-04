package com.old.silence.code.generator.llm;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.content.code.generator.strategy.CodeGenerationStrategy;
import com.old.silence.content.code.generator.util.PromptBuilder;
import com.old.silence.content.code.generator.util.TemplateRetriever;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@DisplayName("DeepSeek 提示词构建测试")
class DeepSeekPromptBuilderTest {

    @Test
    @DisplayName("构建包含模板提示的提示词")
    void shouldBuildPromptWithTemplateHints() {
        TableInfo tableInfo = createTestTableInfo();
        ApiDocumentGeneratorService apiDocService = new ApiDocumentGeneratorService();
        ApiDocument apiDocument = apiDocService.generateDocument(tableInfo);

        TemplateRetriever templateRetriever = Mockito.mock(TemplateRetriever.class);
        Mockito.when(templateRetriever.retrieveHints(any(), any(), any()))
            .thenReturn("/* 模板参考 */");

        PromptBuilder promptBuilder = new PromptBuilder(templateRetriever);
        String prompt = promptBuilder.buildPrompt(tableInfo, apiDocument, createTestConfig(), CodeGenerationStrategy.CodeLayer.SERVICE);

        assertNotNull(prompt);
        assertTrue(prompt.contains("表名: content"));
        assertTrue(prompt.contains("模板参考"));

        Mockito.verify(templateRetriever).retrieveHints(eq(CodeGenerationStrategy.CodeLayer.SERVICE), eq(tableInfo), eq(apiDocument));
    }

    @Test
    @DisplayName("生成DeepSeek测试用API文档")
    void shouldGenerateApiDocument() {
        TableInfo tableInfo = createTestTableInfo();
        ApiDocumentGeneratorService apiDocService = new ApiDocumentGeneratorService();

        ApiDocument apiDocument = apiDocService.generateDocument(tableInfo);

        assertNotNull(apiDocument);
        assertEquals("content", apiDocument.getTableName());
        assertNotNull(apiDocument.getEndpoints());
        assertEquals(5, apiDocument.getEndpoints().size());
    }

    private TableInfo createTestTableInfo() {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("content");
        tableInfo.setComment("内容表");

        List<ColumnInfo> columns = new ArrayList<>();

        ColumnInfo id = new ColumnInfo();
        id.setOriginalName("id");
        id.setFieldName("id");
        id.setType("bigint");
        id.setPrimaryKey(true);
        id.setAutoIncrement(true);
        id.setComment("内容ID");
        columns.add(id);

        ColumnInfo title = new ColumnInfo();
        title.setOriginalName("title");
        title.setFieldName("title");
        title.setType("varchar");
        title.setLength(100);
        title.setRequired(true);
        title.setComment("标题");
        columns.add(title);

        ColumnInfo content = new ColumnInfo();
        content.setOriginalName("content");
        content.setFieldName("content");
        content.setType("text");
        content.setRequired(false);
        content.setComment("内容正文");
        columns.add(content);

        ColumnInfo status = new ColumnInfo();
        status.setOriginalName("status");
        status.setFieldName("status");
        status.setType("tinyint");
        status.setRequired(true);
        status.setComment("状态：1-已保存，2-发布中，3-已发布");
        columns.add(status);

        tableInfo.setColumnInfos(columns);
        tableInfo.setPrimaryKeys(List.of("id"));

        return tableInfo;
    }

    private GeneratorConfig createTestConfig() {
        GeneratorConfig config = new GeneratorConfig();
        config.setBasePackage("com.old.silence.content");
        config.setServiceOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");
        config.setConsoleOutputDir("silence-content-console/src/main/java");
        return config;
    }
}
