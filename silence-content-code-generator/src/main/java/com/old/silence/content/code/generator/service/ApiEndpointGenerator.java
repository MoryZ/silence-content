package com.old.silence.content.code.generator.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import com.old.silence.content.code.generator.constants.ApiEndpointNames;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.Parameter;
import com.old.silence.content.code.generator.model.ResponseInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.NameConverterUtils;

/**
 * API 端点生成器
 * 负责生成 RESTful API 的端点定义（CRUD 接口）
 * 
 * @author moryzang
 */
@Component
public class ApiEndpointGenerator {

    private final ApiParameterGenerator parameterGenerator;
    private final ApiResponseGenerator responseGenerator;

    public ApiEndpointGenerator(ApiParameterGenerator parameterGenerator, 
                               ApiResponseGenerator responseGenerator) {
        this.parameterGenerator = parameterGenerator;
        this.responseGenerator = responseGenerator;
    }

    /**
     * 生成 CRUD 接口端点定义
     */
    public Map<String, ApiEndpoint> generateEndpoints(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw new IllegalArgumentException("TableInfo 不能为 null");
        }
        if (tableInfo.getTableName() == null || tableInfo.getTableName().isEmpty()) {
            throw new IllegalArgumentException("TableInfo 的表名不能为空");
        }
        
        Map<String, ApiEndpoint> endpoints = new LinkedHashMap<>();
        
        var requestPath = tableNameToApiName(tableInfo.getTableName());
        String baseApiPath = "/api/v1/";
        
        // 基础路径（用于分页查询和创建）
        String basePath = baseApiPath + requestPath;
        
        // 带主键的路径（用于根据主键查询、更新、删除）
        StringBuilder fullApiPath = new StringBuilder(basePath);
        for (var primaryKey : tableInfo.getPrimaryKeys()) {
            String javaFieldName = getJavaFieldNameForPrimaryKey(tableInfo, primaryKey);
            fullApiPath.append("/{").append(javaFieldName).append("}");
        }

        // 生成 CRUD 接口
        endpoints.put(ApiEndpointNames.PAGINATED_QUERY, createEndpoint(tableInfo, "GET", basePath, "获取%s列表",
                parameterGenerator::generateQueryParameters, null, responseGenerator::generatePaginationResponse));
        endpoints.put(ApiEndpointNames.CREATE, createEndpoint(tableInfo, "POST", basePath, "创建%s记录",
                parameterGenerator::generateCreatedParameters, responseGenerator::generateRequestExample, null));
        
        endpoints.put(ApiEndpointNames.QUERY_BY_KEY, createEndpoint(tableInfo, "GET", fullApiPath.toString(), "根据主键获取%s详情",
                parameterGenerator::generatePathParameters, null, responseGenerator::generateDetailResponse));
        endpoints.put(ApiEndpointNames.UPDATE, createEndpoint(tableInfo, "PUT", fullApiPath.toString(), "更新%s记录",
                parameterGenerator::generateUpdatedParameters, responseGenerator::generateRequestExample, null));
        endpoints.put(ApiEndpointNames.DELETE, createEndpoint(tableInfo, "DELETE", fullApiPath.toString(), "删除%s记录",
                parameterGenerator::generatePathParameters, null, null));

        return endpoints;
    }

    /**
     * 创建单个端点对象
     */
    private ApiEndpoint createEndpoint(TableInfo tableInfo, String method, String fullApiPath, String description,
                                       Function<TableInfo, List<Parameter>> paramFunction,
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

    /**
     * 根据主键的数据库字段名获取对应的 Java 字段名
     */
    private String getJavaFieldNameForPrimaryKey(TableInfo tableInfo, String primaryKey) {
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            if (primaryKey.equals(column.getOriginalName())) {
                if (column.getFieldName() != null && !column.getFieldName().isEmpty()) {
                    return column.getFieldName();
                }
                return NameConverterUtils.toCamelCase(primaryKey, false);
            }
        }
        return NameConverterUtils.toCamelCase(primaryKey, false);
    }

    /**
     * 表名转 API 路径名
     */
    private String tableNameToApiName(String tableName) {
        return NameConverterUtils.toPluralVariableName(tableName);
    }
}
