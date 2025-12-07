package com.old.silence.content.code.generator.strategy;

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
        // 注册所有策略
        objectProvider.stream().forEach(strategy -> strategies.put(strategy.getStrategyType(), strategy));
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

    /**
     * 根据层级获取策略
     * 支持为不同层级配置不同策略
     *
     * @param layerStrategyMap 层级到策略的映射
     * @param defaultStrategy  默认策略
     * @param layer            代码层级
     * @return 代码生成策略
     */
    public CodeGenerationStrategy getStrategyByLayer(Map<CodeGenerationStrategy.CodeLayer, CodeGenerateToolType> layerStrategyMap,
                                                     CodeGenerateToolType defaultStrategy,
                                                     CodeGenerationStrategy.CodeLayer layer) {
        CodeGenerateToolType strategyName = layerStrategyMap.getOrDefault(layer, defaultStrategy);
        return getStrategy(strategyName);
    }

    /**
     * 获取所有可用策略
     *
     * @return 策略名称列表
     */
    public List<CodeGenerateToolType> getAvailableStrategies() {
        return strategies.keySet().stream().toList();
    }
}

