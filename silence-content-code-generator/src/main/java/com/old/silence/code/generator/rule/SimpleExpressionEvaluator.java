package com.old.silence.code.generator.rule;


import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author moryzang
 */
public class SimpleExpressionEvaluator {

    public Object evaluate(String expression, Map<String, Object> context) {
        if (expression == null || expression.trim().isEmpty()) {
            return true;
        }

        // 去除空格
        expression = expression.trim();

        // 处理简单的布尔表达式
        return evaluateBooleanExpression(expression, context);
    }

    private Object evaluateBooleanExpression(String expression, Map<String, Object> context) {
        // 处理简单的比较和逻辑运算
        if (expression.equals("true")) return true;
        if (expression.equals("false")) return false;

        // 处理 contains 方法
        if (expression.contains(".contains(")) {
            return evaluateContains(expression, context);
        }

        // 处理 startsWith 方法
        if (expression.contains(".startsWith(")) {
            return evaluateStartsWith(expression, context);
        }

        // 处理 endsWith 方法
        if (expression.contains(".endsWith(")) {
            return evaluateEndsWith(expression, context);
        }

        // 处理 equals 方法
        if (expression.contains(".equals(")) {
            return evaluateEquals(expression, context);
        }

        // 处理简单的变量访问
        if (context.containsKey(expression)) {
            return context.get(expression);
        }

        // 处理属性访问 (target.fieldName)
        if (expression.contains(".")) {
            return evaluatePropertyAccess(expression, context);
        }

        // 默认返回 false
        return false;
    }

    private boolean evaluateContains(String expression, Map<String, Object> context) {
        try {
            // 解析 target.fieldName.contains('substring')
            Pattern pattern = Pattern.compile("(\\w+\\.\\w+)\\.contains\\(\"([^\"]+)\"\\)");
            var matcher = pattern.matcher(expression);
            if (matcher.find()) {
                String propertyPath = matcher.group(1);
                String substring = matcher.group(2);
                Object value = getPropertyValue(propertyPath, context);
                return value != null && value.toString().contains(substring);
            }
        } catch (Exception e) {
            System.err.println("解析 contains 表达式失败: " + expression);
        }
        return false;
    }

    private boolean evaluateStartsWith(String expression, Map<String, Object> context) {
        try {
            Pattern pattern = Pattern.compile("(\\w+\\.\\w+)\\.startsWith\\(\"([^\"]+)\"\\)");
            var matcher = pattern.matcher(expression);
            if (matcher.find()) {
                String propertyPath = matcher.group(1);
                String prefix = matcher.group(2);
                Object value = getPropertyValue(propertyPath, context);
                return value != null && value.toString().startsWith(prefix);
            }
        } catch (Exception e) {
            System.err.println("解析 startsWith 表达式失败: " + expression);
        }
        return false;
    }

    private boolean evaluateEndsWith(String expression, Map<String, Object> context) {
        try {
            Pattern pattern = Pattern.compile("(\\w+\\.\\w+)\\.endsWith\\(\"([^\"]+)\"\\)");
            var matcher = pattern.matcher(expression);
            if (matcher.find()) {
                String propertyPath = matcher.group(1);
                String suffix = matcher.group(2);
                Object value = getPropertyValue(propertyPath, context);
                return value != null && value.toString().endsWith(suffix);
            }
        } catch (Exception e) {
            System.err.println("解析 endsWith 表达式失败: " + expression);
        }
        return false;
    }

    private boolean evaluateEquals(String expression, Map<String, Object> context) {
        try {
            Pattern pattern = Pattern.compile("(\\w+\\.\\w+)\\.equals\\(\"([^\"]+)\"\\)");
            var matcher = pattern.matcher(expression);
            if (matcher.find()) {
                String propertyPath = matcher.group(1);
                String expected = matcher.group(2);
                Object value = getPropertyValue(propertyPath, context);
                return value != null && value.toString().equals(expected);
            }
        } catch (Exception e) {
            System.err.println("解析 equals 表达式失败: " + expression);
        }
        return false;
    }

    private Object evaluatePropertyAccess(String expression, Map<String, Object> context) {
        try {
            return getPropertyValue(expression, context);
        } catch (Exception e) {
            System.err.println("解析属性访问失败: " + expression);
        }
        return null;
    }

    private Object getPropertyValue(String propertyPath, Map<String, Object> context) {
        String[] parts = propertyPath.split("\\.");
        if (parts.length != 2) {
            return null;
        }

        String objectName = parts[0];
        String propertyName = parts[1];

        Object obj = context.get(objectName);
        if (obj == null) {
            return null;
        }

        // 简单的反射获取属性值
        try {
            if (obj instanceof Map) {
                return ((Map<?, ?>) obj).get(propertyName);
            }

            // 使用反射获取字段值
            var field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            // 如果反射失败，尝试调用 getter 方法
            try {
                String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                var method = obj.getClass().getMethod(getterName);
                return method.invoke(obj);
            } catch (Exception ex) {
                System.err.println("获取属性值失败: " + propertyPath);
                return null;
            }
        }
    }
}