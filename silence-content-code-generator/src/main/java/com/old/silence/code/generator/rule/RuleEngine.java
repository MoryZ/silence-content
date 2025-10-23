package com.old.silence.code.generator.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.ApiEndpoint;
import com.old.silence.code.generator.model.TableInfo;

/**
 * @author moryzang
 */
public class RuleEngine {
    private final List<Rule> rules = new ArrayList<>();
    private final Map<String, List<Rule>> rulesByType = new HashMap<>();

    public void addRule(Rule rule) {
        rules.add(rule);

        // 按类型分类存储
        String ruleType = rule.getType();
        rulesByType.computeIfAbsent(ruleType, k -> new ArrayList<>()).add(rule);

        // 按优先级排序
        rules.sort(Comparator.comparingInt(Rule::getPriority));
        rulesByType.values().forEach(list ->
                list.sort(Comparator.comparingInt(Rule::getPriority)));
    }

    public void loadRulesFromConfig(RuleConfig ruleConfig) {
        for (RuleConfigItem configItem : ruleConfig.getRules()) {
            if (!configItem.getEnabled()) {
                continue; // 跳过禁用的规则
            }

            Rule rule = new Rule();
            rule.setName(configItem.getName());
            rule.setType(configItem.getType());
            rule.setCondition(createCondition(configItem.getCondition()));
            rule.setAction(createAction(configItem.getAction()));
            rule.setPriority(configItem.getPriority() != null ? configItem.getPriority() : 100);
            rule.setTargetEntity(configItem.getTargetEntity());

            addRule(rule);
        }
        System.out.println("从配置加载了 " + rules.size() + " 条规则");
    }

    public void applyRules(TableInfo tableInfo, ApiDocument apiDoc) {
        for (Rule rule : rules) {
            // 检查目标实体匹配
            if (rule.getTargetEntity() != null &&
                    !rule.getTargetEntity().equals(tableInfo.getTableName())) {
                continue;
            }

            if (rule.getCondition().test(tableInfo)) {
                System.out.println("应用规则: " + rule.getName() + " 到表: " + tableInfo.getTableName());
                rule.getAction().accept(apiDoc);
            }
        }
    }

    public void applyRulesByType(TableInfo tableInfo, ApiDocument apiDoc, String ruleType) {
        List<Rule> typeRules = rulesByType.getOrDefault(ruleType, Collections.emptyList());
        for (Rule rule : typeRules) {
            if (rule.getCondition().test(tableInfo)) {
                rule.getAction().accept(apiDoc);
            }
        }
    }

    private Predicate<TableInfo> createCondition(String condition) {
        // 解析条件表达式
        return tableInfo -> {
            if (condition == null || condition.trim().isEmpty()) {
                return true; // 无条件限制
            }

            // 简单的条件解析实现
            try {
                return evaluateCondition(tableInfo, condition);
            } catch (Exception e) {
                System.err.println("条件解析失败: " + condition + ", 错误: " + e.getMessage());
                return false;
            }
        };
    }

    private Consumer<ApiDocument> createAction(String action) {
        // 解析动作
        return apiDoc -> {
            if (action == null || action.trim().isEmpty()) {
                return;
            }

            try {
                executeAction(apiDoc, action);
            } catch (Exception e) {
                System.err.println("动作执行失败: " + action + ", 错误: " + e.getMessage());
            }
        };
    }

    private boolean evaluateCondition(TableInfo tableInfo, String condition) {
        // 简单的条件评估实现
        if (condition.contains("contains")) {
            String entity = condition.substring(condition.indexOf("'") + 1, condition.lastIndexOf("'"));
            return tableInfo.getTableName().contains(entity);
        }

        if (condition.contains("column_count >")) {
            int count = Integer.parseInt(condition.split(">")[1].trim());
            return tableInfo.getColumns().size() > count;
        }

        if (condition.contains("has_primary_key")) {
            return !tableInfo.getPrimaryKeys().isEmpty();
        }

        // 默认返回true
        return true;
    }

    private void executeAction(ApiDocument apiDoc, String action) {
        // 简单的动作执行实现
        if (action.contains("add_authentication")) {
            // 添加认证要求
            apiDoc.getEndpoints().values().forEach(endpoint -> {
                // 标记需要认证
                Map<String, Object> metadata = endpoint.getMetadata();
                if (metadata == null) {
                    metadata = new HashMap<>();
                    endpoint.setMetadata(metadata);
                }
                metadata.put("requiresAuth", true);
            });
        }

        if (action.contains("add_pagination")) {
            // 确保列表接口有分页参数
            ApiEndpoint listEndpoint = apiDoc.getEndpoints().get("list");
            if (listEndpoint != null) {
                // 添加分页参数
                // 具体实现...
            }
        }

        if (action.contains("add_validation")) {
            // 添加验证规则
            // 具体实现...
        }
    }

    public static class Rule {
        private String name;
        private String type;
        private Predicate<TableInfo> condition;
        private Consumer<ApiDocument> action;
        private int priority = 100;
        private String targetEntity;

        // getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Predicate<TableInfo> getCondition() {
            return condition;
        }

        public void setCondition(Predicate<TableInfo> condition) {
            this.condition = condition;
        }

        public Consumer<ApiDocument> getAction() {
            return action;
        }

        public void setAction(Consumer<ApiDocument> action) {
            this.action = action;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTargetEntity() {
            return targetEntity;
        }

        public void setTargetEntity(String targetEntity) {
            this.targetEntity = targetEntity;
        }
    }
}
