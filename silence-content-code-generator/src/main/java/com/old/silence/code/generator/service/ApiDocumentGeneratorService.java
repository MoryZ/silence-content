package com.old.silence.code.generator.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.ApiEndpoint;
import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.IndexInfo;
import com.old.silence.code.generator.model.Parameter;
import com.old.silence.code.generator.model.ResponseInfo;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.util.NameConverterUtils;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
@Service
public class ApiDocumentGeneratorService {

    private final Set<String> ignoreFields = Set.of("id", "createdDate", "updatedDate", "createdBy", "updatedBy");

    public ApiDocument generateDocument(TableInfo tableInfo) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());
        document.setEndpoints(generateEndpoints(tableInfo));
        return document;
    }

    private Map<String, ApiEndpoint> generateEndpoints(TableInfo tableInfo) {
        Map<String, ApiEndpoint> endpoints = new LinkedHashMap<>();
        var requestPath = tableNameToApiName(tableInfo.getTableName());
        String baseApiPath = "/api/v1/";
        StringBuilder fullApiPath = new StringBuilder(baseApiPath + requestPath);
        for (var primaryKey : tableInfo.getPrimaryKeys()) {
            fullApiPath.append("/{").append(primaryKey).append("}");
        }

        // 生成CRUD接口
        endpoints.put("分页查询", createEndpoint(tableInfo, "GET", fullApiPath.toString(), "获取%s列表",
                this::getQueryParams, null, this::createPaginationResponse));
        endpoints.put("根据主键查询", createEndpoint(tableInfo, "GET", fullApiPath.toString(), "根据主键获取%s详情",
                this::getPathParameters, null, this::createDetailResponse));
        endpoints.put("创建", createEndpoint(tableInfo, "POST", fullApiPath.toString(), "创建%s记录",
                this::getCreatedParameter,  this::getRequestExample, null));
        endpoints.put("更新", createEndpoint(tableInfo, "PUT", fullApiPath.toString(), "创建%s记录",
                this::getUpdatedParameter, this::getRequestExample,  null));
        endpoints.put("删除", createEndpoint(tableInfo, "DELETE", fullApiPath.toString(), "删除%s记录",
                this::getPathParameters, null, null));

        return endpoints;
    }

    private ApiEndpoint createEndpoint(TableInfo tableInfo, String method, String fullApiPath, String description, Function<TableInfo, List<Parameter>> paramFunction,
                                       Function<TableInfo, String> requestExampleFunction,
                                       Function<TableInfo, Object> responseFunction) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod(method);
        endpoint.setPath(fullApiPath);
        endpoint.setDescription(String.format(description, tableInfo.getComment()));

        // 设置查询参数
        if (paramFunction != null) {
            var queryParams = paramFunction.apply(tableInfo);
            endpoint.setParameters(queryParams);
        }

        // 设置请求示例
        if (requestExampleFunction != null) {
            var requestExample = requestExampleFunction.apply(tableInfo);
            endpoint.setRequestJsonExample(requestExample);
        }

        // 设置响应信息
        ResponseInfo<Object> response;
        if (responseFunction != null) {
            response = ResponseInfo.success(
                    String.format(description, "成功"),
                    responseFunction.apply(tableInfo)
            );
        } else {
            response = ResponseInfo.success(String.format(description, "成功"));
        }
        endpoint.setResponse(response);
        return endpoint;
    }


    private List<Parameter> getCreatedParameter(TableInfo tableInfo) {
        // 设置请求体参数
        List<Parameter> parameters = new ArrayList<>();
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            if (!ignoreFields.contains(columnInfo.getFieldName())) {
                var columnExampleValue = getExampleValue(columnInfo.getType());
                var parameterType = getParameterType(columnInfo);
                var requestBody = createParameter(columnInfo.getFieldName(), parameterType, columnInfo.getRequired(), columnInfo.getComment(), columnExampleValue);
                parameters.add(requestBody);
            }
        });
        return parameters;

    }

    private List<Parameter> getUpdatedParameter(TableInfo tableInfo) {
        // 设置参数
        List<Parameter> parameters = new ArrayList<>();

        // 请求体参数
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            if (!ignoreFields.contains(columnInfo.getFieldName())) {
                var columnExampleValue = getExampleValue(columnInfo.getType());
                String parameterType = getParameterType(columnInfo);

                var requestBody = createParameter(columnInfo.getFieldName(), parameterType,
                        columnInfo.getRequired(), columnInfo.getComment(), columnExampleValue);
                parameters.add(requestBody);
            }

        });

        return parameters;
    }


    private List<Parameter> getQueryParams(TableInfo tableInfo) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(createParameter("pageNo", "number", true, "页码", "1"));
        parameters.add(createParameter("pageSize", "number", true, "每页大小", "20"));
        parameters.add(createParameter("sort", "string", true, "排序字段", "-createdDate"));

        var stringCollectionMap = CollectionUtils.transformToMap(tableInfo.getColumnInfos(),
                ColumnInfo::getOriginalName, Function.identity());

        // 为可查询字段添加过滤参数
        for (IndexInfo indexInfo : tableInfo.getIndexes()) {
            for (var columnName : indexInfo.getColumnNames()) {
                var columnInfo = stringCollectionMap.get(columnName);
                String type = columnInfo.getType().toLowerCase();
                var exampleValue = getExampleValue(type);
                parameters.add(createParameter(columnInfo.getFieldName(), getParameterType(columnInfo),
                        false, "根据" + columnInfo.getComment() + "过滤", exampleValue));
            }
        }

        return parameters;
    }

    private List<Parameter> getPathParameters(TableInfo tableInfo) {
        // 设置路径参数
        List<Parameter> parameters = new ArrayList<>();
        String primaryKeyType = getPrimaryKeyType(tableInfo);
        var exampleValue = getExampleValue(primaryKeyType);
        parameters.add(createParameter("id", primaryKeyType, true, "记录ID", exampleValue));
        return parameters;
    }

    private Parameter createParameter(String name, String type, boolean required, String description, Object example) {
        Parameter param = new Parameter();
        param.setName(name);
        param.setType(type);
        param.setRequired(required);
        param.setDescription(description);
        param.setExample(example);
        return param;
    }


    private String getParameterType(ColumnInfo column) {
        String type = column.getType().toLowerCase();
        if (type.contains("int") || type.contains("decimal") || type.contains("float") || type.contains("double")) {
            return "number";
        } else if (type.contains("date") || type.contains("time")) {
            return "string"; // 日期时间通常用字符串传输
        } else if (type.contains("timestamp") || type.contains("datetime")) {
            return "string";
        } else if (type.contains("bit")) {
            return "boolean";
        } else {
            return "string";
        }
    }

    private String getPrimaryKeyType(TableInfo tableInfo) {
        if (tableInfo.getPrimaryKeys().isEmpty()) {
            return "number"; // 默认类型
        }

        // 获取第一个主键的类型
        String primaryKeyName = tableInfo.getPrimaryKeys().getFirst();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (column.getOriginalName().equals(primaryKeyName)) {
                return getParameterType(column);
            }
        }
        return "number";
    }

    private Map<String, Object> createPaginationResponse(TableInfo tableInfo) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("total", 100);
        response.put("data", List.of(createDetailResponse(tableInfo)));
        return response;
    }

    private Map<String, Object> createDetailResponse(TableInfo tableInfo) {
        Map<String, Object> response = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            response.put(column.getFieldName(), getExampleValue(column.getType()));
        }
        return response;
    }

    private String getRequestExample(TableInfo tableInfo) {
        Map<String, Object> example = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (!ignoreFields.contains(column.getFieldName())) {
                example.put(column.getFieldName(), getExampleValue(column.getType()));
            }
        }
        return mapToJsonString(example);
    }

    private Object getExampleValue(String columnType) {
        if (columnType.contains("int") || columnType.contains("bigint") || columnType.contains("tinyint")) {
            return 1;
        } else if (columnType.contains("varchar") || columnType.contains("text") || columnType.contains("char") || columnType.contains("json")) {
            return "字符串示例值";
        } else if (columnType.contains("date") || columnType.contains("time") || columnType.contains("datetime") || columnType.contains("timestamp")) {
            return "2024-01-01";
        } else if (columnType.contains("decimal") || columnType.contains("float") || columnType.contains("double")) {
            return 99.99;
        } else if (columnType.contains("bit")) {
            return true;
        }
        return "示例值";
    }

    private String mapToJsonString(Map<String, Object> map) {
        return JacksonMapper.getSharedInstance().toJson(map);
    }

    private String tableNameToApiName(String tableName) {
        return NameConverterUtils.toPluralVariableName(tableName);
    }

    /**
     * 生成接口文档
     *
     * @param apiDocument     接口文档
     * @param apiDocOutputDir 接口文档输出路径
     */
    public void generateApiDocs(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
        generateMarkdownDocument(apiDocument, apiDocOutputDir);
    }

    /**
     * 生成Markdown格式的API文档
     */
    public void generateMarkdownDocument(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
        StringBuilder markdown = new StringBuilder();

        // 文档标题
        markdown.append("# API接口文档\n\n");
        markdown.append("**数据表：** ").append(apiDocument.getTableName()).append("\n\n");

        // 规则说明
        if (apiDocument.getRules() != null && !apiDocument.getRules().isEmpty()) {
            markdown.append("## 通用规则\n\n");
            for (Map.Entry<String, Object> rule : apiDocument.getRules().entrySet()) {
                markdown.append("- **").append(rule.getKey()).append("**: ")
                        .append(rule.getValue()).append("\n");
            }
            markdown.append("\n");
        }

        // 接口列表
        markdown.append("## 接口列表\n\n");

        if (apiDocument.getEndpoints() != null) {
            int index = 1;
            for (Map.Entry<String, ApiEndpoint> entry : apiDocument.getEndpoints().entrySet()) {
                String endpointName = entry.getKey();
                ApiEndpoint endpoint = entry.getValue();

                markdown.append("### ").append(index++).append(". ").append(endpointName).append(endpoint.getPath()).append("\n\n");

                // 基础信息
                markdown.append("**请求方法：** `").append(endpoint.getMethod()).append("`  \n");
                markdown.append("**接口描述：** ").append(endpoint.getDescription()).append("\n\n");

                // 根据接口类型选择不同的展示策略
                markdown.append(generateEndpointContent(endpoint));
            }
        }

        saveDocumentToFile(markdown.toString(), apiDocOutputDir, apiDocument.getTableName() + ".md");
    }

    /**
     * 根据接口类型生成不同的内容展示
     */
    private String generateEndpointContent(ApiEndpoint endpoint) {
        StringBuilder sb = new StringBuilder();

        String method = endpoint.getMethod().toUpperCase();

        // 请求参数部分
        if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
            sb.append(generateParametersSection(endpoint, method));
        }

        // 响应信息部分
        if (endpoint.getResponse() != null) {
            sb.append(generateResponseSection(endpoint, method));
        }

        return sb.toString();
    }

    /**
     * 生成请求参数部分，根据方法类型决定默认展开状态
     */
    private String generateParametersSection(ApiEndpoint endpoint, String method) {
        StringBuilder sb = new StringBuilder();

        sb.append("#### 请求参数\n\n");

        // 根据方法类型决定默认展开状态
        boolean defaultOpen = isCreateOrUpdateMethod(method);

        sb.append("<details").append(defaultOpen ? " open" : "").append(">\n");
        sb.append("<summary>📋 参数列表（点击").append(defaultOpen ? "折叠" : "展开").append("）</summary>\n\n");
        sb.append("| 参数名 | 类型 | 是否必填 | 描述 | 示例 |\n");
        sb.append("|--------|------|----------|------|------|\n");

        for (Parameter param : endpoint.getParameters()) {
            sb.append("| ").append(escapeMarkdown(param.getName()))
                    .append(" | ").append(escapeMarkdown(param.getType()))
                    .append(" | ").append(param.isRequired() ? "是" : "否")
                    .append(" | ").append(escapeMarkdown(param.getDescription() != null ? param.getDescription() : ""))
                    .append(" | ").append(escapeMarkdown(param.getExample() != null ? param.getExample().toString() : ""))
                    .append(" |\n");
        }
        sb.append("</details>\n\n");

        // 请求源码（默认折叠）
        sb.append("<details>\n");
        sb.append("<summary>📄 请求源码（点击展开）</summary>\n\n");
        sb.append("```").append(getLanguageByMethod(endpoint.getMethod())).append("\n");
        sb.append(generateRequestExample(endpoint));
        sb.append("\n```\n");
        sb.append("</details>\n\n");

        return sb.toString();
    }

    /**
     * 生成响应信息部分，查询接口默认展开出参示例
     */
    private String generateResponseSection(ApiEndpoint endpoint, String method) {
        StringBuilder sb = new StringBuilder();

        sb.append("#### 响应信息\n\n");
        sb.append("**状态码：** ").append(endpoint.getResponse().getCode()).append("  \n");
        sb.append("**提示信息：** ").append(endpoint.getResponse().getMessage()).append("  \n\n");

        // 查询接口默认展开出参示例，其他接口默认折叠
        boolean defaultOpen = isQueryMethod(method);

        sb.append("<details").append(defaultOpen ? " open" : "").append(">\n");
        sb.append("<summary>📄 响应示例（点击").append(defaultOpen ? "折叠" : "展开").append("）</summary>\n\n");
        sb.append("```json\n");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonResponse = mapper.writeValueAsString(endpoint.getResponse().getData());
            sb.append(jsonResponse);
        } catch (Exception e) {
            sb.append("无法序列化响应数据");
        }

        sb.append("\n```\n");
        sb.append("</details>\n\n");

        // 如果是查询接口，额外添加分页响应示例
        if (isQueryMethod(method) && isListEndpoint(endpoint)) {
            sb.append("<details>\n");
            sb.append("<summary>📄 分页响应示例（点击展开）</summary>\n\n");
            sb.append("```json\n");
            sb.append(generatePaginationResponseExample(endpoint.getResponse()));
            sb.append("\n```\n");
            sb.append("</details>\n\n");
        }

        return sb.toString();
    }

    /**
     * 判断是否为创建或更新接口
     */
    private boolean isCreateOrUpdateMethod(String method) {
        return "POST".equals(method) || "PUT".equals(method) || "GET".equals(method);
    }

    /**
     * 判断是否为查询接口
     */
    private boolean isQueryMethod(String method) {
        return "GET".equals(method);
    }

    /**
     * 判断是否为列表查询接口
     */
    private boolean isListEndpoint(ApiEndpoint endpoint) {
        return endpoint.getPath() != null &&
                (endpoint.getPath().contains("list") ||
                        endpoint.getPath().contains("query") ||
                        endpoint.getPath().matches(".*[/]$") || // 以/结尾的路径通常是列表接口
                        !endpoint.getPath().contains("{")); // 不包含路径参数的通常是列表接口
    }

    /**
     * 生成分页响应示例
     */
    private String generatePaginationResponseExample(ResponseInfo response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, Object> paginationResponse = new LinkedHashMap<>();
            paginationResponse.put("code", response.getCode());
            paginationResponse.put("message", response.getMessage());

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("total", 100);
            data.put("pageNo", 1);
            data.put("pageSize", 20);
            data.put("totalPages", 5);

            // 复制原始响应数据作为列表项
            if (response.getData() instanceof Map) {
                List<Object> items = new ArrayList<>();
                items.add(response.getData()); // 添加一个示例项
                data.put("data", items);
            } else {
                data.put("data", Collections.singletonList(response.getData()));
            }

            paginationResponse.put("data", data);

            return mapper.writeValueAsString(paginationResponse);
        } catch (Exception e) {
            return "无法生成分页响应示例";
        }
    }

    private String generateRequestExample(ApiEndpoint endpoint) {
        StringBuilder example = new StringBuilder();

        String method = endpoint.getMethod().toUpperCase();
        switch (method) {
            case "GET":
                example.append("GET ").append(endpoint.getPath());
                if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
                    List<String> queryParams = new ArrayList<>();
                    for (Parameter param : endpoint.getParameters()) {
                        if (isNotPathParameter(param.getName(), endpoint.getPath()) && param.getExample() != null) {
                            queryParams.add(param.getName() + "=" + param.getExample());
                        }
                    }
                    if (!queryParams.isEmpty()) {
                        example.append("?").append(String.join("&", queryParams));
                    }
                }
                example.append("\nContent-Type: application/json");
                break;

            case "POST":
            case "PUT":
                example.append(method).append(" ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json\n\n");

                try {
                    // 生成请求体示例
                    Map<String, Object> requestBody = new LinkedHashMap<>();
                    if (endpoint.getParameters() != null) {
                        for (Parameter param : endpoint.getParameters()) {
                            if (isNotPathParameter(param.getName(), endpoint.getPath()) && param.getExample() != null) {
                                requestBody.put(param.getName(), param.getExample());
                            }
                        }
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(SerializationFeature.INDENT_OUTPUT);
                    String jsonBody = mapper.writeValueAsString(requestBody);
                    example.append(jsonBody);
                } catch (Exception e) {
                    example.append("{}");
                }
                break;

            case "DELETE":
                example.append("DELETE ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json");
                break;

            default:
                example.append(method).append(" ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json");
        }

        return example.toString();
    }

    private boolean isNotPathParameter(String paramName, String path) {
        return !path.contains("{" + paramName + "}");
    }

    private String getLanguageByMethod(String method) {
        return switch (method.toUpperCase()) {
            case "GET", "POST", "PUT", "DELETE" -> "http";
            default -> "json";
        };
    }

    private String escapeMarkdown(String text) {
        if (text == null) return "";
        return text.replace("|", "\\|")
                .replace("*", "\\*")
                .replace("_", "\\_")
                .replace("`", "\\`")
                .replace("#", "\\#")
                .replace("-", "\\-")
                .replace("+", "\\+")
                .replace(".", "\\.")
                .replace("!", "\\!");
    }

    /**
     * 将文档保存到文件
     */
    private void saveDocumentToFile(String content, String apiDocOutputDir, String filename) throws Exception {

        File outputFile = new File(apiDocOutputDir, filename);
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
        }

    }
}
