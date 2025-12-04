package com.old.silence.code.generator.service;

import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.IndexInfo;
import com.old.silence.content.code.generator.model.Parameter;
import com.old.silence.content.code.generator.model.ResponseInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.ApiDocumentGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@DisplayName("ApiDocumentGeneratorService 测试")
class ApiDocumentGeneratorServiceTest {

    private ApiDocumentGeneratorService service;

    @BeforeEach
    void setUp() {
        this.service = new ApiDocumentGeneratorService();
    }

    @Test
    @DisplayName("生成基础API文档")
    void shouldGenerateBasicDocument() {
        TableInfo tableInfo = createTestTableInfo();

        ApiDocument document = service.generateDocument(tableInfo);

        assertNotNull(document);
        assertEquals("content", document.getTableName());
        assertNotNull(document.getEndpoints());
        assertFalse(document.getEndpoints().isEmpty());
    }

    @Test
    @DisplayName("生成完整的CRUD接口")
    void shouldGenerateCrudEndpoints() {
        ApiDocument document = service.generateDocument(createTestTableInfo());

        Map<String, ApiEndpoint> endpoints = document.getEndpoints();
        assertNotNull(endpoints);
        assertAll(
            () -> assertTrue(endpoints.containsKey("分页查询")),
            () -> assertTrue(endpoints.containsKey("根据主键查询")),
            () -> assertTrue(endpoints.containsKey("创建")),
            () -> assertTrue(endpoints.containsKey("更新")),
            () -> assertTrue(endpoints.containsKey("删除"))
        );

        endpoints.values().forEach(endpoint -> {
            assertNotNull(endpoint.getMethod(), "接口请求方法不应为空");
            assertNotNull(endpoint.getPath(), "接口路径不应为空");
        });
    }

    @Test
    @DisplayName("生成接口参数")
    void shouldGenerateEndpointParameters() {
        ApiDocument document = service.generateDocument(createTestTableInfo());
        ApiEndpoint queryEndpoint = document.getEndpoints().get("分页查询");
        ApiEndpoint createEndpoint = document.getEndpoints().get("创建");
        ApiEndpoint detailEndpoint = document.getEndpoints().get("根据主键查询");

        assertNotNull(queryEndpoint);
        assertNotNull(createEndpoint);
        assertNotNull(detailEndpoint);

        assertTrue(queryEndpoint.getParameters().stream().map(Parameter::getName).anyMatch("pageNo"::equals));
        assertTrue(queryEndpoint.getParameters().stream().map(Parameter::getName).anyMatch("pageSize"::equals));

        assertFalse(createEndpoint.getParameters().isEmpty(), "创建接口参数不应为空");
        assertTrue(detailEndpoint.getPath().contains("{id}"));
    }

    @Test
    @DisplayName("生成接口响应信息")
    void shouldGenerateEndpointResponses() {
        ApiDocument document = service.generateDocument(createTestTableInfo());

        document.getEndpoints().forEach((name, endpoint) -> {
            ResponseInfo<?> response = endpoint.getResponse();
            assertNotNull(response, name + " 响应信息不应为空");
            assertNotNull(response.getCode(), name + " 响应码不应为空");
            assertNotNull(response.getMessage(), name + " 响应消息不应为空");
        });

        assertNotNull(document.getEndpoints().get("分页查询").getResponse().getData());
        assertNotNull(document.getEndpoints().get("根据主键查询").getResponse().getData());
    }

    @Test
    @DisplayName("生成Markdown文档调用流程")
    void shouldDelegateMarkdownGeneration() throws Exception {
        ApiDocumentGeneratorService spyService = Mockito.spy(service);
        ApiDocument document = spyService.generateDocument(createTestTableInfo());

        Mockito.doNothing().when(spyService).generateMarkdownDocument(any(ApiDocument.class), any(String.class));

        spyService.generateApiDocs(document, "outputDir");

        Mockito.verify(spyService).generateMarkdownDocument(eq(document), eq("outputDir"));
    }

    @Test
    @DisplayName("生成Markdown文档文件")
    void shouldGenerateMarkdownFile(@TempDir Path tempDir) throws Exception {
        ApiDocument document = service.generateDocument(createTestTableInfo());

        service.generateApiDocs(document, tempDir.toString());

        Path markdownFile = tempDir.resolve("content.md");
        assertTrue(Files.exists(markdownFile), "应生成Markdown文件");

        String content = Files.readString(markdownFile);
        assertNotNull(content);
        assertFalse(content.isBlank());
        assertTrue(content.contains("API接口文档"));
        assertTrue(content.contains("分页查询"));
    }

