package com.old.silence.code.generator.util;

import com.old.silence.code.generator.model.ColumnInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 示例值生成器
 * 根据字段名、表名、注释等信息生成智能示例值
 * 
 * @author moryzang
 */
public class ExampleValueGenerator {
    
    // 字段名到示例值的映射
    private static final Map<String, String> FIELD_NAME_EXAMPLES = new HashMap<>();
    
    static {
        // 名称类
        FIELD_NAME_EXAMPLES.put("title", "示例标题");
        FIELD_NAME_EXAMPLES.put("name", "示例名称");
        FIELD_NAME_EXAMPLES.put("username", "zhangsan");
        FIELD_NAME_EXAMPLES.put("nickname", "张三");
        FIELD_NAME_EXAMPLES.put("realname", "张三");
        FIELD_NAME_EXAMPLES.put("fullname", "张三");
        
        // 描述类
        FIELD_NAME_EXAMPLES.put("description", "这是一个示例描述信息");
        FIELD_NAME_EXAMPLES.put("content", "这是示例内容正文");
        FIELD_NAME_EXAMPLES.put("detail", "详细信息说明");
        FIELD_NAME_EXAMPLES.put("remark", "备注信息");
        FIELD_NAME_EXAMPLES.put("note", "备注");
        FIELD_NAME_EXAMPLES.put("comment", "评论内容");
        FIELD_NAME_EXAMPLES.put("summary", "摘要信息");
        
        // 联系信息类
        FIELD_NAME_EXAMPLES.put("email", "example@example.com");
        FIELD_NAME_EXAMPLES.put("phone", "13800138000");
        FIELD_NAME_EXAMPLES.put("mobile", "13800138000");
        FIELD_NAME_EXAMPLES.put("telephone", "010-12345678");
        FIELD_NAME_EXAMPLES.put("address", "北京市朝阳区示例街道123号");
        
        // URL类
        FIELD_NAME_EXAMPLES.put("url", "https://www.example.com");
        FIELD_NAME_EXAMPLES.put("link", "https://www.example.com/page");
        FIELD_NAME_EXAMPLES.put("website", "https://www.example.com");
        FIELD_NAME_EXAMPLES.put("image", "https://www.example.com/image.jpg");
        FIELD_NAME_EXAMPLES.put("avatar", "https://www.example.com/avatar.jpg");
        FIELD_NAME_EXAMPLES.put("icon", "https://www.example.com/icon.png");
        FIELD_NAME_EXAMPLES.put("cover", "https://www.example.com/cover.jpg");
        
        // 编码类
        FIELD_NAME_EXAMPLES.put("code", "CODE001");
        FIELD_NAME_EXAMPLES.put("sn", "SN20240101001");
        FIELD_NAME_EXAMPLES.put("no", "NO20240101001");
        FIELD_NAME_EXAMPLES.put("number", "NUM001");
        FIELD_NAME_EXAMPLES.put("orderNo", "ORD20240101001");
        
        // 状态类
        FIELD_NAME_EXAMPLES.put("status", "1");
        FIELD_NAME_EXAMPLES.put("state", "1");
        FIELD_NAME_EXAMPLES.put("type", "1");
        
        // 价格金额类
        FIELD_NAME_EXAMPLES.put("price", "99.99");
        FIELD_NAME_EXAMPLES.put("amount", "1000.00");
        FIELD_NAME_EXAMPLES.put("money", "1000.00");
        FIELD_NAME_EXAMPLES.put("cost", "50.00");
        FIELD_NAME_EXAMPLES.put("fee", "10.00");
        
        // 其他常见字段
        FIELD_NAME_EXAMPLES.put("author", "张三");
        FIELD_NAME_EXAMPLES.put("publisher", "示例出版社");
        FIELD_NAME_EXAMPLES.put("category", "示例分类");
        FIELD_NAME_EXAMPLES.put("tag", "示例标签");
        FIELD_NAME_EXAMPLES.put("keyword", "示例关键词");
        FIELD_NAME_EXAMPLES.put("subject", "示例主题");
        FIELD_NAME_EXAMPLES.put("topic", "示例话题");
    }
    
