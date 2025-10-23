package com.old.silence.code.generator.rule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
public class RuleConfig {

    private List<RuleConfigItem> rules = new ArrayList<>();

    // 默认构造函数
    public RuleConfig() {
    }

    // 从JSON文件加载配置
    public static RuleConfig loadFromFile(String filePath) {
        try {

            // 从classpath加载文件
            InputStream inputStream = RuleConfig.class.getClassLoader()
                    .getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new RuntimeException("规则配置文件未找到: " + filePath);
            }

            RuleConfig config = JacksonMapper.getSharedInstance().fromJson(inputStream, RuleConfig.class);
            inputStream.close();

            System.out.println("成功加载规则配置，共 " + config.getRules().size() + " 条规则");
            return config;

        } catch (Exception e) {
            throw new RuntimeException("加载规则配置文件失败: " + filePath, e);
        }
    }

    // 根据类型过滤规则
    public List<RuleConfigItem> getRulesByType(String type) {
        List<RuleConfigItem> filtered = new ArrayList<>();
        for (RuleConfigItem rule : rules) {
            if (rule.getType().equals(type) && rule.getEnabled()) {
                filtered.add(rule);
            }
        }
        return filtered;
    }

    // 根据目标实体过滤规则
    public List<RuleConfigItem> getRulesByTargetEntity(String targetEntity) {
        List<RuleConfigItem> filtered = new ArrayList<>();
        for (RuleConfigItem rule : rules) {
            if (targetEntity.equals(rule.getTargetEntity()) && rule.getEnabled()) {
                filtered.add(rule);
            }
        }
        return filtered;
    }

    // getters and setters
    public List<RuleConfigItem> getRules() {
        return rules;
    }

    public void setRules(List<RuleConfigItem> rules) {
        this.rules = rules;
    }
}
