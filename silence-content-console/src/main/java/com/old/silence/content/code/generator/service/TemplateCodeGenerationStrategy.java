package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

@Service
public class TemplateCodeGenerationStrategy implements CodeGenerationStrategy {

    private static final Logger log = LoggerFactory.getLogger(TemplateCodeGenerationStrategy.class);

    private final SpringCodeGeneratorService springCodeGeneratorService;
    private final CodeGenerator codeGenerator;

    public TemplateCodeGenerationStrategy(SpringCodeGeneratorService springCodeGeneratorService,
                                          CodeGenerator codeGenerator) {
        this.springCodeGeneratorService = springCodeGeneratorService;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                             CodeGenModuleConfig config) {
        log.info("使用模板策略生成 {} 层代码", config.getModuleType());

        CodeGenerator codeGenerator = this.codeGenerator;

        try {
            switch (config.getModuleType()) {
                case CONSOLE:
                    springCodeGeneratorService.generateConsoleCode(codeGenerator, tableInfo, apiDoc, config);
                    break;
                case SERVICE:
                    springCodeGeneratorService.generateServiceCode(codeGenerator, tableInfo, apiDoc, config);
                    break;
                case SERVICE_API:
                    springCodeGeneratorService.generateInterfaceCode(codeGenerator, tableInfo, apiDoc, config);
                    break;
                case ENUM:
                    springCodeGeneratorService.generateEnumCode(codeGenerator, tableInfo, config);
                    break;
                default:
                    log.warn("不支持的代码层级: {}", config.getModuleType());
            }
        } catch (Exception e) {
            log.error("模板代码生成失败: {}", e.getMessage(), e);
            throw new RuntimeException("模板代码生成失败", e);
        }
    }

    @Override
    public CodeGenerateToolType getStrategyType() {
        return CodeGenerateToolType.TEMPLATE;
    }
}
