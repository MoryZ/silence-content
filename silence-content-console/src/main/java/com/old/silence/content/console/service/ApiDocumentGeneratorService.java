package com.old.silence.content.console.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.IndexInfo;
import com.old.silence.content.code.generator.model.Parameter;
import com.old.silence.content.code.generator.model.ResponseInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.ExampleValueGenerator;
import com.old.silence.content.code.generator.util.NameConverterUtils;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API文档生成服务
 * 负责根据表结构信息生成RESTful API文档（支持Markdown格式）
 * 
 * <p>主要功能：
 * <ul>
 *   <li>生成CRUD接口定义（创建、查询、更新、删除）</li>
 *   <li>智能识别查询字段，生成查询参数（基于索引）</li>
 *   <li>支持时间范围查询（datetime/timestamp类型）</li>
 *   <li>生成示例请求和响应</li>
 *   <li>自动处理布尔字段的is前缀</li>
 *   <li>支持复合主键</li>
 * </ul>
 * 
 * <p>已知问题和改进点：
 * <ul>
 *   <li>✅ 分页参数应该是可选的，不是必填</li>
 *   <li>✅ 查询参数仅基于索引字段，未索引字段不生成</li>
 *   <li>✅ 时间范围查询仅支持datetime/timestamp，不包括date</li>
 *   <li>⚠️ 枚举字段未特殊处理，需配合EnumConfig</li>
 *   <li>⚠️ 外键关联未处理，需要扩展</li>
 * </ul>
 * 
 * @author moryzang
 */
@Service
public class ApiDocumentGeneratorService {

    /**
     * 需要忽略的字段（审计字段和主键）
     * 这些字段不会出现在新增和编辑接口的请求参数中
     */
    private final Set<String> ignoreFields = Set.of(
        "id",                           // 主键
        "createdDate", "created_date",  // 创建时间
        "updatedDate", "updated_date",  // 更新时间
        "createdBy", "created_by",      // 创建人
        "updatedBy", "updated_by",      // 更新人
        "deleted", "isDeleted",         // 删除标志
        "deletedDate", "deleted_date",  // 删除时间
        "deletedBy", "deleted_by"       // 删除人
    );
    
    /**
     * 判断是否为审计字段（需要忽略的字段）
     * 
     * @param fieldName Java字段名（驼峰格式）
     * @return true表示是审计字段，应在请求参数中过滤
     */
    private boolean isAuditField(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return false;
        }
        
        // 直接匹配（大小写敏感）
        if (ignoreFields.contains(fieldName)) {
            return true;
        }
        
