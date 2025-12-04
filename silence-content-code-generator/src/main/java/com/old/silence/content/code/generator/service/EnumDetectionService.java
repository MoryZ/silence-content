package com.old.silence.content.code.generator.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.config.EnumConfig;
import com.old.silence.content.code.generator.dto.EnumDetectionResponse;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 枚举字段识别服务
 * 智能识别表中可能需要生成枚举的字段
 *
 * @author moryzang
 */
@Service
public class EnumDetectionService {

    /**
     * 匹配枚举注释的正则表达式
     * 支持格式：
     * 1:待处理 2:已处理
     * 1-待处理,2-已处理
     * 1.待处理;2.已处理
     * 0=未删除,1=已删除
     */
    private static final Pattern ENUM_PATTERN = Pattern.compile(
            "(\\d+)\\s*[:：\\-=.]+\\s*([^,;，；\\s]+)(?:[,;，；\\s]+|$)"
    );

    /**
     * 识别表中的枚举字段
     */
    public EnumDetectionResponse detectEnums(TableInfo tableInfo) {
        EnumDetectionResponse response = new EnumDetectionResponse();
        List<EnumDetectionResponse.EnumSuggestion> suggestions = new ArrayList<>();

        for (ColumnInfo column : tableInfo.getColumnInfos()) {
            EnumDetectionResponse.EnumSuggestion suggestion = analyzeColumn(tableInfo, column);
            if (suggestion != null) {
                suggestions.add(suggestion);
            }
        }

        response.setSuggestions(suggestions);
        return response;
    }

    /**
     * 分析单个字段是否适合生成枚举
     */
    private EnumDetectionResponse.EnumSuggestion analyzeColumn(TableInfo tableInfo, ColumnInfo column) {
        String dbType = column.getType().toLowerCase();
        String comment = column.getComment() != null ? column.getComment() : "";

        // 1. 判断是否是潜在的枚举字段
        if (!isPotentialEnumField(dbType, comment)) {
            return null;
        }

        EnumDetectionResponse.EnumSuggestion suggestion = new EnumDetectionResponse.EnumSuggestion();
        suggestion.setTableName(tableInfo.getTableName());
        suggestion.setColumnName(column.getOriginalName());
        suggestion.setDbType(column.getType());
        suggestion.setComment(comment);

        // 2. 生成枚举类名建议
        String enumClassName = generateEnumClassName(tableInfo.getTableName(), column.getOriginalName());
        suggestion.setSuggestedEnumClassName(enumClassName);

        // 3. 尝试从注释中解析枚举值
        List<EnumConfig.EnumValue> parsedValues = parseEnumValuesFromComment(comment);
        suggestion.setParsedValues(parsedValues);

        // 4. 判断是否强烈建议生成枚举
        boolean strongSuggestion = isTinyInt(dbType) && !parsedValues.isEmpty();
        suggestion.setStrongSuggestion(strongSuggestion);

        // 5. 生成建议原因
        String reason = generateReason(dbType, parsedValues, comment);
        suggestion.setReason(reason);

        return suggestion;
    }

    /**
     * 判断是否是潜在的枚举字段
     */
    private boolean isPotentialEnumField(String dbType, String comment) {
        // tinyint 类型
        if (isTinyInt(dbType)) {
            return true;
        }

        // 注释中包含枚举信息
        if (comment.matches(".*\\d+[:：\\-=.].*")) {
            return true;
        }

        // 字段名包含常见枚举关键词
        String lowerComment = comment.toLowerCase();
        return lowerComment.contains("状态") || lowerComment.contains("类型") 
                || lowerComment.contains("标识") || lowerComment.contains("标志")
                || lowerComment.contains("status") || lowerComment.contains("type")
                || lowerComment.contains("flag");
    }

    /**
     * 判断是否是 tinyint 类型
     */
    private boolean isTinyInt(String dbType) {
        return dbType.startsWith("tinyint");
    }

