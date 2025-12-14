package com.old.silence.content.code.generator.support;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.NameConverterUtils;
import com.old.silence.core.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataModelBuilder {
    private final Set<String> auditFields = Set.of("id", "created_date", "created_by", "updated_date", "updated_by");

    /**
     * 需要@NotNull注解的Java类型集合
     */
    private final Set<String> notNullFields = Set.of("BigInteger", "Long", "Integer", "BigDecimal", "Instant", "Boolean");

    public Map<String, Object> build(TableInfo tableInfo, String basePackageName, String packageName,
                                     Map<String, Object> extras) {
        Map<String, Object> dataModel = new HashMap<>();

        tableInfo.setColumnInfos(tableInfo.getColumnInfos().stream()
                .filter(columnInfo -> !auditFields.contains(columnInfo.getOriginalName()))
                .collect(Collectors.toList()));

        dataModel.put("tableInfo", tableInfo);
        dataModel.put("columnInfos", tableInfo.getColumnInfos());

        dataModel.put("className", NameConverterUtils.toCamelCase(tableInfo.getTableName(), true));
        dataModel.put("variableName", NameConverterUtils.toCamelCase(tableInfo.getTableName(), false));

        dataModel.put("apiName", NameConverterUtils.toPluralVariableName(tableInfo.getTableName()));
        dataModel.put("apiPath", "/" + NameConverterUtils.toPluralVariableName(tableInfo.getTableName()));

        dataModel.put("persistencePackage", "");
        dataModel.put("contextId", tableInfo.getTableName().replace("_", "-"));

        dataModel.put("authorName", "moryzang");
        dataModel.put("basePackage", basePackageName);
        dataModel.put("packageName", basePackageName + packageName);
        dataModel.put("applicationName", "silence-content-service");

        ColumnInfo primaryKey = getPrimaryKeyColumn(tableInfo);
        if (primaryKey != null) {
            dataModel.put("primaryKeyType", FreemarkerHelpers.getJavaType(primaryKey));
            dataModel.put("primaryKeyField", primaryKey.getFieldName() != null ?
                    primaryKey.getFieldName() : NameConverterUtils.toCamelCase(primaryKey.getOriginalName(), false));
            dataModel.put("primaryKeyColumn", primaryKey.getOriginalName());
        } else {
            dataModel.put("primaryKeyType", "BigInteger");
            dataModel.put("primaryKeyField", "id");
            dataModel.put("primaryKeyColumn", "id");
        }

        // 复用库内的 FreeMarker Helper
        dataModel.put("isQueryableField", new FreemarkerHelpers.QueryableFieldMethod());
        dataModel.put("isEnumField", new FreemarkerHelpers.EnumFieldMethod());
        dataModel.put("getJavaType", new FreemarkerHelpers.TypeConverterMethod());
        dataModel.put("toCamelCase", new FreemarkerHelpers.NameConverterMethod());
        dataModel.put("isNumericType", new FreemarkerHelpers.NumericTypeMethod());
        dataModel.put("isBooleanType", new FreemarkerHelpers.BooleanTypeMethod());
        dataModel.put("isInstantType", new FreemarkerHelpers.InstantTypeMethod());
        dataModel.put("isCollectionType", new FreemarkerHelpers.CollectionTypeMethod());
        dataModel.put("isStringType", new FreemarkerHelpers.StringTypeMethod());
        dataModel.put("needsColumnAnnotation", new FreemarkerHelpers.NeedsColumnAnnotationMethod());
        dataModel.put("isMapField", new FreemarkerHelpers.IsMapFieldMethod());


        dataModel.put("hasNotBlank", hasNotBlankAnnotation(tableInfo));
        dataModel.put("hasSize", hasSizeAnnotation(tableInfo));
        dataModel.put("hasNotNull", hasNotNull(tableInfo));


        dataModel.put("hasInstantType", hasColumnType(tableInfo, "Instant"));
        dataModel.put("hasBigDecimalType", hasColumnType(tableInfo, "BigDecimal"));
        dataModel.put("hasBigIntegerType", hasColumnType(tableInfo, "BigInteger"));
        dataModel.put("hasLocalDateType", hasColumnType(tableInfo, "LocalDate"));
        dataModel.put("hasLocalTimeType", hasColumnType(tableInfo, "LocalTime"));
        dataModel.put("hasCollectionType", hasCollectionType(tableInfo));

        // ========== 16. Map类型判断（用于attributes扩展属性） ==========
        dataModel.put("hasMapType", hasMapType(tableInfo));


        if (CollectionUtils.isNotEmpty(extras)) {
            dataModel.putAll(extras);
        }
        return dataModel;
    }

    private boolean hasColumnType(TableInfo tableInfo, String columnType) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> column.getFieldType().equals(columnType));
    }

    /**
     * 检查是否有集合类型字段
     */
    private boolean hasCollectionType(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> {
                    String type = column.getFieldType();
                    return type.startsWith("List<") || type.startsWith("Set<") ||
                            type.startsWith("Map<") || type.endsWith("[]");
                });
    }

    /**
     * 判断表中是否包含Map类型字段（如attributes扩展属性）
     *
     * <p>用于判断是否需要导入java.util.Map和MapAttributeConverter
     *
     * @param tableInfo 表信息
     * @return 是否包含Map类型
     */
    private boolean hasMapType(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> {
                    String type = column.getFieldType();
                    return type.startsWith("Map<");
                });
    }

    /**
     * 检查是否需要@NotBlank注解（用于非空字符串）
     */
    private boolean hasNotBlankAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        column.getFieldType().equals("String"));
    }

    /**
     * 检查是否需要@Size注解（用于带长度限制的字符串）
     */
    private boolean hasSizeAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        column.getFieldType().equals("String") &&
                        column.getLength() != null && column.getLength() > 0);
    }

    /**
     * 检查是否需要@NotNull注解（用于非空的数字、枚举、布尔等）
     */
    private boolean hasNotNull(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        notNullFields.contains(column.getFieldType()));
    }


    private ColumnInfo getPrimaryKeyColumn(TableInfo tableInfo) {
        if (tableInfo.getPrimaryKeys().isEmpty()) {
            return null;
        }
        String primaryKeyName = tableInfo.getPrimaryKeys().getFirst();
        return tableInfo.getColumnInfos().stream()
                .filter(col -> col.getOriginalName().equals(primaryKeyName))
                .findFirst()
                .orElse(null);
    }
}
