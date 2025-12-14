package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

/**
 * 模板代码生成策略
 * 使用FreeMarker模板生成代码（保留现有能力）
 *
 * @author moryzang
 */
@Service
public class TemplateCodeGenerationStrategy implements CodeGenerationStrategy {

    private static final Logger log = LoggerFactory.getLogger(TemplateCodeGenerationStrategy.class);

    private final RefactoredCodeGeneratorService refactoredCodeGeneratorService;
    private final CodeGenerator codeGenerator;

    public TemplateCodeGenerationStrategy(RefactoredCodeGeneratorService refactoredCodeGeneratorService,
                                          CodeGenerator codeGenerator) {
        this.refactoredCodeGeneratorService = refactoredCodeGeneratorService;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                             CodeGenModuleConfig config) {
        log.info("使用模板策略生成 {} 层代码", config.getModuleType());

        CodeGenerator codeGenerator = this.codeGenerator;

        try {
            // 使用统一的生成接口，元数据驱动生成
            refactoredCodeGeneratorService.generateCode(codeGenerator, tableInfo, apiDoc, config);
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

