package com.old.silence.code.generator.rule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.Rule;

/**
 * @author moryzang
 */

import java.util.*;

public class ExpressionRuleEngine implements RuleEngine {
    private final List<ConfigurableRule> rules = new ArrayList<>();

    @Override
    public <T> T executeRules(T target, Map<String, Object> context) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put("target", target);

        // 确保上下文中有必要的变量
        ensureContextVariables(context, target);

        List<ConfigurableRule> sortedRules = rules.stream()
                .filter(Rule::isEnabled)
                .sorted(Comparator.comparingInt(ConfigurableRule::getPriority))
                .toList();

        for (Rule rule : sortedRules) {
            try {
                // 打印调试信息
                System.out.println("执行规则: " + rule.getName() + ", 条件: " + getRuleCondition(rule));
                System.out.println("上下文内容: " + context);

                boolean conditionResult = rule.evaluateCondition(context);
                System.out.println("条件结果: " + conditionResult);

                if (conditionResult) {
                    rule.executeAction(context);
                    System.out.println("✅ 规则执行成功: " + rule.getName());
                } else {
                    System.out.println("⏭️  规则跳过: " + rule.getName());
                }
            } catch (Exception e) {
                System.err.println("❌ 执行规则 '" + rule.getName() + "' 时出错: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("---");
        }

        return target;
    }

    /**
     * 确保上下文中有必要的变量
     */
    private void ensureContextVariables(Map<String, Object> context, Object target) {
        // 确保 target 存在
        context.putIfAbsent("target", target);

        // 如果目标是 FieldInfo，添加便捷访问
        if (target instanceof ColumnInfo field) {
            context.putIfAbsent("field", field);
            context.putIfAbsent("fieldName", field.getName());
            context.putIfAbsent("fieldType", field.getType());
        }
    }

    /**
     * 获取规则条件（用于调试）
     */
    private String getRuleCondition(Rule rule) {
        if (rule instanceof ConfigurableRule) {
            return ((ConfigurableRule) rule).getCondition();
        }
        return "未知条件";
    }

    // 修正方法签名：使用 Rule 接口而不是具体类
    @Override
    public void addRule(ConfigurableRule rule) {
        rules.add(rule);
        sortRules();
    }

    @Override
    public void addRules(List<ConfigurableRule> rules) {
        this.rules.addAll(rules);
        sortRules();
    }

    // 添加专门处理 ConfigurableRule 的方法
    public void addConfigurableRule(ConfigurableRule rule) {
        rules.add(rule);
        sortRules();
    }

    public void addConfigurableRules(List<ConfigurableRule> configurableRules) {
        // 需要转换类型
        List<ConfigurableRule> ruleList = new ArrayList<>(configurableRules);
        this.rules.addAll(ruleList);
        sortRules();
    }

    private void sortRules() {
        rules.sort(Comparator.comparingInt(Rule::getPriority));
    }

    @Override
    public void clearRules() {
        rules.clear();
    }

    // 调试方法
    public void printRules() {
        System.out.println("=== 当前规则列表 ===");
        for (Rule rule : rules) {
            System.out.println("规则: " + rule.getName() +
                    ", 优先级: " + rule.getPriority() +
                    ", 启用: " + rule.isEnabled());
        }
        System.out.println("==================");
    }
}