    // 表名到示例值前缀的映射
    private static final Map<String, String> TABLE_NAME_PREFIXES = new HashMap<>();
    
    static {
        TABLE_NAME_PREFIXES.put("user", "用户");
        TABLE_NAME_PREFIXES.put("content", "内容");
        TABLE_NAME_PREFIXES.put("article", "文章");
        TABLE_NAME_PREFIXES.put("book", "图书");
        TABLE_NAME_PREFIXES.put("product", "产品");
        TABLE_NAME_PREFIXES.put("order", "订单");
        TABLE_NAME_PREFIXES.put("category", "分类");
        TABLE_NAME_PREFIXES.put("tag", "标签");
    }

    /**
     * 根据字段信息生成示例值
     * 
     * @param column 字段信息
     * @param tableName 表名（可选，用于上下文推断）
     * @return 示例值
     */
    public static Object generateExampleValue(ColumnInfo column, String tableName) {
        if (column == null) {
            return "示例值";
        }
        
        String fieldName = column.getFieldName() != null ? column.getFieldName().toLowerCase() : "";
        String originalName = column.getOriginalName() != null ? column.getOriginalName().toLowerCase() : "";
        String type = column.getType() != null ? column.getType().toLowerCase() : "";
        String comment = column.getComment() != null ? column.getComment() : "";
        Integer length = column.getLength();
        
        // 1. 先根据数据类型判断
        if (type.contains("int") || type.contains("bigint") || type.contains("tinyint") || type.contains("smallint")) {
            return generateIntegerExample(fieldName, originalName, comment);
        } else if (type.contains("decimal") || type.contains("float") || type.contains("double") || type.contains("numeric")) {
            return generateDecimalExample(fieldName, originalName, comment);
        } else if (type.contains("date") || type.contains("time") || type.contains("timestamp") || type.contains("datetime")) {
            return generateDateTimeExample(fieldName, originalName, comment, type);
        } else if (type.contains("bit") || type.contains("boolean")) {
            return generateBooleanExample(fieldName, originalName, comment);
        } else if (type.contains("json")) {
            return generateJsonExample(fieldName, originalName, comment);
        } else if (type.contains("varchar") || type.contains("char") || type.contains("text")) {
            return generateStringExample(fieldName, originalName, comment, tableName, length);
        }
        
        return "示例值";
    }
    
    /**
     * 生成整数类型示例值
     */
    private static Object generateIntegerExample(String fieldName, String originalName, String comment) {
        // 如果是ID字段，返回示例ID
        if (fieldName.contains("id") || originalName.contains("id")) {
            return 1L;
        }
        
        // 如果是状态字段，从注释中提取状态值
        if (fieldName.contains("status") || fieldName.contains("state") || fieldName.contains("type")) {
            if (comment.contains("1") || comment.contains("已") || comment.contains("启用")) {
                return 1;
            }
            return 1;
        }
        
        // 如果是数量、计数类字段
        if (fieldName.contains("count") || fieldName.contains("num") || fieldName.contains("quantity")) {
            return 10;
        }
        
        // 如果是排序字段
        if (fieldName.contains("sort") || fieldName.contains("order") || fieldName.contains("sequence")) {
            return 1;
        }
        
        return 1;
    }
    
    /**
     * 生成小数类型示例值
     */
    private static Object generateDecimalExample(String fieldName, String originalName, String comment) {
        // 价格类字段
        if (fieldName.contains("price") || fieldName.contains("amount") || fieldName.contains("money") || 
            fieldName.contains("cost") || fieldName.contains("fee")) {
            return 99.99;
        }
        
        // 百分比、比率类字段
        if (fieldName.contains("rate") || fieldName.contains("percent") || fieldName.contains("ratio")) {
            return 0.95;
        }
        
        // 评分类字段
        if (fieldName.contains("score") || fieldName.contains("rating")) {
            return 4.5;
        }
        
        return 99.99;
    }
    
