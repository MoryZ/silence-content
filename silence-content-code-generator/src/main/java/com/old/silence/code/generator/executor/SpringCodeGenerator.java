package com.old.silence.code.generator.executor;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.util.NameConverterUtils;

public class SpringCodeGenerator {

    private final Set<String> auditFields = Set.of("id", "created_date", "created_by", "updated_date", "updated_by");
    private final Set<String> notNullFields = Set.of("BigInteger", "Long", "Integer", "BigDecimal", "Instant", "Boolean");

    private final Configuration freemarkerConfig;
    private final String persistencePackage;
    private final boolean useLombok;

    public SpringCodeGenerator(String persistencePackage, boolean useLombok) {
        this.persistencePackage = persistencePackage;
        this.useLombok = useLombok;
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    public void generateFile(TableInfo tableInfo, String outputDir,
                             String basePackageName, String packageName,
                             String templateName, String suffix) throws Exception {
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        dataModel.put("authorName", "moryzang");
        dataModel.put("basePackage", basePackageName);
        dataModel.put("packageName", basePackageName + packageName);
        dataModel.put("applicationName", "silence-content-service");
        dataModel.put("contextId", tableInfo.getTableName().replace("_", "-"));
        dataModel.put("primaryType", "BigInteger");

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

        tableInfo.setColumnInfos(tableInfo.getColumnInfos().stream().filter(columnInfo -> !auditFields.contains(columnInfo.getOriginalName()))
                .collect(Collectors.toList()));
        dataModel.put("tableInfo", tableInfo);
        dataModel.put("columnInfos", tableInfo.getColumnInfos());

        dataModel.put("className", toCamelCase(tableInfo.getTableName(), true));
        dataModel.put("apiName", toPluralVariableName(tableInfo.getTableName()));

        dataModel.put("persistencePackage", persistencePackage);

        // 添加工具方法
        dataModel.put("getJavaType", new SpringCodeGenerator.TypeConverterMethod());
        dataModel.put("toCamelCase", new SpringCodeGenerator.NameConverterMethod());
        dataModel.put("isQueryableField", new SpringCodeGenerator.QueryableFieldMethod());
        dataModel.put("isEnumField", new SpringCodeGenerator.EnumFieldMethod());


        dataModel.put("hasInstantType", hasColumnType(tableInfo, "Instant"));
        dataModel.put("hasBigDecimalType", hasColumnType(tableInfo, "BigDecimal"));
        dataModel.put("hasBigIntegerType", hasColumnType(tableInfo, "BigInteger"));

        // 检查是否需要导入的类型
        dataModel.put("hasSize", hasSizeAnnotation(tableInfo));
        dataModel.put("hasNotBlank", hasNotBlankAnnotation(tableInfo));
        dataModel.put("hasNotNull", hasNotNull(tableInfo));

        // 添加工具方法
        dataModel.put("isNumericType", new SpringCodeGenerator.NumericTypeMethod());
        dataModel.put("isBooleanType", new SpringCodeGenerator.BooleanTypeMethod());
        dataModel.put("isInstantType", new SpringCodeGenerator.InstantTypeMethod());
        dataModel.put("isCollectionType", false);



        return dataModel;
    }

    // 检查是否需要NotBlank注解
    private boolean hasNotBlankAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        convertToJavaType(column).equals("String"));
    }

    private boolean hasSizeAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        convertToJavaType(column).equals("String") &&
                        column.getLength() != null && column.getLength() > 0);
    }

    // 检查是否需要NotNull注解
    private boolean hasNotNull(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        notNullFields.contains(convertToJavaType(column)));
    }


    private static boolean isQueryableField(ColumnInfo column) {
        if (column == null) return false;

        String type = column.getType().toLowerCase();
        String name = column.getOriginalName().toLowerCase();

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

    private static boolean isEnumField(ColumnInfo column) {
        if (column == null) return false;
        String name = column.getOriginalName().toLowerCase();
        return name.contains("status") || name.contains("type");
    }

    // ========== 类型检测方法实现 ==========



    private boolean hasColumnType(TableInfo tableInfo, String columnType) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> convertToJavaType(column).equals(columnType));
    }

    public String getPersistencePackage() {
        return persistencePackage;
    }

    public boolean isUseLombok() {
        return useLombok;
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

            // 处理 FreeMarker 的包装类型
            Object arg = arguments.getFirst();
            ColumnInfo column;

            if (arg instanceof ColumnInfo) {
                column = (ColumnInfo) arg;
            } else if (arg instanceof freemarker.ext.beans.BeanModel) {
                // FreeMarker 包装的对象
                column = (ColumnInfo) ((freemarker.ext.beans.BeanModel) arg).getWrappedObject();
            } else {
                throw new RuntimeException("getJavaType方法参数类型错误，期望ColumnInfo，实际: " + arg.getClass());
            }

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

            // 处理第一个参数
            Object nameArg = arguments.get(0);
            String name = extractString(nameArg);

            // 处理第二个参数 - 正确处理 FreeMarker 的布尔类型
            Object capitalizeArg = arguments.get(1);
            boolean capitalizeFirst = extractBoolean(capitalizeArg);

            return capitalizeFirst ? Character.toUpperCase(name.charAt(0)) +
                    name.substring(1) : name;
        }

        /**
         * 从 FreeMarker 参数中提取字符串
         */
        private String extractString(Object arg) {
            if (arg instanceof String) {
                return (String) arg;
            } else {
                return arg.toString();
            }
        }

        /**
         * 从 FreeMarker 参数中提取布尔值
         */
        private boolean extractBoolean(Object arg) {
            // 处理 FreeMarker 的布尔包装类型
            if (arg instanceof freemarker.template.TemplateBooleanModel) {
                try {
                    return ((freemarker.template.TemplateBooleanModel) arg).getAsBoolean();
                } catch (TemplateModelException e) {
                    throw new RuntimeException(e);
                }
            } else if (arg instanceof Boolean) {
                return (Boolean) arg;
            } else if (arg instanceof String) {
                return Boolean.parseBoolean((String) arg);
            } else {
                // 默认处理
                return Boolean.parseBoolean(arg.toString());
            }
        }
    }

    public static class QueryableFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isQueryableField方法需要1个参数");
            }

            Object arg = arguments.get(0);
            ColumnInfo column;

            if (arg instanceof ColumnInfo) {
                column = (ColumnInfo) arg;
            } else if (arg instanceof freemarker.ext.beans.BeanModel) {
                column = (ColumnInfo) ((freemarker.ext.beans.BeanModel) arg).getWrappedObject();
            } else {
                throw new RuntimeException("isQueryableField方法参数类型错误: " + arg.getClass());
            }

            return isQueryableField(column);
        }
    }

    public static class EnumFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isEnumField方法需要1个参数");
            }

            Object arg = arguments.get(0);
            ColumnInfo column;

            if (arg instanceof ColumnInfo) {
                column = (ColumnInfo) arg;
            } else if (arg instanceof freemarker.ext.beans.BeanModel) {
                column = (ColumnInfo) ((freemarker.ext.beans.BeanModel) arg).getWrappedObject();
            } else {
                throw new RuntimeException("isEnumField方法参数类型错误: " + arg.getClass());
            }

            return isEnumField(column);
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

            return tableInfo.getColumnInfos().stream()
                    .filter(col -> col.getOriginalName().equals(columnName))
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
            return "BigInteger";
        } else if (type.contains("int")) {
            return "Long";
        } else if (type.contains("smallint") || type.contains("tinyint")) {
            if (type.contains("(1)") || type.contains("boolean")) {
                return "Boolean";
            } else if (type.contains("(3)")) {
                return "Byte";
            }
            return "Integer";
        }// 浮点类型
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
                type.contains("set") || type.contains("json")) {
            return "String";
        }
        // 日期时间类型
        else if (type.contains("datetime") || type.contains("timestamp")) {
            return "Instant";
        } else if (type.contains("date")) {
            return "LocalDate";
        } else if (type.contains("time")) {
            return "LocalTime";
        }
        // 布尔类型
        else if (type.contains("boolean") || type.contains("bool") || type.contains("bit")) {
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
        return NameConverterUtils.toCamelCase(str, capitalizeFirst);
    }

    /**
     * 转换为复数形式命名
     */
    public static String toPluralVariableName(String str) {
        return NameConverterUtils.toPluralVariableName(str);
    }



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
        return tableInfo.getColumnInfos().stream()
                .filter(col -> col.getOriginalName().equals(primaryKeyName))
                .findFirst()
                .orElse(null);
    }


    public static class NumericTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isNumericType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.get(0));
            return isNumericType(column);
        }
    }

    public static class BooleanTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isBooleanType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.get(0));
            return isBooleanType(column);
        }
    }

    public static class InstantTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isInstantType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return isInstantType(column);
        }
    }

    // 类型判断逻辑
    private static boolean isCollectionType(ColumnInfo column, String type) {
        if (column == null) return false;
        String javaType = convertToJavaType(column);
        return javaType.startsWith(type) || javaType.equals(type);
    }

    private static boolean isNumericType(ColumnInfo column) {
        if (column == null) return false;
        String javaType = convertToJavaType(column);
        return javaType.equals("Integer") ||
                javaType.equals("Long") ||
                javaType.equals("BigInteger") ||
                javaType.equals("BigDecimal") ||
                javaType.equals("Double") ||
                javaType.equals("Float") ||
                javaType.equals("Short") ||
                javaType.equals("Byte");
    }

    private static boolean isBooleanType(ColumnInfo column) {
        if (column == null) return false;
        String javaType = convertToJavaType(column);
        return javaType.equals("Boolean") || javaType.equals("boolean");
    }

    private static boolean isInstantType(ColumnInfo column) {
        if (column == null) return false;
        String javaType = convertToJavaType(column);
        return javaType.equals("Instant");
    }

    // 从参数中提取ColumnInfo的辅助方法
    private static ColumnInfo extractColumnInfo(Object arg) {
        if (arg instanceof ColumnInfo) {
            return (ColumnInfo) arg;
        } else if (arg instanceof freemarker.ext.beans.BeanModel) {
            Object wrapped = ((freemarker.ext.beans.BeanModel) arg).getWrappedObject();
            if (wrapped instanceof ColumnInfo) {
                return (ColumnInfo) wrapped;
            }
        }
        throw new RuntimeException("无法从参数类型 " + arg.getClass() + " 提取ColumnInfo");
    }
}