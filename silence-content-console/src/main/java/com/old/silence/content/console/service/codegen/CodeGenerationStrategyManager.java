package com.old.silence.content.console.service.codegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;

/**
 * 代码生成策略管理器
 * 根据配置选择合适的生成策略
 *
 * @author moryzang
 */
@Component
public class CodeGenerationStrategyManager {

    private final Map<CodeGenerateToolType, CodeGenerationStrategy> strategies = new HashMap<>();

    public CodeGenerationStrategyManager(ObjectProvider<CodeGenerationStrategy> objectProvider) {
        objectProvider.stream()
                .forEach(strategy -> strategies.put(strategy.getStrategyType(), strategy));
    }

    /**
     * 获取策略
     *
     * @param strategyType 策略名称 (TEMPLATE, LLM, HYBRID)
     * @return 代码生成策略
     */
    public CodeGenerationStrategy getStrategy(CodeGenerateToolType strategyType) {
        CodeGenerationStrategy strategy = strategies.get(strategyType);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的生成策略: " + strategyType);
        }
        return strategy;
    }


}

