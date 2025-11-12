package com.old.silence.code.generator.strategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成策略管理器
 * 根据配置选择合适的生成策略
 *
 * @author moryzang
 */
@Component
public class CodeGenerationStrategyManager {

    private final Map<String, CodeGenerationStrategy> strategies = new HashMap<>();

    public CodeGenerationStrategyManager(List<CodeGenerationStrategy> strategyList) {
        // 注册所有策略
        for (CodeGenerationStrategy strategy : strategyList) {
            strategies.put(strategy.getStrategyName(), strategy);
        }
    }

    /**
     * 获取策略
     *
     * @param strategyName 策略名称 (TEMPLATE, LLM, HYBRID)
     * @return 代码生成策略
     */
    public CodeGenerationStrategy getStrategy(String strategyName) {
        CodeGenerationStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的生成策略: " + strategyName);
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
    public CodeGenerationStrategy getStrategyByLayer(Map<CodeGenerationStrategy.CodeLayer, String> layerStrategyMap,
                                                     String defaultStrategy,
                                                     CodeGenerationStrategy.CodeLayer layer) {
        String strategyName = layerStrategyMap.getOrDefault(layer, defaultStrategy);
        return getStrategy(strategyName);
    }

    /**
     * 获取所有可用策略
     *
     * @return 策略名称列表
     */
    public List<String> getAvailableStrategies() {
        return strategies.keySet().stream().toList();
    }
}

