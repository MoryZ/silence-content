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

        if (CollectionUtils.isNotEmpty(extras)) {
            dataModel.putAll(extras);
        }
        return dataModel;
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
