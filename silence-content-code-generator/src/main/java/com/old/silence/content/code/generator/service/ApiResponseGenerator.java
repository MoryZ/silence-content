package com.old.silence.content.code.generator.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.ExampleValueGenerator;
import com.old.silence.json.JacksonMapper;

/**
 * API 响应生成器
 * 负责根据表结构生成 API 接口的响应示例数据
 * 
 * @author moryzang
 */
@Component
public class ApiResponseGenerator {

    /**
     * 生成详情响应数据
     */
    public Map<String, Object> generateDetailResponse(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw new IllegalArgumentException("TableInfo 不能为 null");
        }
        if (tableInfo.getColumnInfos() == null || tableInfo.getColumnInfos().isEmpty()) {
            return new LinkedHashMap<>();
        }
        
        Map<String, Object> response = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            String displayFieldName = removeIsPrefix(column.getFieldName());
            response.put(displayFieldName, getExampleValue(column, tableInfo.getTableName()));
        }
        return response;
    }

    /**
     * 生成分页响应数据
     */
    public Map<String, Object> generatePaginationResponse(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw new IllegalArgumentException("TableInfo 不能为 null");
        }
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("total", 100);
        response.put("data", List.of(generateDetailResponse(tableInfo)));
        return response;
    }

    /**
     * 生成请求示例 JSON 字符串
     */
    public String generateRequestExample(TableInfo tableInfo) {
        Map<String, Object> example = new LinkedHashMap<>();
        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            String fieldName = column.getFieldName();
            // 排除审计字段和主键
            if (isNotAuditField(fieldName) && !Boolean.TRUE.equals(column.getPrimaryKey())) {
                String displayFieldName = removeIsPrefix(fieldName);
                example.put(displayFieldName, getExampleValue(column, tableInfo.getTableName()));
            }
        }
        if (example.isEmpty()) {
            return "";
        }
        return mapToJsonString(example);
    }

    /**
     * 去掉is前缀（用于API文档显示）
     */
    private String removeIsPrefix(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return fieldName;
        }
        if (fieldName.length() > 2 && fieldName.startsWith("is") && Character.isUpperCase(fieldName.charAt(2))) {
            return Character.toLowerCase(fieldName.charAt(2)) + fieldName.substring(3);
        }
        return fieldName;
    }

    /**
     * 判断是否为审计字段
     */
    private boolean isNotAuditField(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return true;
        }
        String lowerFieldName = fieldName.toLowerCase();
        return !lowerFieldName.equals("id") &&
                !lowerFieldName.startsWith("created") &&
                !lowerFieldName.startsWith("updated") &&
                !lowerFieldName.startsWith("deleted");
    }

    /**
     * 获取示例值
     */
    private Object getExampleValue(ColumnInfo column, String tableName) {
        return ExampleValueGenerator.generateExampleValue(column, tableName);
    }

    /**
     * Map 转 JSON 字符串
     */
    private String mapToJsonString(Map<String, Object> map) {
        return JacksonMapper.getSharedInstance().toJson(map);
    }
}
