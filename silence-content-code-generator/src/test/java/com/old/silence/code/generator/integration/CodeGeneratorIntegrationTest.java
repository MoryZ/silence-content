package com.old.silence.code.generator.integration;

import com.old.silence.content.code.generator.config.NamingRulesConfig;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.parser.SQLParser;
import com.old.silence.content.code.generator.parser.impl.JSqlParserImpl;
import com.old.silence.content.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.content.code.generator.service.RuleProcessorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@DisplayName("代码生成器集成流程测试")
class CodeGeneratorIntegrationTest {

    private final SQLParser sqlParser = new JSqlParserImpl();
    private final ApiDocumentGeneratorService apiDocService = new ApiDocumentGeneratorService();
    private final RuleProcessorService ruleProcessorService = new RuleProcessorService(new NamingRulesConfig());

    @Test
    @DisplayName("通过SQL解析生成API文档并导出Markdown")
    void shouldGenerateApiDocFromSql(@TempDir Path tempDir) throws Exception {
        TableInfo tableInfo = sqlParser.parseCreateTable(buildContentTableSql());
        assertNotNull(tableInfo, "SQL解析结果不应为空");
        tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        ApiDocument apiDocument = apiDocService.generateDocument(tableInfo);
        assertNotNull(apiDocument);
        assertEquals("content", apiDocument.getTableName());
        assertEquals(5, apiDocument.getEndpoints().size());

        apiDocService.generateApiDocs(apiDocument, tempDir.toString());
        Path markdownFile = tempDir.resolve("content.md");
        assertTrue(Files.exists(markdownFile));
        assertFalse(Files.readString(markdownFile).isBlank());
    }

    @Test
    @DisplayName("通过SQLAnalyzer生成API文档")
    void shouldGenerateApiDocFromAnalyzer() throws Exception {
        TableInfo analyzerTable = sqlParser.parseCreateTable(buildContentTableSql());
        analyzerTable.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

        SQLAnalyzer analyzer = Mockito.mock(SQLAnalyzer.class);
        Mockito.when(analyzer.analyzeTable("content")).thenReturn(analyzerTable);

        TableInfo tableInfo = analyzer.analyzeTable("content");
        ApiDocument apiDocument = apiDocService.generateDocument(tableInfo);

        assertNotNull(apiDocument);
        assertEquals(5, apiDocument.getEndpoints().size());
        Mockito.verify(analyzer).analyzeTable(eq("content"));
    }

    @Test
    @DisplayName("SQL解析与SQLAnalyzer结果一致性")
    void shouldAlignSqlAndAnalyzerResults() throws Exception {
        TableInfo sqlTable = sqlParser.parseCreateTable(buildContentTableSql());
        sqlTable.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
        ApiDocument sqlDocument = apiDocService.generateDocument(sqlTable);

        TableInfo analyzerTable = sqlParser.parseCreateTable(buildContentTableSql());
        analyzerTable.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
        ApiDocument analyzerDocument = apiDocService.generateDocument(analyzerTable);

        assertEquals(sqlTable.getTableName(), analyzerTable.getTableName());
        assertEquals(sqlTable.getColumnInfos().size(), analyzerTable.getColumnInfos().size());
        assertEquals(sqlDocument.getEndpoints().size(), analyzerDocument.getEndpoints().size());
    }

    private String buildContentTableSql() {
        return """
            CREATE TABLE `content` (
              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '内容ID',
              `title` varchar(100) NOT NULL COMMENT '标题',
              `subtitle` varchar(200) NULL COMMENT '副标题',
              `content` text NULL COMMENT '内容正文',
              `metadata` json NULL COMMENT '元数据',
              `status` tinyint NOT NULL COMMENT '状态：1-已保存，2-发布中，3-已发布',
              `price` decimal(10,2) NULL COMMENT '价格',
              `published_at` datetime NULL COMMENT '发布时间',
              `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
              `is_published` bit NOT NULL DEFAULT 0 COMMENT '是否发布',
              `description` text NULL COMMENT '描述',
              `author` varchar(50) NULL DEFAULT '匿名' COMMENT '作者',
              PRIMARY KEY (`id`),
              KEY `idx_title` (`title`),
              UNIQUE KEY `uk_author` (`author`)
            ) ENGINE = InnoDB COMMENT = '内容表';
            """;
    }
}
