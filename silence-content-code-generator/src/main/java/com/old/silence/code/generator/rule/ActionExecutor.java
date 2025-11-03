package com.old.silence.code.generator.rule;

import java.util.HashMap;
import java.util.Map;


/**
 * @author moryzang
 */
public class ActionExecutor {
    private final Map<String, Action> actionRegistry = new HashMap<>();
    private final ExpressionEvaluator expressionEvaluator;

    public ActionExecutor() {
        this.expressionEvaluator = new ExpressionEvaluator();
        registerDefaultActions();
    }

    public ActionExecutor(ExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        registerAction("regex_replace", new RegexReplaceAction());
        registerAction("expression", new ExpressionAction());
    }

    public void registerAction(String actionType, Action action) {
        actionRegistry.put(actionType, action);
    }

    public void executeAction(ActionConfig actionConfig, Map<String, Object> context) {
        String actionType = actionConfig.type();
        Action action = actionRegistry.get(actionType);

        if (action == null) {
            throw new RuntimeException("未知的动作类型: " + actionType);
        }

        Object target = context.get("target");
        Map<String, Object> processedConfig = preprocessConfig(actionConfig.config(), context);

        action.execute(target, processedConfig, context);
    }

    private Map<String, Object> preprocessConfig(Map<String, Object> config, Map<String, Object> context) {
        if (config == null) {
            return Map.of(); // 使用不可变空Map
        }

        return config.entrySet().stream()
                .collect(HashMap::new,
                        (map, entry) -> map.put(entry.getKey(), processValue(entry.getValue(), context)),
                        HashMap::putAll);
    }

    private Object processValue(Object value, Map<String, Object> context) {
        // 使用 switch 表达式和模式匹配
        return switch (value) {
            case String str when str.startsWith("${") && str.endsWith("}") -> {
                String expression = str.substring(2, str.length() - 1);
                yield evaluateExpressionSafely(expression, context);
            }
            case null -> null;
            default -> value;
        };
    }

    private Object evaluateExpressionSafely(String expression, Map<String, Object> context) {
        try {
            return expressionEvaluator.evaluate(expression, context);
        } catch (Exception e) {
            System.err.println("表达式求值失败: " + expression + ", 错误: " + e.getMessage());
            return "EXPRESSION_ERROR: " + expression; // 使用标准字符串连接
        }
    }

    // 使用文本块定义帮助方法
    public String getSupportedActions() {
        return """
            支持的动作为:
            - regex_replace: 正则表达式替换
            - expression: 表达式求值
            - set_property: 设置属性
            """;
    }
}