package com.old.silence.code.generator.rule;

import java.util.Map;


/**
 * @author moryzang
 */
import java.util.Map;

import com.old.silence.code.generator.model.ColumnInfo;

public class ExpressionAction implements Action {
    private final ExpressionEvaluator evaluator;

    public ExpressionAction() {
        this.evaluator = new ExpressionEvaluator();
    }

    public ExpressionAction(ExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public String getType() {
        return "expression";
    }

    @Override
    public Object execute(Object target, Map<String, Object> config, Map<String, Object> context) {
        String expression = (String) config.get("expression");

        if (expression == null || expression.isBlank()) {
            System.err.println("ExpressionAction: 表达式为空");
            return target;
        }

        // 设置当前结果到上下文
        context.put("result", target);
        context.put("target", target);

        try {
            Object result = evaluator.evaluate(expression, context);

            // 如果目标是 FieldInfo，可以更新字段名
            if (target instanceof ColumnInfo field && result instanceof String stringResult) {
                field.setName(stringResult);
            }

            return result;
        } catch (Exception e) {
            System.err.println("ExpressionAction 执行失败: " + expression + ", 错误: " + e.getMessage());
            return target;
        }
    }
}