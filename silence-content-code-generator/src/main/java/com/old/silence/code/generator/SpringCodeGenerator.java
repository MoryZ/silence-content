package com.old.silence.code.generator;// SpringCodeGenerator.java


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModelEx;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.TableInfo;

public class SpringCodeGenerator {
    private final Configuration freemarkerConfig;
    private String persistencePackage = "javax";
    private boolean useLombok;

    public SpringCodeGenerator() throws Exception {
        this("javax", true);
    }

    public SpringCodeGenerator(String persistencePackage, boolean useLombok) throws Exception {
        this.persistencePackage = persistencePackage;
        this.useLombok = useLombok;
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    public void generateFile(TableInfo tableInfo, String outputDir, String templateName, String suffix) throws Exception {
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        dataModel.put("basePackage", "com.old.silence");
        dataModel.put("applicationName", "content-service");

        Template template = freemarkerConfig.getTemplate(templateName);
        String fileName = dataModel.get("className") + suffix + ".java";
        File outputFile = new File(outputDir, fileName);

        outputFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
        }
        System.out.println("生成 " + suffix + ": " + outputFile.getAbsolutePath());
    }

    private Map<String, Object> createBaseDataModel(TableInfo tableInfo) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("tableInfo", tableInfo);
        dataModel.put("className", toCamelCase(tableInfo.getTableName(), true));
        dataModel.put("persistencePackage", persistencePackage);

        // 添加工具方法
        dataModel.put("getJavaType", new TypeConverterMethod());
        dataModel.put("toCamelCase", new NameConverterMethod());
        dataModel.put("isQueryableField", new QueryableFieldMethod());
        dataModel.put("isEnumField", new EnumFieldMethod());

