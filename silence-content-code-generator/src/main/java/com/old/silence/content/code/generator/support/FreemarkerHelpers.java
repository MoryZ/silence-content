package com.old.silence.content.code.generator.support;

import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.util.List;

public final class FreemarkerHelpers {
    private FreemarkerHelpers() {}

    // ===== FreeMarker 方法实现 =====

    public static class TypeConverterMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("getJavaType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return convertToJavaType(column);
        }
    }

    public static class NameConverterMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 2) {
                throw new RuntimeException("toCamelCase方法需要2个参数");
            }
            Object nameArg = arguments.get(0);
            String name = extractString(nameArg);

            Object capitalizeArg = arguments.get(1);
            boolean capitalizeFirst = extractBoolean(capitalizeArg);

            return capitalizeFirst ? Character.toUpperCase(name.charAt(0)) + name.substring(1) : name;
        }

        private String extractString(Object arg) {
            if (arg instanceof String) {
                return (String) arg;
            } else {
                return arg.toString();
            }
        }

        private boolean extractBoolean(Object arg) {
            if (arg instanceof TemplateBooleanModel) {
                try {
                    return ((TemplateBooleanModel) arg).getAsBoolean();
                } catch (TemplateModelException e) {
                    throw new RuntimeException(e);
                }
            } else if (arg instanceof Boolean) {
                return (Boolean) arg;
            } else if (arg instanceof String) {
                return Boolean.parseBoolean((String) arg);
            } else {
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
            ColumnInfo column = extractColumnInfo(arguments.get(0));
            return isQueryableField(column);
        }
    }

    public static class EnumFieldMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isEnumField方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            return isEnumField(column);
        }
    }

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

    public static class NumericTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isNumericType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            String javaType = convertToJavaType(column);
            return javaType.equals("Integer") || javaType.equals("Long") || javaType.equals("BigInteger") ||
                    javaType.equals("BigDecimal") || javaType.equals("Double") || javaType.equals("Float") ||
                    javaType.equals("Short") || javaType.equals("Byte");
        }
    }

    public static class BooleanTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isBooleanType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.get(0));
            String javaType = convertToJavaType(column);
            return javaType.equals("Boolean") || javaType.equals("boolean");
        }
    }

    public static class InstantTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isInstantType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            String javaType = convertToJavaType(column);
            return javaType.equals("Instant");
        }
    }

    public static class CollectionTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) throws TemplateModelException {
            if (arguments.size() != 1) {
                throw new TemplateModelException("isCollectionType方法需要1个参数");
            }
            String fieldType = convertToString(arguments.getFirst());
            if (fieldType == null) return false;
            fieldType = fieldType.trim();
            return fieldType.contains("List<") || fieldType.contains("Set<") || fieldType.contains("Collection<") ||
                    fieldType.contains("Map<") || fieldType.equals("List") || fieldType.equals("Set") ||
                    fieldType.equals("Collection") || fieldType.equals("Map") || fieldType.equals("ArrayList") ||
                    fieldType.equals("LinkedList") || fieldType.equals("HashSet") || fieldType.equals("TreeSet") ||
                    fieldType.equals("Vector") || fieldType.equals("Stack") || fieldType.equals("HashMap") ||
                    fieldType.equals("TreeMap") || fieldType.equals("LinkedHashMap") ||
                    fieldType.equals("ConcurrentHashMap") || fieldType.endsWith("[]");
        }

        private String convertToString(Object arg) throws TemplateModelException {
            if (arg == null) return null;
            if (arg instanceof SimpleScalar) {
                return ((SimpleScalar) arg).getAsString();
            } else if (arg instanceof TemplateBooleanModel) {
                return String.valueOf(((TemplateBooleanModel) arg).getAsBoolean());
            } else if (arg instanceof TemplateNumberModel) {
                return String.valueOf(((TemplateNumberModel) arg).getAsNumber());
            } else if (arg instanceof freemarker.ext.beans.GenericObjectModel) {
                Object wrapped = ((freemarker.ext.beans.GenericObjectModel) arg).getWrappedObject();
                return wrapped != null ? wrapped.toString() : null;
            } else if (arg instanceof freemarker.template.AdapterTemplateModel) {
                Object adapted = ((freemarker.template.AdapterTemplateModel) arg).getAdaptedObject(Object.class);
                return adapted != null ? adapted.toString() : null;
            } else {
                return arg.toString();
            }
        }
    }

    public static class StringTypeMethod implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) {
            if (arguments.size() != 1) {
                throw new RuntimeException("isStringType方法需要1个参数");
            }
            ColumnInfo column = extractColumnInfo(arguments.getFirst());
            String javaType = convertToJavaType(column);
            return javaType.equals("String");
        }
    }

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

    // ===== 供 DataModelBuilder 使用的静态方法 =====

    public static String getJavaType(ColumnInfo column) {
        return convertToJavaType(column);
    }

    // ===== 私有辅助逻辑（从原实现抽取） =====

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

    private static String convertToJavaType(ColumnInfo column) {
        if (column == null || column.getType() == null) {
            return "Object";
        }
        String type = column.getType().toLowerCase();
        if (type.contains("bigint")) {
            return "BigInteger";
        } else if (type.contains("int")) {
            return "Long";
        } else if (type.contains("smallint") || type.contains("tinyint")) {
            if (type.contains("(1)") || type.contains("boolean")) {
                return "Boolean";
            }
            return "Byte";
        } else if (type.contains("decimal") || type.contains("numeric")) {
            return "BigDecimal";
        } else if (type.contains("float")) {
            return "Float";
        } else if (type.contains("double") || type.contains("real")) {
            return "Double";
        } else if (type.contains("varchar") || type.contains("char") || type.contains("text") ||
                type.contains("enum") || type.contains("set") || type.contains("json")) {
            return "String";
        } else if (type.contains("datetime") || type.contains("timestamp")) {
            return "Instant";
        } else if (type.contains("date")) {
            return "LocalDate";
        } else if (type.contains("time")) {
            return "LocalTime";
        } else if (type.contains("boolean") || type.contains("bool") || type.contains("bit")) {
            return "Boolean";
        } else if (type.contains("blob") || type.contains("binary")) {
            return "byte[]";
        } else {
            return "String";
        }
    }

    private static boolean isQueryableField(ColumnInfo column) {
        if (column == null) return false;
        String type = column.getType().toLowerCase();
        String name = column.getOriginalName().toLowerCase();
        boolean isExcludedType = type.contains("text") || type.contains("blob") || type.contains("binary") ||
                type.contains("json") || type.contains("clob");
        boolean isExcludedName = name.contains("password") || name.contains("secret") || name.contains("token") ||
                name.contains("salt") || name.contains("reference") || name.endsWith("_ref") ||
                name.endsWith("_url") || name.contains("content");
        return !isExcludedType && !isExcludedName;
    }

    private static boolean isEnumField(ColumnInfo column) {
        if (column == null) return false;
        String type = column.getType().toLowerCase();
        String name = column.getOriginalName().toLowerCase();
        boolean isTinyInt = type.startsWith("tinyint");
        boolean hasEnumKeyword = name.contains("status") || name.contains("type") || name.contains("flag") ||
                name.contains("state") || name.contains("category");
        return isTinyInt || hasEnumKeyword;
    }

    private static boolean needsColumnAnnotation(ColumnInfo column) {
        if (column == null) return false;
        String fieldName = column.getFieldName();
        String javaType = convertToJavaType(column);
        if (!javaType.equals("Boolean") && !javaType.equals("boolean")) {
            return false;
        }
        return fieldName != null && fieldName.length() > 2 && fieldName.startsWith("is") &&
                Character.isUpperCase(fieldName.charAt(2));
    }

    private static boolean isMapField(ColumnInfo column) {
        if (column == null) return false;
        String fieldName = column.getFieldName();
        String javaType = convertToJavaType(column);
        boolean isAttributesField = "attributes".equals(fieldName) || "extAttributes".equals(fieldName);
        boolean isMapType = javaType.startsWith("Map<");
        return isAttributesField && isMapType;
    }
}
