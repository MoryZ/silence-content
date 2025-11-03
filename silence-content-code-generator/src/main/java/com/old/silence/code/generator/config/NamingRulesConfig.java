package com.old.silence.code.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
@Component
@ConfigurationProperties(prefix = "naming.rules")
public class NamingRulesConfig {
    private List<FieldMappingRule> fieldMapping = new ArrayList<>();
    private List<ValidationRule> validation = new ArrayList<>();

    public static class FieldMappingRule {
        private String pattern;
        private String replacement;
        private String transform;
        private String type;
        private Boolean addIsPrefix = false;
        private Integer priority = 0;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getReplacement() {
            return replacement;
        }

        public void setReplacement(String replacement) {
            this.replacement = replacement;
        }

        public String getTransform() {
            return transform;
        }

        public void setTransform(String transform) {
            this.transform = transform;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getAddIsPrefix() {
            return addIsPrefix;
        }

        public void setAddIsPrefix(Boolean addIsPrefix) {
            this.addIsPrefix = addIsPrefix;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }
    }

    public static class ValidationRule {
        private String fieldPattern;
        private Boolean required = false;
        private String type;
        private String format;
        private String defaultValue;

        public String getFieldPattern() {
            return fieldPattern;
        }

        public void setFieldPattern(String fieldPattern) {
            this.fieldPattern = fieldPattern;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public List<FieldMappingRule> getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(List<FieldMappingRule> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public List<ValidationRule> getValidation() {
        return validation;
    }

    public void setValidation(List<ValidationRule> validation) {
        this.validation = validation;
    }
}