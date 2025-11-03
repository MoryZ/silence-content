package com.old.silence.code.generator.rule;

import java.io.InputStream;
import java.util.List;

import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */

public class JsonRuleLoader {

    public List<ConfigurableRule> loadRules(String configPath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
        if (inputStream == null) {
            throw new RuntimeException("找不到配置文件: " + configPath);
        }
        RuleConfig ruleConfig = JacksonMapper.getSharedInstance().fromJson(inputStream, RuleConfig.class);
        return ruleConfig.rules();
    }
}