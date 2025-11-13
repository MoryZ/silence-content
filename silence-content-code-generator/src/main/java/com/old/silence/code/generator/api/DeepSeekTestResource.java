package com.old.silence.code.generator.api;

import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.llm.LLMService;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.orchestrator.CodeGenerationOrchestrator;
import com.old.silence.code.generator.parser.SQLParser;
import com.old.silence.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.code.generator.service.RuleProcessorService;
import com.old.silence.code.generator.strategy.CodeGenerationStrategy;
import com.old.silence.code.generator.util.CodeFormatter;
import com.old.silence.code.generator.util.PromptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * DeepSeek代码生成测试接口
 * 用于测试DeepSeek代码生成效果
 * 
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1/test/deepseek")
public class DeepSeekTestResource {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekTestResource.class);

    private final SQLParser sqlParser;
    private final ApiDocumentGeneratorService apiDocService;
    private final RuleProcessorService ruleProcessorService;
    private final PromptBuilder promptBuilder;
    private final CodeGenerationOrchestrator orchestrator;
    private final LLMService llmService;
    private final CodeFormatter codeFormatter;

    public DeepSeekTestResource(SQLParser sqlParser,
                                ApiDocumentGeneratorService apiDocService,
                                RuleProcessorService ruleProcessorService,
                                PromptBuilder promptBuilder,
                                CodeGenerationOrchestrator orchestrator,
                                LLMService llmService,
                                CodeFormatter codeFormatter) {
        this.sqlParser = sqlParser;
        this.apiDocService = apiDocService;
        this.ruleProcessorService = ruleProcessorService;
        this.promptBuilder = promptBuilder;
        this.orchestrator = orchestrator;
        this.llmService = llmService;
        this.codeFormatter = codeFormatter;
    }

    /**
     * 测试1: 从SQL生成代码（完整流程）
     */
    @PostMapping("/generate-from-sql")
    public ResponseEntity<Map<String, Object>> generateFromSQL(@RequestBody GenerateRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("开始从SQL生成代码，表名: {}", request.getTableName());
            
            GeneratorConfig config = createConfig(request);
            CodeGenerationOrchestrator.GenerationResult generationResult = 
                orchestrator.generateFromSQL(request.getSql(), config);
            
            if (!generationResult.isSuccess()) {
                result.put("success", false);
                result.put("message", generationResult.getMessage());
                return ResponseEntity.badRequest().body(result);
            }
            
            TableInfo tableInfo = generationResult.getTableInfo();
            ApiDocument apiDoc = generationResult.getApiDocument();
            
            result.put("success", true);
            result.put("message", generationResult.getMessage());
            if (tableInfo != null) {
                result.put("tableInfo", Map.of(
                    "tableName", tableInfo.getTableName(),
                    "comment", tableInfo.getComment() != null ? tableInfo.getComment() : "",
                    "columnCount", tableInfo.getColumnInfos() != null ? tableInfo.getColumnInfos().size() : 0
                ));
            }
            if (apiDoc != null) {
                result.put("apiDoc", Map.of(
                    "endpointCount", apiDoc.getEndpoints() != null ? apiDoc.getEndpoints().size() : 0
                ));
            }
            result.put("generationResult", generationResult);
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("代码生成失败", e);
            result.put("success", false);
            result.put("message", "代码生成失败: " + e.getMessage());
            result.put("error", e.getClass().getSimpleName());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    /**
     * 测试2: 测试提示词构建
     */
    @PostMapping("/test-prompt")
    public ResponseEntity<Map<String, Object>> testPrompt(@RequestBody PromptTestRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 解析SQL
            TableInfo tableInfo = sqlParser.parseCreateTable(request.getSql());
            if (tableInfo == null) {
                result.put("success", false);
                result.put("message", "SQL解析失败");
                return ResponseEntity.badRequest().body(result);
            }
            
            // 2. 字段转换
            tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
            
            // 3. 生成API文档
            ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
            
            // 4. 构建提示词
            GeneratorConfig config = createConfig(null);
            CodeGenerationStrategy.CodeLayer layer = 
                CodeGenerationStrategy.CodeLayer.valueOf(request.getLayer().toUpperCase());
            
            String prompt = promptBuilder.buildPrompt(tableInfo, apiDoc, config, layer);
            
            result.put("success", true);
            result.put("prompt", prompt);
            result.put("promptLength", prompt.length());
            result.put("layer", layer.name());
            result.put("tableName", tableInfo.getTableName());
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("提示词构建失败", e);
            result.put("success", false);
            result.put("message", "提示词构建失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    /**
     * 测试3: 直接调用DeepSeek生成代码
     */
    @PostMapping("/generate-code")
    public ResponseEntity<Map<String, Object>> generateCode(@RequestBody CodeGenerateRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 解析SQL
            TableInfo tableInfo = sqlParser.parseCreateTable(request.getSql());
            if (tableInfo == null) {
                result.put("success", false);
                result.put("message", "SQL解析失败");
                return ResponseEntity.badRequest().body(result);
            }
            
            // 2. 字段转换
            tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);
            
            // 3. 生成API文档
            ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
            
            // 4. 构建提示词
            GeneratorConfig config = createConfig(null);
            CodeGenerationStrategy.CodeLayer layer = 
                CodeGenerationStrategy.CodeLayer.valueOf(request.getLayer().toUpperCase());
            
            String prompt = promptBuilder.buildPrompt(tableInfo, apiDoc, config, layer);
            
            // 5. 调用DeepSeek生成代码
            log.info("调用DeepSeek生成代码，层级: {}", layer);
            
            // 构建上下文
            com.old.silence.code.generator.llm.LLMService.CodeGenerationContext context = 
                new com.old.silence.code.generator.llm.LLMService.CodeGenerationContext();
            context.setTableInfo(tableInfo);
            context.setApiDocument(apiDoc);
            context.setLayer(layer.name());
            context.setBasePackage(config.getBasePackage());
            context.setClassName(getClassName(tableInfo));
            
            // 直接调用LLM服务生成代码
            String generatedCode = llmService.generateCode(prompt, context);
            log.info("DeepSeek生成的代码长度: {} 字符", generatedCode != null ? generatedCode.length() : 0);
            
            // 格式化代码
            String formattedCode = codeFormatter.format(generatedCode, layer);
            
            result.put("success", true);
            result.put("message", "代码生成成功");
            result.put("layer", layer.name());
            result.put("tableName", tableInfo.getTableName());
            result.put("promptLength", prompt.length());
            result.put("code", formattedCode);
            result.put("rawCode", generatedCode);
            result.put("codeLength", formattedCode != null ? formattedCode.length() : 0);
            result.put("rawCodeLength", generatedCode != null ? generatedCode.length() : 0);
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("代码生成失败", e);
            result.put("success", false);
            result.put("message", "代码生成失败: " + e.getMessage());
            result.put("error", e.getClass().getSimpleName());
            return ResponseEntity.internalServerError().body(result);
        }
    }
    
    /**
     * 获取类名
     */
    private String getClassName(TableInfo tableInfo) {
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
     * 测试4: 获取DeepSeek配置信息
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        Map<String, Object> result = new HashMap<>();
        
        String apiKey = System.getenv("DEEPSEEK_API_KEY");
        boolean configured = apiKey != null && !apiKey.isEmpty() && !apiKey.equals("your-deepseek-api-key");
        
        result.put("configured", configured);
        result.put("apiKeySet", configured);
        result.put("provider", "DEEPSEEK");
        result.put("baseUrl", "https://api.deepseek.com");
        result.put("model", "deepseek-coder");
        result.put("message", configured 
            ? "DeepSeek已配置，可以开始测试" 
            : "请设置环境变量 DEEPSEEK_API_KEY 或在配置文件中配置");
        
        return ResponseEntity.ok(result);
    }

    /**
     * 创建配置
     */
    private GeneratorConfig createConfig(GenerateRequest request) {
        GeneratorConfig config = new GeneratorConfig();
        config.setBasePackage("com.old.silence.content");
        config.setServiceOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");
        config.setConsoleOutputDir("silence-content-console/src/main/java");
        config.setApiDocOutputDir("silence-content-api-docs");
        config.setStrategyType("LLM");
        
        if (request != null && request.getBasePackage() != null) {
            config.setBasePackage(request.getBasePackage());
        }
        
        return config;
    }

    /**
     * 代码生成请求
     */
    public static class GenerateRequest {
        private String sql;
        private String tableName;
        private String basePackage;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }
    }

    /**
     * 提示词测试请求
     */
    public static class PromptTestRequest {
        private String sql;
        private String layer = "SERVICE";

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }
    }

    /**
     * 代码生成请求
     */
    public static class CodeGenerateRequest {
        private String sql;
        private String layer = "SERVICE";

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }
    }
}

