package com.old.silence.content.code.generator.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.IndexInfo;
import com.old.silence.content.code.generator.model.Parameter;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.ExampleValueGenerator;
import com.old.silence.content.code.generator.util.NameConverterUtils;
import com.old.silence.core.util.CollectionUtils;

/**
 * API 参数生成器
 * 负责根据表结构生成 API 接口所需的各种参数
 * 
 * @author moryzang
 */
@Component
public class ApiParameterGenerator {

    /**
     * 需要忽略的字段（审计字段和主键）
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
     * 生成创建接口的参数
     */
    public List<Parameter> generateCreatedParameters(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw new IllegalArgumentException("TableInfo 不能为 null");
        }
        if (tableInfo.getColumnInfos() == null || tableInfo.getColumnInfos().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Parameter> parameters = new ArrayList<>();
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            String fieldName = columnInfo.getFieldName();
            // 排除审计字段和主键
            if (isNotAuditField(fieldName) && !Boolean.TRUE.equals(columnInfo.getPrimaryKey())) {
                var columnExampleValue = getExampleValue(columnInfo, tableInfo.getTableName());
                var parameterType = getParameterType(columnInfo);
                var requestBody = createParameter(fieldName, parameterType, columnInfo.getRequired(), 
                        columnInfo.getComment(), columnExampleValue);
                parameters.add(requestBody);
            }
        });
        return parameters;
    }

    /**
     * 生成更新接口的参数
     */
    public List<Parameter> generateUpdatedParameters(TableInfo tableInfo) {
        List<Parameter> parameters = new ArrayList<>();
        tableInfo.getColumnInfos().forEach(columnInfo -> {
            String fieldName = columnInfo.getFieldName();
            // 排除审计字段和主键
            if (isNotAuditField(fieldName) && !Boolean.TRUE.equals(columnInfo.getPrimaryKey())) {
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
     * 生成查询接口的参数列表
     * 包含分页参数、排序参数和基于索引的过滤参数
     */
    public List<Parameter> generateQueryParameters(TableInfo tableInfo) {
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
                        Object endExample = generateEndDateTimeExample();
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
     * 生成路径参数（用于根据主键查询、更新、删除）
     */
    public List<Parameter> generatePathParameters(TableInfo tableInfo) {
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
     * 根据主键的数据库字段名获取对应的Java字段名
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

    /**
     * 创建参数对象
     */
    private Parameter createParameter(String name, String type, boolean required, String description, Object example) {
        Parameter param = new Parameter();
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
     */
    public String removeIsPrefix(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return fieldName;
        }
        if (fieldName.length() > 2 && fieldName.startsWith("is") && Character.isUpperCase(fieldName.charAt(2))) {
            return Character.toLowerCase(fieldName.charAt(2)) + fieldName.substring(3);
        }
        return fieldName;
    }

    /**
     * 获取参数类型
     */
    private String getParameterType(ColumnInfo column) {
        String type = column.getType().toLowerCase();
        if (type.contains("int") || type.contains("decimal") || type.contains("float") || type.contains("double")) {
            return "number";
        } else if (type.contains("date") || type.contains("time")) {
            return "string";
        } else if (type.contains("timestamp") || type.contains("datetime")) {
            return "string";
        } else if (type.contains("bit")) {
            return "boolean";
        } else {
            return "string";
        }
    }

    /**
     * 判断是否为审计字段
     */
    private boolean isNotAuditField(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return true;
        }
        if (ignoreFields.contains(fieldName)) {
            return false;
        }
        String lowerFieldName = fieldName.toLowerCase();
        return !lowerFieldName.equals("id") &&
                !lowerFieldName.startsWith("created") &&
                !lowerFieldName.startsWith("updated") &&
                !lowerFieldName.startsWith("deleted");
    }

    /**
     * 判断是否为需要范围查询的时间类型
     */
    private boolean isDateTimeType(String fieldType) {
        if (fieldType == null) {
            return false;
        }
        String lowerType = fieldType.toLowerCase();
        return lowerType.contains("timestamp") || lowerType.contains("datetime");
    }

    /**
     * 生成开始时间示例值
     */
    private Object generateStartDateTimeExample() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00Z";
    }

    /**
     * 生成结束时间示例值
     */
    private Object generateEndDateTimeExample() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T23:59:59Z";
    }

    /**
     * 获取示例值
     */
    private Object getExampleValue(ColumnInfo column, String tableName) {
        return ExampleValueGenerator.generateExampleValue(column, tableName);
    }
}