    /**
     * 生成日期时间类型示例值
     */
    private static Object generateDateTimeExample(String fieldName, String originalName, String comment, String columnType) {
        String lowerType = columnType != null ? columnType.toLowerCase() : "";
        
        // datetime 和 timestamp 对应 Instant 类型，使用 UTC 时间格式
        if (lowerType.contains("timestamp") || lowerType.contains("datetime")) {
            // UTC时间格式：2024-01-15T10:30:00Z
            // 注意：这里使用当前时间，实际应该是UTC时间，但为了示例使用当前时间
            LocalDateTime now = LocalDateTime.now();
            return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T" + 
                   now.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "Z";
        }
        
        // date 对应 LocalDate，格式：yyyy-MM-dd
        if (lowerType.equals("date") || (lowerType.contains("date") && !lowerType.contains("time") && !lowerType.contains("timestamp"))) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        
        // time 对应 LocalTime，格式：HH:mm:ss
        if (lowerType.equals("time") || (lowerType.contains("time") && !lowerType.contains("date") && !lowerType.contains("timestamp"))) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        
        // 默认情况（兼容旧代码）
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        if (fieldName.contains("date") || originalName.contains("date")) {
            if (fieldName.contains("time") || originalName.contains("time")) {
                return now;
            }
            return date;
        }
        
        if (fieldName.contains("time") || originalName.contains("time")) {
            return now;
        }
        
        // 创建时间、更新时间
        if (fieldName.contains("created") || fieldName.contains("updated") || 
            fieldName.contains("modified") || fieldName.contains("deleted")) {
            return now;
        }
        
        // 发布时间、生效时间等
        if (fieldName.contains("publish") || fieldName.contains("effective") || 
            fieldName.contains("expire") || fieldName.contains("start") || fieldName.contains("end")) {
            return date;
        }
        
        return date;
    }
    
    /**
     * 生成布尔类型示例值
     */
    private static Object generateBooleanExample(String fieldName, String originalName, String comment) {
        // 如果是is开头的字段，通常表示是否
        if (fieldName.startsWith("is") || fieldName.startsWith("has") || fieldName.startsWith("can")) {
            // 根据字段名判断默认值
            if (fieldName.contains("delete") || fieldName.contains("disable") || fieldName.contains("hidden")) {
                return false;
            }
            return true;
        }
        
        return true;
    }
    
    /**
     * 生成JSON类型示例值
     */
    private static Object generateJsonExample(String fieldName, String originalName, String comment) {
        Map<String, Object> json = new HashMap<>();
        json.put("key1", "value1");
        json.put("key2", "value2");
        return json;
    }
    
    /**
     * 生成字符串类型示例值
     */
    private static Object generateStringExample(String fieldName, String originalName, String comment, 
                                                String tableName, Integer length) {
        // 1. 先尝试从字段名映射中获取
        String example = findExampleByFieldName(fieldName, originalName);
        if (example != null) {
            return limitLength(example, length);
        }
        
        // 2. 从注释中提取信息
        example = extractExampleFromComment(comment, fieldName);
        if (example != null) {
            return limitLength(example, length);
        }
        
        // 3. 根据表名和字段名组合生成
        example = generateByTableAndField(tableName, fieldName, originalName);
        if (example != null) {
            return limitLength(example, length);
        }
        
        // 4. 根据字段名模式推断
        example = inferFromFieldNamePattern(fieldName, originalName);
        if (example != null) {
            return limitLength(example, length);
        }
        
        // 5. 默认值
        return limitLength("示例" + (fieldName.isEmpty() ? "值" : fieldName), length);
    }
    
