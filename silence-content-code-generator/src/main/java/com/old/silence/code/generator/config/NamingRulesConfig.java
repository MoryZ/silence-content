package com.old.silence.code.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Component
@PropertySource(
        value = "classpath:naming-rules.yaml",
        factory = YamlPropertySourceFactory.class
)
@ConfigurationProperties(prefix = "naming.rules")
public class NamingRulesConfig {
    private List<FieldMappingRule> fieldMapping = new ArrayList<>();
    private List<ValidationRule> validation = new ArrayList<>();

    // 使用 record 简化数据类（Java 14+）
    public record FieldMappingRule(
            String pattern,
            String replacement,
            String transform,
            String type,
            Boolean addIsPrefix,
            Integer priority
    ) {
        public FieldMappingRule {
            addIsPrefix = addIsPrefix != null ? addIsPrefix : false;
            priority = priority != null ? priority : 0;
        }
    }

    public record ValidationRule(
            String fieldPattern,
            Boolean required,
            String type,
            String format,
            String defaultValue
    ) {
        public ValidationRule {
            required = required != null ? required : false;
        }
    }

    // 业务方法
    public Optional<FieldMappingRule> findMappingRule(String fieldName, String fieldType) {
        return fieldMapping.stream()
                .filter(rule -> matchesRule(rule, fieldName, fieldType))
                .max(Comparator.comparingInt(FieldMappingRule::priority));
    }

    public List<ValidationRule> getValidationRules(String fieldType) {
        return validation.stream()
                .filter(rule -> rule.type() == null || rule.type().equals(fieldType))
                .collect(Collectors.toList());
    }

    private boolean matchesRule(FieldMappingRule rule, String fieldName, String fieldType) {
        // 类型匹配
        if (rule.type() != null && !rule.type().equals(fieldType)) {
            return false;
        }
        // 模式匹配
        return rule.pattern() == null || fieldName.matches(rule.pattern());
    }

    // 简化 getter/setter
    public List<FieldMappingRule> getFieldMapping() { return fieldMapping; }
    public void setFieldMapping(List<FieldMappingRule> fieldMapping) {
        this.fieldMapping = fieldMapping != null ? fieldMapping : new ArrayList<>();
    }

    public List<ValidationRule> getValidation() { return validation; }
    public void setValidation(List<ValidationRule> validation) {
        this.validation = validation != null ? validation : new ArrayList<>();
    }
}