package com.old.silence.content.code.generator.executor;


import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.Template;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.old.silence.content.code.generator.engine.TemplateEngine;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.support.DataModelBuilder;
import com.old.silence.content.code.generator.support.FileOutputService;
import com.old.silence.content.code.generator.util.NameConverterUtils;

/**
 * Spring代码生成器
 * 负责为模板准备数据模型，并使用FreeMarker生成代码文件
 *
 * <p>数据模型分类：
 * <ol>
 *   <li>文件类名：className, variableName</li>
 *   <li>文件引用：通过${className}Command等方式在模板中构建</li>
 *   <li>API路径名：apiName, apiPath, 主键字段名</li>
 *   <li>主包名：basePackage, persistencePackage</li>
 *   <li>应用名：contextId（用于FeignClient）</li>
 *   <li>主键信息：primaryKeyType, primaryKeyField, primaryKeyColumn</li>
 *   <li>包名：在generateFile方法参数中传入</li>
 *   <li>查询字段：isQueryableField方法</li>
 *   <li>枚举字段：isEnumField方法, hasEnumField</li>
 *   <li>类型检测：hasInstantType, hasBigDecimalType等</li>
 *   <li>必填字符串：hasNotBlank, hasSize</li>
 *   <li>必填其他类型：hasNotNull</li>
 *   <li>集合类型：hasCollectionType</li>
 * </ol>
 *
 * @author moryzang
 */
public class SpringCodeGenerator implements com.old.silence.content.code.generator.api.CodeGenerator {

    /**
     * 审计字段列表（这些字段会被过滤，不出现在Command/Query等DTO中）
     */
    private final Set<String> auditFields = Set.of("id", "created_date", "created_by", "updated_date", "updated_by");


    /**
     * 需要@NotNull注解的Java类型集合
     */
    private final Set<String> notNullFields = Set.of("BigInteger", "Long", "Integer", "BigDecimal", "Instant", "Boolean");

    private final TemplateEngine templateEngine;
    private final DataModelBuilder dataModelBuilder;
    private final FileOutputService fileOutputService;

    public SpringCodeGenerator(TemplateLoader databaseTemplateLoader,
                               DataModelBuilder dataModelBuilder,
                               FileOutputService fileOutputService) {
        this.templateEngine = TemplateEngine.of(databaseTemplateLoader);
        this.dataModelBuilder = dataModelBuilder;
        this.fileOutputService = fileOutputService;
    }


