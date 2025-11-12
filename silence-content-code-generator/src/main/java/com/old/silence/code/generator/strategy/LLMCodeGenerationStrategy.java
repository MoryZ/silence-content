package com.old.silence.code.generator.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.llm.LLMService;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.util.CodeFormatter;
import com.old.silence.code.generator.util.PromptBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 大模型代码生成策略
 * 使用大模型生成代码（新增能力）
 *
 * @author moryzang
 */
@Service
public class LLMCodeGenerationStrategy implements CodeGenerationStrategy {

    private static final Logger log = LoggerFactory.getLogger(LLMCodeGenerationStrategy.class);

    private final LLMService llmService;
    private final PromptBuilder promptBuilder;
    private final CodeFormatter codeFormatter;

    public LLMCodeGenerationStrategy(LLMService llmService,
                                    PromptBuilder promptBuilder,
                                    CodeFormatter codeFormatter) {
        this.llmService = llmService;
        this.promptBuilder = promptBuilder;
        this.codeFormatter = codeFormatter;
    }

    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc,
                            GeneratorConfig config, CodeLayer layer) {
        log.info("使用大模型策略生成 {} 层代码", layer);

        try {
            // 1. 构建提示词
            String prompt = promptBuilder.buildPrompt(tableInfo, apiDoc, config, layer);
            log.debug("生成的提示词: {}", prompt);

            // 2. 构建生成上下文
            LLMService.CodeGenerationContext context = new LLMService.CodeGenerationContext();
            context.setTableInfo(tableInfo);
            context.setApiDocument(apiDoc);
            context.setLayer(layer.name());
            context.setBasePackage(config.getBasePackage());
            context.setClassName(getClassName(tableInfo));

            // 3. 调用大模型生成代码
            String generatedCode = llmService.generateCode(prompt, context);
            log.debug("大模型生成的代码长度: {} 字符", generatedCode.length());

            // 4. 代码后处理（格式化、验证等）
            String formattedCode = codeFormatter.format(generatedCode, layer);
            log.debug("格式化后的代码长度: {} 字符", formattedCode.length());

            // 5. 保存代码文件
            saveCodeFile(formattedCode, tableInfo, config, layer);

            log.info("大模型代码生成完成: {}", layer);

        } catch (Exception e) {
            log.error("大模型代码生成失败: {}", e.getMessage(), e);
            throw new RuntimeException("大模型代码生成失败", e);
        }
    }

    @Override
    public boolean supports(CodeLayer layer) {
        // 大模型策略支持所有层级
        return layer != null;
    }

    @Override
    public String getStrategyName() {
        return "LLM";
    }

    /**
     * 获取类名
     */
    private String getClassName(TableInfo tableInfo) {
        // 将表名转换为类名（驼峰命名）
        String tableName = tableInfo.getTableName();
        String[] parts = tableName.split("_");
        StringBuilder className = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                className.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
            }
        }
        return className.toString();
    }

    /**
     * 保存代码文件
     */
    private void saveCodeFile(String code, TableInfo tableInfo,
                              GeneratorConfig config, CodeLayer layer) throws IOException {
        String outputDir = getOutputDir(config, layer);
        String fileName = getFileName(tableInfo, layer);
        String fileExtension = getFileExtension(layer);

        Path outputPath = Paths.get(outputDir, fileName + fileExtension);
        Files.createDirectories(outputPath.getParent());

        try (FileWriter writer = new FileWriter(outputPath.toFile())) {
            writer.write(code);
        }

        log.info("代码文件已保存: {}", outputPath);
    }

    /**
     * 获取输出目录
     */
    private String getOutputDir(GeneratorConfig config, CodeLayer layer) {
        return switch (layer) {
            case CONSOLE -> config.getConsoleOutputDir();
            case SERVICE -> config.getServiceOutputDir();
            case SERVICE_API -> config.getInterfaceOutputDir();
            case ENUM -> config.getEnumOutputDir();
            case FRONTEND -> config.getFrontendOutputDir();
        };
    }

    /**
     * 获取文件名
     */
    private String getFileName(TableInfo tableInfo, CodeLayer layer) {
        String className = getClassName(tableInfo);
        return switch (layer) {
            case CONSOLE -> className + "ConsoleResource";
            case SERVICE -> className + "Service";
            case SERVICE_API -> className + "Service";
            case ENUM -> className + "Enum";
            case FRONTEND -> className.toLowerCase() + "Api";
        };
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(CodeLayer layer) {
        return switch (layer) {
            case FRONTEND -> ".vue"; // 或 .tsx, .jsx 等
            default -> ".java";
        };
    }
}