    /**
     * 生成枚举类名
     * 规则：表名(驼峰) + 字段名(驼峰) + Enum
     */
    private String generateEnumClassName(String tableName, String columnName) {
        // 去除表名前缀（如 t_, tb_, sys_）
        String cleanTableName = tableName.replaceFirst("^(t_|tb_|sys_|tbl_)", "");
        
        // 转驼峰
        String tableNameCamel = toCamelCase(cleanTableName, true);
        String columnNameCamel = toCamelCase(columnName, true);

        // 如果字段名已经包含了表的业务含义，可以简化
        // 例如：user 表的 user_status -> UserStatusEnum 而不是 UserUserStatusEnum
        if (columnNameCamel.toLowerCase().startsWith(tableNameCamel.toLowerCase())) {
            return columnNameCamel + "Enum";
        }

        return tableNameCamel + columnNameCamel + "Enum";
    }

    /**
     * 从注释中解析枚举值
     */
    private List<EnumConfig.EnumValue> parseEnumValuesFromComment(String comment) {
        List<EnumConfig.EnumValue> values = new ArrayList<>();
        if (comment == null || comment.trim().isEmpty()) {
            return values;
        }

        Matcher matcher = ENUM_PATTERN.matcher(comment);
        while (matcher.find()) {
            String code = matcher.group(1);
            String description = matcher.group(2);
            
            // 生成枚举常量名
            String name = generateEnumConstantName(description);
            
            values.add(new EnumConfig.EnumValue(code, name, description));
        }

        return values;
    }

    /**
     * 生成枚举常量名
     * 例如：待处理 -> PENDING, 已处理 -> PROCESSED
     */
    private String generateEnumConstantName(String description) {
        // 移除特殊字符
        String cleaned = description.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
        
        // 如果是中文，使用拼音或简短英文（这里简化处理，实际可接入拼音库）
        if (cleaned.matches(".*[\\u4e00-\\u9fa5].*")) {
            // 简单的中文到英文映射
            return mapChineseToEnglish(cleaned);
        }
        
        // 英文转大写下划线
        return toUpperUnderscore(cleaned);
    }

    /**
     * 简单的中文到英文映射
     */
    private String mapChineseToEnglish(String chinese) {
        // 这里可以扩展更多映射
        String result = chinese;
        result = result.replace("待处理", "PENDING")
                .replace("处理中", "PROCESSING")
                .replace("已处理", "PROCESSED")
                .replace("已完成", "COMPLETED")
                .replace("已取消", "CANCELLED")
                .replace("已删除", "DELETED")
                .replace("未删除", "NOT_DELETED")
                .replace("启用", "ENABLED")
                .replace("禁用", "DISABLED")
                .replace("正常", "NORMAL")
                .replace("异常", "ABNORMAL")
                .replace("成功", "SUCCESS")
                .replace("失败", "FAILED")
                .replace("男", "MALE")
                .replace("女", "FEMALE");
        
        // 如果没有匹配到，返回拼音首字母大写
        if (result.equals(chinese)) {
            return "ENUM_" + chinese.hashCode();
        }
        
        return result;
    }

    /**
     * 生成建议原因
     */
    private String generateReason(String dbType, List<EnumConfig.EnumValue> parsedValues, String comment) {
        StringBuilder reason = new StringBuilder();
        
        if (isTinyInt(dbType)) {
            reason.append("字段类型为 tinyint，通常用于存储枚举值；");
        }
        
        if (!parsedValues.isEmpty()) {
            reason.append("注释中包含 ").append(parsedValues.size()).append(" 个枚举值定义；");
        } else if (comment.contains("状态") || comment.contains("类型") || 
                   comment.contains("status") || comment.contains("type")) {
            reason.append("字段注释包含状态/类型等枚举相关关键词；");
        }
        
        if (reason.length() == 0) {
            reason.append("建议确认是否需要生成枚举");
        }
        
        return reason.toString();
    }

    /**
     * 下划线转驼峰
     */
    private String toCamelCase(String str, boolean capitalizeFirst) {
        String[] parts = str.split("_");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].toLowerCase();
            if (i == 0 && !capitalizeFirst) {
                result.append(part);
            } else {
                result.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    result.append(part.substring(1));
                }
            }
        }
        
        return result.toString();
    }

    /**
     * 转大写下划线
     */
    private String toUpperUnderscore(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();
    }
}
