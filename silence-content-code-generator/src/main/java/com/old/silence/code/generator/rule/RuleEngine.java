package com.old.silence.code.generator.rule;

import java.util.List;
import java.util.Map;

import com.old.silence.code.generator.model.Rule;

/**
 * @author moryzang
 */
public interface RuleEngine {
    /**
     * 执行规则处理
     */
    <T> T executeRules(T target, Map<String, Object> context);

    /**
     * 添加规则
     */
    void addRule(ConfigurableRule rule);

    /**
     * 批量添加规则
     */
    void addRules(List<ConfigurableRule> rules);

    /**
     * 清空规则
     */
    void clearRules();
}
