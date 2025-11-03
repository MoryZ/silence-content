package com.old.silence.code.generator.rule;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.old.silence.code.generator.model.ColumnInfo;

/**
 * @author moryzang
 */
public class RegexReplaceAction implements Action {
    @Override
    public String getType() {
        return "regex_replace";
    }

    @Override
    public Object execute(Object target, Map<String, Object> config, Map<String, Object> context) {
        // 1. 参数验证
        if (target == null) {
            System.err.println("RegexReplaceAction: 目标对象为null");
            return null;
        }

        if (!(target instanceof ColumnInfo)) {
            System.err.println("RegexReplaceAction: 目标对象不是ColumnInfo类型，实际类型: " + target.getClass().getSimpleName());
            return target; // 返回原对象，不处理
        }

        // 2. 配置验证
        if (config == null || config.isEmpty()) {
            System.err.println("RegexReplaceAction: 配置为空");
            return target;
        }

        String pattern = getStringConfig(config, "pattern");
        String replacement = getStringConfig(config, "replacement");

        if (pattern == null || pattern.isEmpty()) {
            System.err.println("RegexReplaceAction: 正则表达式模式为空");
            return target;
        }

        // 3. 获取配置参数（带默认值）
        boolean global = getBooleanConfig(config, "global", true);
        boolean caseInsensitive = getBooleanConfig(config, "caseInsensitive", false);
        boolean multiline = getBooleanConfig(config, "multiline", false);

        ColumnInfo field = (ColumnInfo) target;
        String originalName = field.getName();

        try {
            // 4. 编译正则表达式（支持标志）
            int flags = 0;
            if (caseInsensitive) {
                flags |= Pattern.CASE_INSENSITIVE;
            }
            if (multiline) {
                flags |= Pattern.MULTILINE;
            }

            Pattern compiledPattern = Pattern.compile(pattern, flags);
            String result = originalName;

            // 5. 执行替换
            if (global) {
                result = compiledPattern.matcher(result).replaceAll(replacement != null ? replacement : "");
            } else {
                result = compiledPattern.matcher(result).replaceFirst(replacement != null ? replacement : "");
            }

            // 6. 只有在实际发生变化时才更新
            if (!result.equals(originalName)) {
                field.setName(result);
                System.out.println("RegexReplaceAction: '" + originalName + "' -> '" + result + "'");
            } else {
                System.out.println("RegexReplaceAction: 字段名未发生变化: " + originalName);
            }

            return field;

        } catch (PatternSyntaxException e) {
            System.err.println("RegexReplaceAction: 正则表达式语法错误 - " + pattern + ", 错误: " + e.getMessage());
            return target;
        } catch (Exception e) {
            System.err.println("RegexReplaceAction: 执行替换时发生错误: " + e.getMessage());
            return target;
        }
    }

    // 辅助方法：安全获取字符串配置
    private String getStringConfig(Map<String, Object> config, String key) {
        Object value = config.get(key);
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        }
        return null;
    }

    // 辅助方法：安全获取布尔配置（带默认值）
    private boolean getBooleanConfig(Map<String, Object> config, String key, boolean defaultValue) {
        Object value = config.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        } else if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }
        return defaultValue;
    }

    // 辅助方法：安全获取整数配置
    private int getIntConfig(Map<String, Object> config, String key, int defaultValue) {
        Object value = config.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}