    private TableInfo createTestTableInfo() {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("content");
        tableInfo.setComment("内容表");
        tableInfo.setSchema("silence-content");

        List<ColumnInfo> columns = new ArrayList<>();

        ColumnInfo id = new ColumnInfo();
        id.setOriginalName("id");
        id.setFieldName("id");
        id.setType("bigint");
        id.setLength(20);
        id.setNullable(false);
        id.setRequired(true);
        id.setPrimaryKey(true);
        id.setAutoIncrement(true);
        id.setComment("内容ID");
        columns.add(id);

        ColumnInfo title = new ColumnInfo();
        title.setOriginalName("title");
        title.setFieldName("title");
        title.setType("varchar");
        title.setLength(100);
        title.setNullable(false);
        title.setRequired(true);
        title.setComment("标题");
        columns.add(title);

        ColumnInfo subtitle = new ColumnInfo();
        subtitle.setOriginalName("subtitle");
        subtitle.setFieldName("subtitle");
        subtitle.setType("varchar");
        subtitle.setLength(200);
        subtitle.setNullable(true);
        subtitle.setRequired(false);
        subtitle.setComment("副标题");
        columns.add(subtitle);

        ColumnInfo content = new ColumnInfo();
        content.setOriginalName("content");
        content.setFieldName("content");
        content.setType("text");
        content.setNullable(true);
        content.setRequired(false);
        content.setComment("内容正文");
        columns.add(content);

        ColumnInfo metadata = new ColumnInfo();
        metadata.setOriginalName("metadata");
        metadata.setFieldName("metadata");
        metadata.setType("json");
        metadata.setNullable(true);
        metadata.setRequired(false);
        metadata.setComment("元数据（JSON格式）");
        columns.add(metadata);

        ColumnInfo status = new ColumnInfo();
        status.setOriginalName("status");
        status.setFieldName("status");
        status.setType("tinyint");
        status.setLength(1);
        status.setNullable(false);
        status.setRequired(true);
        status.setComment("状态：1-已保存，2-发布中，3-已发布");
        columns.add(status);

        ColumnInfo price = new ColumnInfo();
        price.setOriginalName("price");
        price.setFieldName("price");
        price.setType("decimal");
        price.setLength(10);
        price.setNullable(true);
        price.setRequired(false);
        price.setComment("价格");
        columns.add(price);

        ColumnInfo publishedAt = new ColumnInfo();
        publishedAt.setOriginalName("published_at");
        publishedAt.setFieldName("publishedAt");
        publishedAt.setType("datetime");
        publishedAt.setNullable(true);
        publishedAt.setRequired(false);
        publishedAt.setComment("发布时间");
        columns.add(publishedAt);

        ColumnInfo createdAt = new ColumnInfo();
        createdAt.setOriginalName("created_at");
        createdAt.setFieldName("createdAt");
        createdAt.setType("timestamp");
        createdAt.setNullable(false);
        createdAt.setRequired(true);
        createdAt.setComment("创建时间");
        columns.add(createdAt);

        ColumnInfo isPublished = new ColumnInfo();
        isPublished.setOriginalName("is_published");
        isPublished.setFieldName("isPublished");
        isPublished.setType("bit");
        isPublished.setLength(1);
        isPublished.setNullable(false);
        isPublished.setRequired(true);
        isPublished.setComment("是否已发布");
        columns.add(isPublished);

        ColumnInfo description = new ColumnInfo();
        description.setOriginalName("description");
        description.setFieldName("description");
        description.setType("text");
        description.setNullable(true);
        description.setRequired(false);
        description.setComment("描述信息");
        columns.add(description);

        ColumnInfo author = new ColumnInfo();
        author.setOriginalName("author");
        author.setFieldName("author");
        author.setType("varchar");
        author.setLength(50);
        author.setNullable(true);
        author.setRequired(false);
        author.setDefaultValue("匿名");
        author.setComment("作者");
        columns.add(author);

        tableInfo.setColumnInfos(columns);

        List<String> primaryKeys = new ArrayList<>();
        primaryKeys.add("id");
        tableInfo.setPrimaryKeys(primaryKeys);

        List<IndexInfo> indexes = new ArrayList<>();

        IndexInfo titleIndex = new IndexInfo();
        titleIndex.setIndexName("idx_title");
        titleIndex.setUnique(false);
        titleIndex.addColumnName("title");
        indexes.add(titleIndex);

        IndexInfo statusIndex = new IndexInfo();
        statusIndex.setIndexName("idx_status");
        statusIndex.setUnique(false);
        statusIndex.addColumnName("status");
        indexes.add(statusIndex);

        IndexInfo publishedAtIndex = new IndexInfo();
        publishedAtIndex.setIndexName("idx_published_at");
        publishedAtIndex.setUnique(false);
        publishedAtIndex.addColumnName("published_at");
        indexes.add(publishedAtIndex);

        IndexInfo uniqueIndex = new IndexInfo();
        uniqueIndex.setIndexName("uk_title_status");
        uniqueIndex.setUnique(true);
        uniqueIndex.addColumnName("title");
        uniqueIndex.addColumnName("status");
        indexes.add(uniqueIndex);

        tableInfo.setIndexes(indexes);

        return tableInfo;
    }
}
