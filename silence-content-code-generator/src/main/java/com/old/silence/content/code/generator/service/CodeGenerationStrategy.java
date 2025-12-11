package com.old.silence.content.code.generator.service;

import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
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
     */
    void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                      CodeGenModuleConfig config);


    /**
     * 策略名称
     *
     * @return 策略名称
     */
    CodeGenerateToolType getStrategyType();


}

