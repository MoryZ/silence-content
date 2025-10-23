package com.old.silence.code.generator.util;

/**
 * @author moryzang
 */
public final class NameConverterUtils {

    private NameConverterUtils() {
        throw new AssertionError();
    }

    /**
     * 将表名转换为类名（大驼峰）
     */
    public static String toClassName(String tableName) {
        return toCamelCase(tableName, true);
    }

    /**
     * 将表名转换为变量名（小驼峰）
     */
    public static String toVariableName(String tableName) {
        return toCamelCase(tableName, false);
    }

    /**
     * 将表名转换为复数形式的变量名
     */
    public static String toPluralVariableName(String tableName) {
        String baseName = removeCommonSuffixes(tableName);
        String plural = toSimplePlural(baseName);
        return toCamelCase(plural, false);
    }

    /**
     * 移除常见的表名后缀
     */
    private static String removeCommonSuffixes(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            return tableName;
        }

        String lowerName = tableName.toLowerCase();

        // 移除常见的表名前缀和后缀
        if (lowerName.startsWith("t_") || lowerName.startsWith("tb_") ||
                lowerName.startsWith("table_")) {
            return tableName.substring(2);
        }

        if (lowerName.startsWith("sys_")) {
            return tableName.substring(4);
        }

        return tableName;
    }

    /**
     * 简单的复数转换
     */
    private static String toSimplePlural(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        String lowerWord = word.toLowerCase();

        // 如果单词已经以s结尾，直接返回
        if (lowerWord.endsWith("s") || lowerWord.endsWith("x") ||
                lowerWord.endsWith("ch") || lowerWord.endsWith("sh")) {
            return word;
        }

        // 如果以y结尾，且前一个字母不是元音，变y为ies
        if (lowerWord.endsWith("y")) {
            char beforeY = lowerWord.charAt(lowerWord.length() - 2);
            if (!isVowel(beforeY)) {
                return word.substring(0, word.length() - 1) + "ies";
            }
        }

        // 默认加s
        return word + "s";
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

    /**
     * 判断字符是否是元音
     */
    private static boolean isVowel(char c) {
        return "aeiou".indexOf(Character.toLowerCase(c)) >= 0;
    }
}
