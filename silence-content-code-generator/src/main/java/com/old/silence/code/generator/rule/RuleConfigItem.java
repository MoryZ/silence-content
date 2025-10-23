package com.old.silence.code.generator.rule;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moryzang
 */
public class RuleConfigItem {

    private String name;
    private String type;
    private String condition;
    private String action;
    private String description;
    private Integer priority;

    @JsonProperty("target_entity")
    private String targetEntity;

    private Boolean enabled;

    // 默认构造函数
    public RuleConfigItem() {
        this.enabled = true;
    }

    // 带参数构造函数
    public RuleConfigItem(String name, String type, String condition, String action) {
        this();
        this.name = name;
        this.type = type;
        this.condition = condition;
        this.action = action;
    }

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "RuleConfigItem{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", condition='" + condition + '\'' +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", targetEntity='" + targetEntity + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
