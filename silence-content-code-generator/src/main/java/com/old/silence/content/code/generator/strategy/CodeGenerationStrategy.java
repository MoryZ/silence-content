package com.old.silence.content.code.generator.strategy;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateStrategyType;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

/**
 * 代码生成策略接口
 * 支持多种代码生成方式：模板生成、大模型生成、混合生成等
 *
 * @author moryzang
 */
public interface CodeGenerationStrategy {

    /**
     * 生成代码
     *
     * @param tableInfo 表信息
     * @param apiDoc    接口文档
     * @param config    配置
     * @param layer     生成层级
     */
    void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                      GeneratorConfig config, CodeLayer layer);

    /**
     * 是否支持该层级
     *
     * @param layer 代码层级
     * @return 是否支持
     */
    boolean supports(CodeLayer layer);

    /**
     * 策略名称
     *
     * @return 策略名称
     */
    CodeGenerateStrategyType getStrategyType();

    /**
     * 代码生成层级枚举
     */
    enum CodeLayer {
        /**
         * Console层
         */
        CONSOLE,
        /**
         * Service层
         */
        SERVICE,
        /**
         * Service-API层
         */
        SERVICE_API,
        /**
         * Enum枚举
         */
        ENUM
    }
}

