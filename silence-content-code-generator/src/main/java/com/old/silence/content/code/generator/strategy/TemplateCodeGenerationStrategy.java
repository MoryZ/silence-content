package com.old.silence.content.code.generator.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.SpringCodeGeneratorService;

/**
 * 模板代码生成策略
 * 使用FreeMarker模板生成代码（保留现有能力）
 *
 * @author moryzang
 */
@Service
public class TemplateCodeGenerationStrategy implements CodeGenerationStrategy {

    private static final Logger log = LoggerFactory.getLogger(TemplateCodeGenerationStrategy.class);

    private final SpringCodeGeneratorService springCodeGeneratorService;

    public TemplateCodeGenerationStrategy(SpringCodeGeneratorService springCodeGeneratorService) {
        this.springCodeGeneratorService = springCodeGeneratorService;
    }

    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                             CodeGenModuleConfig config, CodeLayer layer) {
        log.info("使用模板策略生成 {} 层代码", layer);

        // 初始化代码生成器
        SpringCodeGenerator codeGenerator = new SpringCodeGenerator();

        try {
            switch (layer) {
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
                    log.warn("不支持的代码层级: {}", layer);
            }
        } catch (Exception e) {
            log.error("模板代码生成失败: {}", e.getMessage(), e);
            throw new RuntimeException("模板代码生成失败", e);
        }
    }

    @Override
    public boolean supports(CodeLayer layer) {
        // 模板策略支持所有层级（除了FRONTEND需要特殊处理）
        return layer != null;
    }

    @Override
    public CodeGenerateToolType getStrategyType() {
        return CodeGenerateToolType.TEMPLATE;
    }
}