    /**
     * 从字段名映射中查找示例值
     */
    private static String findExampleByFieldName(String fieldName, String originalName) {
        // 直接匹配
        if (FIELD_NAME_EXAMPLES.containsKey(fieldName)) {
            return FIELD_NAME_EXAMPLES.get(fieldName);
        }
        
        // 部分匹配（字段名包含关键字）
        for (Map.Entry<String, String> entry : FIELD_NAME_EXAMPLES.entrySet()) {
            if (fieldName.contains(entry.getKey()) || originalName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * 从注释中提取示例值
     */
    private static String extractExampleFromComment(String comment, String fieldName) {
        if (comment == null || comment.isEmpty()) {
            return null;
        }
        
        // 如果注释中包含枚举值，提取第一个
        // 例如：状态：1-已保存，2-发布中，3-已发布
        if (comment.contains("：") || comment.contains(":")) {
            String[] parts = comment.split("[:：]");
            if (parts.length > 1) {
                String valuePart = parts[1].trim();
                // 提取枚举值后的描述
                if (valuePart.contains("-") || valuePart.contains("—")) {
                    String[] enumParts = valuePart.split("[-—]");
                    if (enumParts.length > 1) {
                        return enumParts[1].trim();
                    }
                }
            }
        }
        
        // 如果注释本身就是示例值（长度适中）
        if (comment.length() > 2 && comment.length() < 50) {
            return comment;
        }
        
        return null;
    }
    
    /**
     * 根据表名和字段名组合生成示例值
     */
    private static String generateByTableAndField(String tableName, String fieldName, String originalName) {
        if (tableName == null || tableName.isEmpty()) {
            return null;
        }
        
        String tablePrefix = TABLE_NAME_PREFIXES.get(tableName.toLowerCase());
        if (tablePrefix == null) {
            // 尝试从表名中提取关键词
            tablePrefix = extractPrefixFromTableName(tableName);
        }
        
        if (tablePrefix != null) {
            // 根据字段类型生成
            if (fieldName.contains("title") || fieldName.contains("name")) {
                return tablePrefix + "示例标题";
            } else if (fieldName.contains("description") || fieldName.contains("content")) {
                return "这是" + tablePrefix + "的示例描述信息";
            } else if (fieldName.contains("code") || fieldName.contains("no")) {
                return tablePrefix.toUpperCase() + "001";
            }
        }
        
        return null;
    }
    
    /**
     * 从表名中提取前缀
     */
    private static String extractPrefixFromTableName(String tableName) {
        // 移除常见后缀
        String name = tableName.toLowerCase();
        name = name.replace("_", "");
        name = name.replace("table", "");
        name = name.replace("tb", "");
        name = name.replace("t", "");
        
        // 提取关键词
        if (name.contains("content")) return "内容";
        if (name.contains("article")) return "文章";
        if (name.contains("book")) return "图书";
        if (name.contains("user")) return "用户";
        if (name.contains("product")) return "产品";
        if (name.contains("order")) return "订单";
        
        return null;
    }
    
    /**
     * 根据字段名模式推断示例值
     */
    private static String inferFromFieldNamePattern(String fieldName, String originalName) {
        String name = fieldName.toLowerCase();
        
        // 标题、名称类
        if (name.contains("title") || name.contains("name")) {
            return "示例标题";
        }
        
        // 描述、内容类
        if (name.contains("description") || name.contains("content") || name.contains("detail")) {
            return "这是示例描述信息";
        }
        
        // 副标题
        if (name.contains("subtitle")) {
            return "示例副标题";
        }
        
        // 作者
        if (name.contains("author")) {
            return "张三";
        }
        
        // 关键词
        if (name.contains("keyword")) {
            return "示例关键词";
        }
        
        // 标签
        if (name.contains("tag")) {
            return "示例标签";
        }
        
        // 分类
        if (name.contains("category") || name.contains("type")) {
            return "示例分类";
        }
        
        // 元数据
        if (name.contains("metadata") || name.contains("meta")) {
            return "{\"key\":\"value\"}";
        }
        
        return null;
    }
    
    /**
     * 限制字符串长度
     */
    private static String limitLength(String value, Integer maxLength) {
        if (value == null) {
            return null;
        }
        
        if (maxLength != null && maxLength > 0 && value.length() > maxLength) {
            return value.substring(0, Math.min(maxLength - 3, value.length())) + "...";
        }
        
        return value;
    }
}

