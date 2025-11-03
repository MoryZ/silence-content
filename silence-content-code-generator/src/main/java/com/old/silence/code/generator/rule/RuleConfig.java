package com.old.silence.code.generator.rule;

import java.util.List;
import java.util.Map;



/**
 * @author moryzang
 */

public record RuleConfig(
        List<ConfigurableRule> rules,
        Map<String, String> actionHandlers,
        Map<String, Object> globalConfig
) {
    // 简洁的 Record 定义
}