        // 前缀匹配（大小写不敏感）
        String lowerFieldName = fieldName.toLowerCase();
        return lowerFieldName.equals("id") ||
               lowerFieldName.startsWith("created") || 
               lowerFieldName.startsWith("updated") || 
               lowerFieldName.startsWith("deleted")  ||
               lowerFieldName.startsWith("isdeleted");
    }

    public ApiDocument generateDocument(TableInfo tableInfo) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());
        document.setEndpoints(generateEndpoints(tableInfo));
        document.setTableInfo(tableInfo);
        return document;
    }

    private Map<String, ApiEndpoint> generateEndpoints(TableInfo tableInfo) {
        Map<String, ApiEndpoint> endpoints = new LinkedHashMap<>();
        var requestPath = tableNameToApiName(tableInfo.getTableName());
        String baseApiPath = "/api/v1/";
        
        // 基础路径（用于分页查询和创建）
        String basePath = baseApiPath + requestPath;
        
        // 带主键的路径（用于根据主键查询、更新、删除）
        StringBuilder fullApiPath = new StringBuilder(basePath);
        for (var primaryKey : tableInfo.getPrimaryKeys()) {
            // 使用Java驼峰命名作为路径参数
            String javaFieldName = getJavaFieldNameForPrimaryKey(tableInfo, primaryKey);
            fullApiPath.append("/{").append(javaFieldName).append("}");
        }

        // 生成CRUD接口
        // 分页查询和创建使用基础路径
        endpoints.put("分页查询", createEndpoint(tableInfo, "GET", basePath, "获取%s列表",
                this::getQueryParams, null, this::createPaginationResponse));
        endpoints.put("创建", createEndpoint(tableInfo, "POST", basePath, "创建%s记录",
                this::getCreatedParameter,  this::getRequestExample, null));
        
        // 根据主键查询、更新、删除使用带主键的路径
        endpoints.put("根据主键查询", createEndpoint(tableInfo, "GET", fullApiPath.toString(), "根据主键获取%s详情",
                this::getPathParameters, null, this::createDetailResponse));
        endpoints.put("更新", createEndpoint(tableInfo, "PUT", fullApiPath.toString(), "更新%s记录",
                this::getUpdatedParameter, this::getRequestExample,  null));
        endpoints.put("删除", createEndpoint(tableInfo, "DELETE", fullApiPath.toString(), "删除%s记录",
                this::getPathParameters, null, null));

        return endpoints;
    }
    
    /**
     * 根据主键的数据库字段名获取对应的Java字段名
     */
    private String getJavaFieldNameForPrimaryKey(TableInfo tableInfo, String primaryKey) {
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (primaryKey.equals(column.getOriginalName())) {
                // 如果有Java字段名，使用Java字段名；否则使用驼峰转换
                if (column.getFieldName() != null && !column.getFieldName().isEmpty()) {
                    return column.getFieldName();
                }
                return NameConverterUtils.toCamelCase(primaryKey, false);
            }
        }
        // 如果找不到，使用驼峰转换
        return NameConverterUtils.toCamelCase(primaryKey, false);
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
        // 设置请求体参数（排除审计字段和主键）
        List<Parameter> parameters = new ArrayList<>();
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            String fieldName = columnInfo.getFieldName();
            // 排除审计字段和主键
            if (!isAuditField(fieldName) && !Boolean.TRUE.equals(columnInfo.getPrimaryKey())) {
                var columnExampleValue = getExampleValue(columnInfo, tableInfo.getTableName());
                var parameterType = getParameterType(columnInfo);
                var requestBody = createParameter(fieldName, parameterType, columnInfo.getRequired(), columnInfo.getComment(), columnExampleValue);
                parameters.add(requestBody);
            }
        });
        return parameters;
    }

    private List<Parameter> getUpdatedParameter(TableInfo tableInfo) {
        // 设置参数（排除审计字段和主键）
        List<Parameter> parameters = new ArrayList<>();

        // 请求体参数
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            String fieldName = columnInfo.getFieldName();
            // 排除审计字段和主键
            if (!isAuditField(fieldName) && !Boolean.TRUE.equals(columnInfo.getPrimaryKey())) {
                var columnExampleValue = getExampleValue(columnInfo, tableInfo.getTableName());
                String parameterType = getParameterType(columnInfo);

                var requestBody = createParameter(fieldName, parameterType,
                        columnInfo.getRequired(), columnInfo.getComment(), columnExampleValue);
                parameters.add(requestBody);
            }
        });

        return parameters;
    }


    /**
     * 获取查询接口的参数列表
     * 包含分页参数、排序参数和基于索引的过滤参数
     * 
     * @param tableInfo 表信息
     * @return 查询参数列表
     */
    private List<Parameter> getQueryParams(TableInfo tableInfo) {
        List<Parameter> parameters = new ArrayList<>();
        
        // 分页参数（可选，有默认值）
        parameters.add(createParameter("pageNo", "number", false, "页码（从1开始，默认1）", 1));
        parameters.add(createParameter("pageSize", "number", false, "每页大小（默认20）", 20));
        
        // 排序参数（可选，支持多字段，-表示降序）
        parameters.add(createParameter("sort", "string", false, "排序字段（多个用逗号分隔，-表示降序）", "-createdDate,id"));

        var stringCollectionMap = CollectionUtils.transformToMap(tableInfo.getColumnInfos(),
                ColumnInfo::getOriginalName, Function.identity());

        Set<String> fields = new HashSet<>();
        // 为可查询字段添加过滤参数
        for (IndexInfo indexInfo : tableInfo.getIndexes()) {
            for (var columnName : indexInfo.getColumnNames()) {
                var columnInfo = stringCollectionMap.get(columnName);
                if (columnInfo == null || "PRIMARY".equals(indexInfo.getIndexName())) {
                    continue;
                }

                if (!fields.contains(columnInfo.getFieldName())) {
                    fields.add(columnInfo.getFieldName());
                    
                    String fieldType = columnInfo.getType().toLowerCase();
                    String baseFieldName = removeIsPrefix(columnInfo.getFieldName());
                    String comment = columnInfo.getComment() != null ? columnInfo.getComment() : "";
                    
                    // 如果是 datetime/timestamp 类型字段，生成起止时间参数（范围查询，UTC时间）
                    if (isDateTimeType(fieldType)) {
                        // 开始时间参数
                        String startFieldName = baseFieldName + "Start";
                        Object startExample = generateStartDateTimeExample();
                        parameters.add(createParameter(startFieldName, "string", false, 
                                comment + "开始时间（范围查询，UTC时间）", startExample));
                        
                        // 结束时间参数
                        String endFieldName = baseFieldName + "End";
                        Object endExample = generateEndDateTimeExample(columnInfo);
                        parameters.add(createParameter(endFieldName, "string", false, 
                                comment + "结束时间（范围查询，UTC时间）", endExample));
                    } else {
                        // date、time 或其他类型字段，正常生成单个参数
                        var exampleValue = getExampleValue(columnInfo, tableInfo.getTableName());
                        parameters.add(createParameter(columnInfo.getFieldName(), getParameterType(columnInfo),
                                false, "根据" + comment + "过滤", exampleValue));
                    }
                }
            }
        }

        return parameters;
    }
    
    /**
     * 判断是否为需要范围查询的时间类型（datetime/timestamp）
     * date 和 time 类型不需要范围查询，因为已经确定了具体的一天或小时
     */
    private boolean isDateTimeType(String fieldType) {
        if (fieldType == null) {
            return false;
        }
        String lowerType = fieldType.toLowerCase();
        // 只有 datetime 和 timestamp 需要范围查询（对应 Instant 类型，UTC时间）
        // date 对应 LocalDate，time 对应 LocalTime，不需要范围查询
        return lowerType.contains("timestamp") || lowerType.contains("datetime");
    }
    
    /**
     * 生成开始时间示例值（UTC时间格式，用于 datetime/timestamp）
     */
    private Object generateStartDateTimeExample() {
        // UTC时间格式：2024-01-15T00:00:00Z
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00Z";
    }
    
    /**
     * 生成结束时间示例值（UTC时间格式，用于 datetime/timestamp）
     */
    private Object generateEndDateTimeExample(ColumnInfo column) {
        // UTC时间格式：2024-01-15T23:59:59Z
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T23:59:59Z";
    }

    private List<Parameter> getPathParameters(TableInfo tableInfo) {
        // 设置路径参数（使用Java字段名）
        List<Parameter> parameters = new ArrayList<>();
        for (String primaryKey : tableInfo.getPrimaryKeys()) {
            String javaFieldName = getJavaFieldNameForPrimaryKey(tableInfo, primaryKey);
            String primaryKeyType = getPrimaryKeyType(tableInfo, primaryKey);
            // 查找对应的ColumnInfo来生成示例值
            ColumnInfo pkColumn = tableInfo.getColumnInfos().stream()
                .filter(col -> primaryKey.equals(col.getOriginalName()))
                .findFirst()
                .orElse(null);
            Object exampleValue = pkColumn != null 
                ? getExampleValue(pkColumn, tableInfo.getTableName())
                : 1L;
            String description = getPrimaryKeyDescription(tableInfo, primaryKey);
            parameters.add(createParameter(javaFieldName, primaryKeyType, true, description, exampleValue));
        }
        return parameters;
    }
    
    /**
     * 获取主键的描述信息
     */
    private String getPrimaryKeyDescription(TableInfo tableInfo, String primaryKey) {
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (primaryKey.equals(column.getOriginalName())) {
                if (column.getComment() != null && !column.getComment().isEmpty()) {
                    return column.getComment();
                }
            }
        }
        return "主键" + primaryKey;
    }
    
    /**
     * 获取指定主键的类型
     */
    private String getPrimaryKeyType(TableInfo tableInfo, String primaryKey) {
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (primaryKey.equals(column.getOriginalName())) {
                return getParameterType(column);
            }
        }
        return "number";
    }

    private Parameter createParameter(String name, String type, boolean required, String description, Object example) {
        Parameter param = new Parameter();
        // 如果是is开头的字段，去掉is前缀（用于API文档显示）
        String displayName = removeIsPrefix(name);
        param.setName(displayName);
        param.setType(type);
        param.setRequired(required);
        param.setDescription(description);
        param.setExample(example);
        return param;
    }
    
    /**
     * 去掉is前缀（用于API文档显示）
     * 例如：isPublished -> published
     */
    private String removeIsPrefix(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return fieldName;
        }
        // 如果字段名以is开头，且第二个字符是大写，则去掉is前缀
        if (fieldName.length() > 2 && fieldName.startsWith("is") && Character.isUpperCase(fieldName.charAt(2))) {
            // 将第三个字符转为小写
            return Character.toLowerCase(fieldName.charAt(2)) + fieldName.substring(3);
        }
        return fieldName;
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


    private Map<String, Object> createPaginationResponse(TableInfo tableInfo) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("total", 100);
        response.put("data", List.of(createDetailResponse(tableInfo)));
        return response;
    }

    private Map<String, Object> createDetailResponse(TableInfo tableInfo) {
        Map<String, Object> response = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            // 响应数据中，is开头的字段也去掉is前缀
            String displayFieldName = removeIsPrefix(column.getFieldName());
            response.put(displayFieldName, getExampleValue(column, tableInfo.getTableName()));
        }
        return response;
    }

    private String getRequestExample(TableInfo tableInfo) {
        Map<String, Object> example = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            String fieldName = column.getFieldName();
            // 排除审计字段和主键
            if (!isAuditField(fieldName) && !Boolean.TRUE.equals(column.getPrimaryKey())) {
                // 请求示例中，is开头的字段也去掉is前缀
                String displayFieldName = removeIsPrefix(fieldName);
                example.put(displayFieldName, getExampleValue(column, tableInfo.getTableName()));
            }
        }
        if (CollectionUtils.isEmpty(example)) {
            return "";
        }
        System.err.println(example);
        return mapToJsonString(example);
    }

    /**
     * 获取示例值（使用智能生成器）
     * 
     * @param column 字段信息
     * @param tableName 表名
     * @return 示例值
     */
    private Object getExampleValue(ColumnInfo column, String tableName) {
        return ExampleValueGenerator.generateExampleValue(column, tableName);
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
        String tableName = apiDocument.getTableName();
        if (tableName == null || tableName.trim().isEmpty()) {
            tableName = "未知表";
        }
        markdown.append("**数据表：** ").append(tableName).append("\n\n");

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

        String filename = tableName + ".md";
        saveDocumentToFile(markdown.toString(), apiDocOutputDir, filename);
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
     * 
     * @param response 原始响应对象
     * @return JSON格式的分页响应示例
     */
    private String generatePaginationResponseExample(ResponseInfo<?> response) {
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
