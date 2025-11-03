package com.old.silence.code.generator.rule;

import javax.script.*;
import java.util.Map;

/**
 * @author moryzang
 */

public class ExpressionEvaluator {

    public Object evaluate(String expression, Map<String, Object> context) {
        if (expression == null || expression.trim().isEmpty()) {
            return true;
        }

        // 使用简单的表达式解析器
        SimpleExpressionEvaluator simpleEvaluator = new SimpleExpressionEvaluator();
        return simpleEvaluator.evaluate(expression, context);
    }

    public boolean evaluateBoolean(String expression, Map<String, Object> context) {
        Object result = evaluate(expression, context);
        return Boolean.TRUE.equals(result);
    }
}