    /**
     * 渲染模板并生成文件
     */
    @Override
    public void generateFile(TableInfo tableInfo, String outputDir,
                             String basePackageName, String packageName,
                             String templateName, String suffix) throws Exception {
        String content = renderTemplate(tableInfo, basePackageName, packageName, templateName);
        String fileName = fileOutputService.fileName(tableInfo, suffix);
        File outputFile = new File(outputDir, fileName);

        outputFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
        }
        System.out.println("生成 " + suffix + ": " + outputFile.getAbsolutePath());
    }

    public String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName,
                                 String templateName) {
        return renderTemplate(tableInfo, basePackageName, packageName, templateName, null);
    }

    /**
     * 渲染模板返回代码内容（用于预览）
     */
    @Override
    public String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName,
                                 String templateName, Map<String, Object> customerDataModel) {
        Map<String, Object> dataModel = dataModelBuilder.build(tableInfo, basePackageName, packageName, customerDataModel);
        Template template = templateEngine.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        try {
            template.process(dataModel, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    /**
     * 获取生成的文件名
     */
    public static String getJavaType(ColumnInfo column) {
        return convertToJavaType(column);
    }

    /**
     * 创建基础数据模型供模板使用
     *
     * @param tableInfo 表信息
     * @return 模板数据模型
     */
    private Map<String, Object> createBaseDataModel(TableInfo tableInfo) {
        Map<String, Object> dataModel = new HashMap<>();

        // 过滤审计字段
        tableInfo.setColumnInfos(tableInfo.getColumnInfos().stream()
                .filter(columnInfo -> !auditFields.contains(columnInfo.getOriginalName()))
                .collect(Collectors.toList()));

        // Keep a sane default; renderTemplate will override if provided
        dataModel.put("tableInfo", tableInfo);
        dataModel.put("columnInfos", tableInfo.getColumnInfos());

        // ========== 1. 文件类名相关 ==========
        // className: User, Order, Product (大驼峰)
        dataModel.put("className", toCamelCase(tableInfo.getTableName(), true));
        // variableName: user, order, product (小驼峰)
        dataModel.put("variableName", toCamelCase(tableInfo.getTableName(), false));

        // ========== 2. 文件引用其他文件名 ==========
        // 例如：UserCommand, UserQuery, UserView, UserService, UserRepository等
        // 在模板中使用: ${className}Command, ${className}Query, ${className}View

        // ========== 3. API路径名和PathVariable字段名 ==========
        // apiName: users, orders, products (复数形式，用于REST路径)
        dataModel.put("apiName", toPluralVariableName(tableInfo.getTableName()));
        // apiPath: /users, /orders, /products
        dataModel.put("apiPath", "/" + toPluralVariableName(tableInfo.getTableName()));

        // ========== 4. 主包名 ==========
        dataModel.put("persistencePackage", "");
        // 在模板中通过 ${basePackage}.xxx 来构建完整包名

        // ========== 5. 应用名（用于FeignClient的contextId） ==========
        // contextId: user, order-item, product-category
        dataModel.put("contextId", tableInfo.getTableName().replace("_", "-"));

        // ========== 6. 主键类型和主键字段 ==========
        ColumnInfo primaryKey = getPrimaryKeyColumn(tableInfo);
        if (primaryKey != null) {
            // primaryKeyType: BigInteger, Long, String等
            dataModel.put("primaryKeyType", convertToJavaType(primaryKey));
            // primaryKeyField: id
            dataModel.put("primaryKeyField", primaryKey.getFieldName() != null ?
                    primaryKey.getFieldName() : toCamelCase(primaryKey.getOriginalName(), false));
            // primaryKeyColumn: id (数据库列名)
            dataModel.put("primaryKeyColumn", primaryKey.getOriginalName());
        } else {
            // 默认值
            dataModel.put("primaryKeyType", "BigInteger");
            dataModel.put("primaryKeyField", "id");
            dataModel.put("primaryKeyColumn", "id");
        }

        // ========== 7. 各种文件的包名（在generateFile方法中通过参数传入） ==========
        // 例如：basePackage + .domain.model, basePackage + .api.dto 等

        // ========== 8. 查询字段判断工具 ==========
        dataModel.put("isQueryableField", new QueryableFieldMethod());

        // ========== 9. 枚举字段判断工具 ==========
        dataModel.put("isEnumField", new EnumFieldMethod());
        dataModel.put("hasEnumField", hasEnumField(tableInfo));

        // ========== 10-12. 特殊类型检测（用于import判断） ==========
        dataModel.put("hasInstantType", hasColumnType(tableInfo, "Instant"));
        dataModel.put("hasBigDecimalType", hasColumnType(tableInfo, "BigDecimal"));
        dataModel.put("hasBigIntegerType", hasColumnType(tableInfo, "BigInteger"));
        dataModel.put("hasLocalDateType", hasColumnType(tableInfo, "LocalDate"));
        dataModel.put("hasLocalTimeType", hasColumnType(tableInfo, "LocalTime"));

        // ========== 13. 必填字段判断（字符串） ==========
        dataModel.put("hasNotBlank", hasNotBlankAnnotation(tableInfo));
        dataModel.put("hasSize", hasSizeAnnotation(tableInfo));

        // ========== 14. 必填字段判断（数字、枚举、布尔等） ==========
        dataModel.put("hasNotNull", hasNotNull(tableInfo));

        // ========== 15. 集合类型判断（用于嵌套对象） ==========
        dataModel.put("hasCollectionType", hasCollectionType(tableInfo));

        // ========== 16. Map类型判断（用于attributes扩展属性） ==========
        dataModel.put("hasMapType", hasMapType(tableInfo));

        // ========== FreeMarker工具方法 ==========
        dataModel.put("getJavaType", new TypeConverterMethod());
        dataModel.put("toCamelCase", new NameConverterMethod());
        dataModel.put("isNumericType", new NumericTypeMethod());
        dataModel.put("isBooleanType", new BooleanTypeMethod());
        dataModel.put("isInstantType", new InstantTypeMethod());
        dataModel.put("isCollectionType", new CollectionTypeMethod());
        dataModel.put("isStringType", new StringTypeMethod());
        dataModel.put("needsColumnAnnotation", new NeedsColumnAnnotationMethod());
        dataModel.put("isMapField", new IsMapFieldMethod());

        return dataModel;
    }

    /**
     * 检查是否需要@NotBlank注解（用于非空字符串）
     */
    private boolean hasNotBlankAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        convertToJavaType(column).equals("String"));
    }

    /**
     * 检查是否需要@Size注解（用于带长度限制的字符串）
     */
    private boolean hasSizeAnnotation(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        convertToJavaType(column).equals("String") &&
                        column.getLength() != null && column.getLength() > 0);
    }

    /**
     * 检查是否需要@NotNull注解（用于非空的数字、枚举、布尔等）
     */
    private boolean hasNotNull(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> !column.getNullable() &&
                        notNullFields.contains(convertToJavaType(column)));
    }

    /**
     * 检查是否有枚举字段
     */
    private boolean hasEnumField(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(SpringCodeGenerator::isEnumField);
    }

    /**
     * 检查是否有集合类型字段
     */
    private boolean hasCollectionType(TableInfo tableInfo) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> {
                    String type = convertToJavaType(column);
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
                    String type = convertToJavaType(column);
                    return type.startsWith("Map<");
                });
    }


    /**
     * 判断字段是否适合作为查询条件
     *
     * <p>排除规则：
     * <ul>
     *   <li>大文本字段（text, blob, clob）</li>
     *   <li>二进制字段（binary）</li>
     *   <li>JSON字段</li>
     *   <li>密码、密钥、令牌等敏感字段</li>
     *   <li>文件引用字段</li>
     * </ul>
     *
     * @param column 列信息
     * @return 是否可查询
     */
    private static boolean isQueryableField(ColumnInfo column) {
        if (column == null) return false;

        String type = column.getType().toLowerCase();
        String name = column.getOriginalName().toLowerCase();

        // 排除大字段、二进制字段
        boolean isExcludedType = type.contains("text") ||
                type.contains("blob") ||
                type.contains("binary") ||
                type.contains("json") ||
                type.contains("clob");

        // 排除敏感字段和文件引用字段
        boolean isExcludedName = name.contains("password") ||
                name.contains("secret") ||
                name.contains("token") ||
                name.contains("salt") ||  // 密码盐值
                name.contains("reference") ||
                name.endsWith("_ref") ||
                name.endsWith("_url") ||  // URL字段通常不用于查询
                name.contains("content");  // 内容字段

        return !isExcludedType && !isExcludedName;
    }

    /**
     * 判断字段是否是枚举类型
     *
     * <p>识别规则：字段名包含status、type、flag、state等关键词
     *
     * @param column 列信息
     * @return 是否是枚举字段
     */
    private static boolean isEnumField(ColumnInfo column) {
        if (column == null) return false;
        String type = column.getType().toLowerCase();
        String name = column.getOriginalName().toLowerCase();

        // tinyint类型很可能是枚举
        boolean isTinyInt = type.startsWith("tinyint");

        // 字段名包含枚举相关关键词
        boolean hasEnumKeyword = name.contains("status") ||
                name.contains("type") ||
                name.contains("flag") ||
                name.contains("state") ||
                name.contains("category");

        return isTinyInt || hasEnumKeyword;
    }

    // ========== 类型检测方法实现 ==========


    private boolean hasColumnType(TableInfo tableInfo, String columnType) {
        return tableInfo.getColumnInfos().stream()
                .anyMatch(column -> convertToJavaType(column).equals(columnType));
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

            Object arg = arguments.getFirst();
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
            }
            return "Byte";
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
     * 获取表的主键列
     *
     * @param tableInfo 表信息
     * @return 主键列信息，如果没有主键返回null
     */
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


    public static class NumericTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isNumericType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
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

    public static class CollectionTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) throws TemplateModelException {
            if (arguments.size() != 1) {
                throw new TemplateModelException("isCollectionType方法需要1个参数");
            }

            // 获取参数并转换为字符串
            String fieldType = convertToString(arguments.getFirst());
            return isCollectionType(fieldType);
        }

        private String convertToString(Object arg) throws TemplateModelException {
            if (arg == null) {
                return null;
            }

            if (arg instanceof SimpleScalar) {
                return ((SimpleScalar) arg).getAsString();
            } else if (arg instanceof TemplateBooleanModel) {
                return String.valueOf(((TemplateBooleanModel) arg).getAsBoolean());
            } else if (arg instanceof TemplateNumberModel) {
                return String.valueOf(((TemplateNumberModel) arg).getAsNumber());
            } else if (arg instanceof freemarker.ext.beans.GenericObjectModel) {
                // 处理Java对象
                Object wrapped = ((freemarker.ext.beans.GenericObjectModel) arg).getWrappedObject();
                return wrapped != null ? wrapped.toString() : null;
            } else if (arg instanceof freemarker.template.AdapterTemplateModel) {
                // 处理适配器模型
                Object adapted = ((freemarker.template.AdapterTemplateModel) arg).getAdaptedObject(Object.class);
                return adapted != null ? adapted.toString() : null;
            } else {
                // 尝试直接转换为字符串
                return arg.toString();
            }
        }

        private boolean isCollectionType(String fieldType) {
            if (fieldType == null || fieldType.trim().isEmpty()) {
                return false;
            }

            fieldType = fieldType.trim();

            // 常见的集合类型
            return fieldType.contains("List<") ||
                    fieldType.contains("Set<") ||
                    fieldType.contains("Collection<") ||
                    fieldType.contains("Map<") ||
                    fieldType.equals("List") ||
                    fieldType.equals("Set") ||
                    fieldType.equals("Collection") ||
                    fieldType.equals("Map") ||
                    fieldType.equals("ArrayList") ||
                    fieldType.equals("LinkedList") ||
                    fieldType.equals("HashSet") ||
                    fieldType.equals("TreeSet") ||
                    fieldType.equals("Vector") ||
                    fieldType.equals("Stack") ||
                    fieldType.equals("HashMap") ||
                    fieldType.equals("TreeMap") ||
                    fieldType.equals("LinkedHashMap") ||
                    fieldType.equals("ConcurrentHashMap") ||
                    fieldType.endsWith("[]");  // 数组
        }
    }

    /**
     * 字符串类型判断方法 - 用于FreeMarker模板
     */
    public static class StringTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isStringType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return isStringType(column);
        }
    }

    /**
     * @Column 注解判断方法 - 用于FreeMarker模板
     *
     * <p>检查boolean字段是否需要@Column注解
     * <p>规则：字段名以"is"开头且下一个字符是大写（如isDeleted -> is_deleted）
     */
    public static class NeedsColumnAnnotationMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("needsColumnAnnotation方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return needsColumnAnnotation(column);
        }
    }

    /**
     * Map字段判断方法 - 用于FreeMarker模板
     *
     * <p>检查字段是否是Map类型的扩展属性
     * <p>规则：字段名为"attributes"或"extAttributes"且类型为Map
     */
    public static class IsMapFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isMapField方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return isMapField(column);
        }
    }

    // ========== 类型判断逻辑 ==========

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

    private static boolean isStringType(ColumnInfo column) {
        if (column == null) return false;
        String javaType = convertToJavaType(column);
        return javaType.equals("String");
    }

    /**
     * 判断boolean字段是否需要@Column注解
     *
     * <p>检查规则：
     * <ul>
     *   <li>字段名以"is"开头</li>
     *   <li>第3个字符是大写字母（如isDeleted）</li>
     *   <li>字段类型是Boolean</li>
     * </ul>
     *
     * <p>示例：
     * <ul>
     *   <li>isDeleted -> true (需要@Column(name="is_deleted"))</li>
     *   <li>stickyTop -> false (不需要)</li>
     *   <li>status -> false (不需要)</li>
     * </ul>
     *
     * @param column 列信息
     * @return 是否需要@Column注解
     */
    private static boolean needsColumnAnnotation(ColumnInfo column) {
        if (column == null) return false;

        String fieldName = column.getFieldName();
        String javaType = convertToJavaType(column);

        // 检查是否是Boolean类型
        if (!javaType.equals("Boolean") && !javaType.equals("boolean")) {
            return false;
        }

        // 检查字段名是否以"is"开头且第3个字符是大写
        if (fieldName != null && fieldName.length() > 2 &&
                fieldName.startsWith("is") &&
                Character.isUpperCase(fieldName.charAt(2))) {
            return true;
        }

        return false;
    }

    /**
     * 判断字段是否是Map类型的扩展属性
     *
     * <p>检查规则：
     * <ul>
     *   <li>字段名为"attributes"或"extAttributes"</li>
     *   <li>字段类型为Map<String, Object></li>
     * </ul>
     *
     * <p>此类字段需要添加@Convert(converter=MapAttributeConverter.class)
     *
     * @param column 列信息
     * @return 是否是Map扩展属性字段
     */
    private static boolean isMapField(ColumnInfo column) {
        if (column == null) return false;

        String fieldName = column.getFieldName();
        String javaType = convertToJavaType(column);

        // 检查字段名是否为attributes或extAttributes
        boolean isAttributesField = "attributes".equals(fieldName) ||
                "extAttributes".equals(fieldName);

        // 检查字段类型是否为Map
        boolean isMapType = javaType.startsWith("Map<");

        return isAttributesField && isMapType;
    }

    // ========== 辅助方法 ==========

    /**
     * 从FreeMarker参数中提取ColumnInfo对象
     */
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