        return dataModel;
    }

    // 在 SpringCodeGenerator.java 中添加完整的工具方法实现
    public static class QueryableFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isQueryableField方法需要1个参数");
            }
            ColumnInfo column = (ColumnInfo) arguments.get(0);
            return isQueryableField(column);
        }
    }

    private static boolean isQueryableField(ColumnInfo column) {
        if (column == null) return false;

        String type = column.getType().toLowerCase();
        String name = column.getName().toLowerCase();

        // 排除主键、大字段、二进制字段和元数据字段
        boolean isExcludedType = type.contains("text") ||
                type.contains("blob") ||
                type.contains("binary") ||
                type.contains("json") ||
                type.contains("clob");

        boolean isExcludedName = name.contains("password") ||
                name.contains("secret") ||
                name.contains("token") ||
                name.contains("reference") || // 排除文件引用字段
                name.endsWith("_ref");

        return !isExcludedType && !isExcludedName;
    }

    // 枚举字段判断方法
    public static class EnumFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isEnumField方法需要1个参数");
            }
            ColumnInfo column = (ColumnInfo) arguments.get(0);
            return isEnumField(column);
        }
    }

    private static boolean isEnumField(ColumnInfo column) {
        if (column == null) return false;
        String name = column.getName().toLowerCase();
        return name.contains("status") || name.contains("type");
    }

    // ========== 类型检测方法实现 ==========

    private boolean hasDateType(TableInfo tableInfo) {
        return tableInfo.getColumns().stream()
                .anyMatch(column -> convertToJavaType(column).equals("Date"));
    }

    private boolean hasBigDecimalType(TableInfo tableInfo) {
        return tableInfo.getColumns().stream()
                .anyMatch(column -> convertToJavaType(column).equals("BigDecimal"));
    }

    private boolean hasLocalDateTimeType(TableInfo tableInfo) {
        return tableInfo.getColumns().stream()
                .anyMatch(column -> convertToJavaType(column).equals("LocalDateTime"));
    }

    private boolean hasLocalDateType(TableInfo tableInfo) {
        return tableInfo.getColumns().stream()
                .anyMatch(column -> convertToJavaType(column).equals("LocalDate"));
    }


    // ========== FreeMarker 工具方法实现 ==========

    /**
     * 类型转换方法 - 用于FreeMarker模板
     */
    public static class TypeConverterMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("getJavaType方法需要1个参数");
            }
            ColumnInfo column = (ColumnInfo) arguments.get(0);
            return convertToJavaType(column);
        }
    }

    /**
     * 名称转换方法 - 用于FreeMarker模板
     */
    public static class NameConverterMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 2) {
                throw new RuntimeException("toCamelCase方法需要2个参数");
            }
            String name = arguments.get(0).toString();
            Boolean capitalizeFirst = Boolean.parseBoolean(arguments.get(1).toString());
            return toCamelCase(name, capitalizeFirst);
        }
    }

    /**
     * 列查找方法 - 用于FreeMarker模板
     */
    public static class ColumnFinderMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 2) {
                throw new RuntimeException("getColumnByName方法需要2个参数");
            }
            TableInfo tableInfo = (TableInfo) arguments.get(0);
            String columnName = arguments.get(1).toString();

            return tableInfo.getColumns().stream()
                    .filter(col -> col.getName().equals(columnName))
                    .findFirst()
                    .orElse(null);
        }
    }

    // ========== 核心工具方法 ==========

    /**
     * 数据库类型到Java类型转换
     */
    private static String convertToJavaType(ColumnInfo column) {
        if (column == null || column.getType() == null) {
            return "Object";
        }

        String type = column.getType().toLowerCase();

        // 整数类型
        if (type.contains("bigint")) {
            return "Long";
        } else if (type.contains("int") || type.contains("integer")) {
            return "Integer";
        } else if (type.contains("smallint") || type.contains("tinyint")) {
            if (type.contains("(1)") || type.contains("boolean")) {
                return "Boolean";
            }
            return "Integer";
        }
        // 浮点类型
        else if (type.contains("decimal") || type.contains("numeric")) {
            return "BigDecimal";
        } else if (type.contains("float")) {
            return "Float";
        } else if (type.contains("double") || type.contains("real")) {
            return "Double";
        }
        // 字符串类型
        else if (type.contains("varchar") || type.contains("char") ||
                type.contains("text") || type.contains("enum") ||
                type.contains("set")) {
            return "String";
        }
        // 日期时间类型
        else if (type.contains("datetime") || type.contains("timestamp")) {
            return "LocalDateTime";
        } else if (type.contains("date")) {
            return "LocalDate";
        } else if (type.contains("time")) {
            return "LocalTime";
        }
        // 布尔类型
        else if (type.contains("boolean") || type.contains("bool")) {
            return "Boolean";
        }
        // 二进制类型
        else if (type.contains("blob") || type.contains("binary")) {
            return "byte[]";
        }
        // 其他类型
        else {
            return "String"; // 默认类型
        }
    }

    /**
     * 转换为驼峰命名
     */
    public static String toCamelCase(String str, boolean capitalizeFirst) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] parts = str.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) continue;

            if (i == 0 && !capitalizeFirst) {
                result.append(part.toLowerCase());
            } else {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    // ========== Setter方法 ==========

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }

    public void setPersistencePackage(String persistencePackage) {
        if ("jakarta".equals(persistencePackage) || "javax".equals(persistencePackage)) {
            this.persistencePackage = persistencePackage;
        } else {
            throw new IllegalArgumentException("persistencePackage必须是'javax'或'jakarta'");
        }
    }

    // ========== 辅助方法 ==========

    /**
     * 将包名转换为文件路径
     */
    private String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }

    /**
     * 获取表的主键列
     */
    private ColumnInfo getPrimaryKeyColumn(TableInfo tableInfo) {
        if (tableInfo.getPrimaryKeys().isEmpty()) {
            return null;
        }

        String primaryKeyName = tableInfo.getPrimaryKeys().get(0);
        return tableInfo.getColumns().stream()
                .filter(col -> col.getName().equals(primaryKeyName))
                .findFirst()
                .orElse(null);
    }
}