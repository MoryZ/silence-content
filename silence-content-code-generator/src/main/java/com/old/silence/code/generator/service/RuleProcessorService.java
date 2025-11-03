package com.old.silence.code.generator.service;

import org.springframework.stereotype.Service;
import com.old.silence.code.generator.config.NamingRulesConfig;
import com.old.silence.code.generator.model.ColumnInfo;

import java.util.Comparator;
import java.util.List;

/**
 * @author moryzang
 */
@Service
public class RuleProcessorService {

    private final NamingRulesConfig rulesConfig;

    public RuleProcessorService(NamingRulesConfig rulesConfig) {
        this.rulesConfig = rulesConfig;
    }

    // 数据库字段转Java字段
    public void convertToJavaField(ColumnInfo columnInfo) {
        var originalName = columnInfo.getOriginalName();

        // 按优先级排序规则
        List<NamingRulesConfig.FieldMappingRule> sortedRules = rulesConfig.getFieldMapping()
                .stream()
                .sorted(Comparator.comparing(NamingRulesConfig.FieldMappingRule::getPriority))
                .toList();

        for (NamingRulesConfig.FieldMappingRule rule : sortedRules) {
            if (originalName.matches(rule.getPattern())) {
                originalName = applyReplacement(originalName, rule);
                originalName = applyTransform(originalName, rule);

                columnInfo.setFieldName(originalName);
                columnInfo.setFieldType(rule.getType());
                columnInfo.setAddIsPrefix(rule.getAddIsPrefix());
                break;
            }
        }

        // 应用校验规则
        applyValidationRules(columnInfo, originalName);

    }

    private String applyReplacement(String input, NamingRulesConfig.FieldMappingRule rule) {
        if (rule.getReplacement() != null && !rule.getReplacement().isEmpty()) {
            return input.replaceAll(rule.getPattern(), rule.getReplacement());
        }
        return input;
    }

    private String applyTransform(String input, NamingRulesConfig.FieldMappingRule rule) {
        if ("underscoreToCamelCase".equals(rule.getTransform())) {
            return underscoreToCamelCase(input);
        }
        return input;
    }

    private String underscoreToCamelCase(String underscore) {
        String[] parts = underscore.split("_");
        StringBuilder camelCase = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase())
                    .append(parts[i].substring(1).toLowerCase());
        }
        return camelCase.toString();
    }

    private void applyValidationRules(ColumnInfo fieldInfo, String dbField) {
        for (NamingRulesConfig.ValidationRule rule : rulesConfig.getValidation()) {
            if (dbField.matches(rule.getFieldPattern())) {
                if (rule.getRequired() != null) {
                    fieldInfo.setRequired(rule.getRequired());
                }
                if (rule.getType() != null) {
                    fieldInfo.setFieldType(rule.getType());
                }
                if (rule.getDefaultValue() != null) {
                    fieldInfo.setDefaultValue(rule.getDefaultValue());
                }
            }
        }
    }
}