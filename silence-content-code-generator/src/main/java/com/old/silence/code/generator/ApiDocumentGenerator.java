package com.old.silence.code.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
public class ApiDocumentGenerator {

    private final String baseApiPath = "/api/v1/";

    public ApiDocument generateDocument(TableInfo tableInfo) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());
        document.setEndpoints(generateEndpoints(tableInfo));

        return document;
    }

    private Map<String, ApiEndpoint> generateEndpoints(TableInfo tableInfo) {
        Map<String, ApiEndpoint> endpoints = new LinkedHashMap<>();

        // 生成CRUD接口
        endpoints.put("page", createListEndpoint(tableInfo));
        endpoints.put("get", createGetEndpoint(tableInfo));
        endpoints.put("create", createCreateEndpoint(tableInfo));
        endpoints.put("update", createUpdateEndpoint(tableInfo));
        endpoints.put("delete", createDeleteEndpoint(tableInfo));

        return endpoints;
    }

    private ApiEndpoint createListEndpoint(TableInfo tableInfo) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod("GET");
        endpoint.setPath(baseApiPath + tableNameToApiName(tableInfo.getTableName()));
        endpoint.setDescription("获取" + tableInfo.getTableName() + "列表");

        // 设置查询参数
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(createParameter("pageNo", "integer", true, "页码", "1"));
        parameters.add(createParameter("pageSize", "integer", true, "每页大小", "20"));
        parameters.add(createParameter("sort", "string", true, "排序字段", "-createdDate"));

        var stringCollectionMap = CollectionUtils.transformToMap(tableInfo.getColumns(), ColumnInfo::getName, Function.identity());

        // 为可查询字段添加过滤参数
        for (IndexInfo indexInfo : tableInfo.getIndexes()) {
            for (var columnName : indexInfo.getColumnNames()) {
                var columnInfo = stringCollectionMap.get(columnName);
                parameters.add(createParameter(columnInfo.getName(), getParameterType(columnInfo),
                        false, "根据" + columnInfo.getComment() + "过滤", null));
            }

        }

        endpoint.setParameters(parameters);

        // 设置响应信息
        ResponseInfo<Object> response = ResponseInfo.success(
                "获取列表成功",
                createPaginationResponse(tableInfo)
        );
        endpoint.setResponse(response);

        return endpoint;
    }

    private ApiEndpoint createGetEndpoint(TableInfo tableInfo) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod("GET");
        endpoint.setPath(baseApiPath + tableNameToApiName(tableInfo.getTableName()) + "/{id}");
        endpoint.setDescription("根据ID获取" + tableInfo.getTableName() + "详情");

        // 设置路径参数
        List<Parameter> parameters = new ArrayList<>();
        String primaryKeyType = getPrimaryKeyType(tableInfo);
        parameters.add(createParameter("id", primaryKeyType, true, "记录ID", null));
        endpoint.setParameters(parameters);

        // 设置响应信息
        ResponseInfo<Object> response = ResponseInfo.success(
                "获取详情成功",
                createDetailResponse(tableInfo)
        );
        endpoint.setResponse(response);

        return endpoint;
    }

    private ApiEndpoint createCreateEndpoint(TableInfo tableInfo) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod("POST");
        endpoint.setPath("/api/" + tableNameToApiName(tableInfo.getTableName()));
        endpoint.setDescription("创建" + tableInfo.getTableName() + "记录");

        // 设置请求体参数
        List<Parameter> parameters = new ArrayList<>();
        Parameter requestBody = new Parameter();
        requestBody.setName("body");
        requestBody.setType("object");
        requestBody.setRequired(true);
        requestBody.setDescription("创建请求体");
        requestBody.setExample(createRequestExample(tableInfo, "create"));
        parameters.add(requestBody);
        endpoint.setParameters(parameters);

        // 设置响应信息
        ResponseInfo<Object> response = ResponseInfo.success(
                "创建成功",
                createDetailResponse(tableInfo)
        );
        endpoint.setResponse(response);

        return endpoint;
    }

    private ApiEndpoint createUpdateEndpoint(TableInfo tableInfo) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod("PUT");
        endpoint.setPath(baseApiPath+ tableNameToApiName(tableInfo.getTableName()) + "/{id}");
        endpoint.setDescription("更新" + tableInfo.getTableName() + "记录");

        // 设置参数
        List<Parameter> parameters = new ArrayList<>();

        // 路径参数
        String primaryKeyType = getPrimaryKeyType(tableInfo);
        parameters.add(createParameter("id", primaryKeyType, true, "记录ID", null));

        // 请求体参数
        Parameter requestBody = new Parameter();
        requestBody.setName("body");
        requestBody.setType("object");
        requestBody.setRequired(true);
        requestBody.setDescription("更新请求体");
        requestBody.setExample(createRequestExample(tableInfo, "update"));
        parameters.add(requestBody);

        endpoint.setParameters(parameters);

        // 设置响应信息
        ResponseInfo<Object> response = ResponseInfo.success(
                "更新成功",
                createDetailResponse(tableInfo)
        );
        endpoint.setResponse(response);

        return endpoint;
    }

    private ApiEndpoint createDeleteEndpoint(TableInfo tableInfo) {
        ApiEndpoint endpoint = new ApiEndpoint();
        endpoint.setMethod("DELETE");
        endpoint.setPath(baseApiPath + tableNameToApiName(tableInfo.getTableName()) + "/{id}");
        endpoint.setDescription("删除" + tableInfo.getTableName() + "记录");

        // 设置路径参数
        List<Parameter> parameters = new ArrayList<>();
        String primaryKeyType = getPrimaryKeyType(tableInfo);
        parameters.add(createParameter("id", primaryKeyType, true, "记录ID", null));
        endpoint.setParameters(parameters);

        // 设置响应信息
        ResponseInfo<Object> response = ResponseInfo.success("删除成功", null);
        endpoint.setResponse(response);

        return endpoint;
    }

    // ========== 辅助方法 ==========

    private Parameter createParameter(String name, String type, boolean required, String description, String example) {
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
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (column.getName().equals(primaryKeyName)) {
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
        for (ColumnInfo column : tableInfo.getColumns()) {
            response.put(column.getName(), getExampleValue(column));
        }
        return response;
    }

    private String createRequestExample(TableInfo tableInfo, String operation) {
        Map<String, Object> example = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumns()) {
            // 创建操作包含所有非自增字段
            if ("create".equals(operation)) {
                if (!Boolean.TRUE.equals(column.getAutoIncrement())) {
                    example.put(column.getName(), getExampleValue(column));
                }
            }
            // 更新操作包含所有字段（除了主键）
            else if ("update".equals(operation)) {
                if (!tableInfo.getPrimaryKeys().contains(column.getName())) {
                    example.put(column.getName(), getExampleValue(column));
                }
            }
        }
        return mapToJsonString(example);
    }

    private Object getExampleValue(ColumnInfo column) {
        String type = column.getType().toLowerCase();
        if (type.contains("int")) {
            return 1;
        } else if (type.contains("varchar") || type.contains("text") || type.contains("char")) {
            return column.getName() + "示例值";
        } else if (type.contains("date") || type.contains("time")) {
            return "2024-01-01";
        } else if (type.contains("decimal") || type.contains("float") || type.contains("double")) {
            return 99.99;
        } else if (type.contains("boolean") || type.contains("tinyint(1)")) {
            return true;
        } else {
            return "示例值";
        }
    }

    private String mapToJsonString(Map<String, Object> map) {
        return JacksonMapper.getSharedInstance().toJson(map);
    }

    private String tableNameToApiName(String tableName) {
        return NameConverterUtils.toPluralVariableName(tableName);
    }
}
