# 代码生成器扩展实现指南

## 一、快速开始

### 1.1 当前架构概览

现有代码生成器已经实现了：
- ✅ SQL表结构分析（SQLAnalyzer）
- ✅ API接口文档生成（ApiDocumentGeneratorService）
- ✅ 基于FreeMarker模板的代码生成（SpringCodeGenerator）

新增扩展能力：
- 🔄 策略模式支持多种生成方式
- 🔄 大模型代码生成（待实现）
- 🔄 SQL输入解析（待实现）
- 🔄 前端代码生成（待实现）

### 1.2 核心接口说明

#### CodeGenerationStrategy（代码生成策略接口）

所有代码生成策略都需要实现此接口：

```java
public interface CodeGenerationStrategy {
    void generateCode(TableInfo tableInfo, ApiDocument apiDoc, 
                     GeneratorConfig config, CodeLayer layer);
    boolean supports(CodeLayer layer);
    String getStrategyName();
}
```

#### CodeGenerationOrchestrator（代码生成编排器）

统一管理代码生成流程：

```java
public interface CodeGenerationOrchestrator {
    GenerationResult generateFromSQL(String sql, GeneratorConfig config);
    GenerationResult generateFromDatabase(String tableName, GeneratorConfig config);
    GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config);
}
```

## 二、实现步骤

### 阶段一：完成基础架构（当前状态）

✅ 已创建核心接口和策略类
✅ 已创建模板生成策略（保留现有能力）
✅ 已创建大模型生成策略框架

### 阶段二：实现SQL解析器

需要实现 `SQLParser` 接口：

```java
@Service
public class MySQLSQLParser implements SQLParser {
    @Override
    public TableInfo parseCreateTable(String sql) {
        // 使用正则表达式或ANTLR解析CREATE TABLE语句
        // 提取表名、字段、索引等信息
    }
}
```

**推荐工具**：
- **ANTLR4**：强大的语法解析器生成工具
- **正则表达式**：简单场景可用
- **JSqlParser**：Java SQL解析库

### 阶段三：实现大模型服务

需要实现 `LLMService` 接口，支持多种大模型提供商：

#### 3.1 OpenAI实现示例

```java
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "OPENAI")
public class OpenAILLMService implements LLMService {
    
    private final OpenAIClient client;
    
    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4")
            .messages(List.of(
                Message.builder()
                    .role("system")
                    .content("你是一个经验丰富的Java开发工程师")
                    .build(),
                Message.builder()
                    .role("user")
                    .content(prompt)
                    .build()
            ))
            .temperature(0.3)
            .maxTokens(4000)
            .build();
            
        ChatCompletionResponse response = client.createChatCompletion(request);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
```

#### 3.2 Ollama实现示例（本地部署）

```java
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "OLLAMA")
public class OllamaLLMService implements LLMService {
    
    private final RestTemplate restTemplate;
    
    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        String url = "http://localhost:11434/api/generate";
        OllamaRequest request = new OllamaRequest("llama2", prompt);
        OllamaResponse response = restTemplate.postForObject(url, request, OllamaResponse.class);
        return response.getResponse();
    }
}
```

### 阶段四：实现代码生成编排器

```java
@Service
public class DefaultCodeGenerationOrchestrator implements CodeGenerationOrchestrator {
    
    private final SQLParser sqlParser;
    private final SQLAnalyzer sqlAnalyzer;
    private final ApiDocumentGeneratorService apiDocService;
    private final CodeGenerationStrategyManager strategyManager;
    
    @Override
    public GenerationResult generateFromSQL(String sql, GeneratorConfig config) {
        // 1. 解析SQL
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);
        
        // 2. 生成API文档
        ApiDocument apiDoc = apiDocService.generateDocument(tableInfo);
        
        // 3. 生成代码
        generateCodeWithStrategy(tableInfo, apiDoc, config);
        
        return GenerationResult.success("生成成功", tableInfo, apiDoc, config.getOutputPath());
    }
    
    private void generateCodeWithStrategy(TableInfo tableInfo, ApiDocument apiDoc, GeneratorConfig config) {
        // 根据配置选择策略
        CodeGenerationStrategy strategy = strategyManager.getStrategy(config.getStrategyType());
        
        // 生成各层级代码
        for (CodeLayer layer : CodeLayer.values()) {
            if (strategy.supports(layer)) {
                strategy.generateCode(tableInfo, apiDoc, config, layer);
            }
        }
    }
}
```

## 三、配置示例

### 3.1 application.yml配置

```yaml
code-generator:
  strategy:
    # 默认策略：TEMPLATE, LLM, HYBRID
    default: TEMPLATE
    # 各层级策略配置
    layers:
      console: TEMPLATE
      service: LLM
      service-api: TEMPLATE
      enum: TEMPLATE
      frontend: LLM
  
  llm:
    provider: OPENAI  # OPENAI, CLAUDE, OLLAMA
    api-key: ${LLM_API_KEY:your-api-key}
    model: gpt-4
    base-url: https://api.openai.com/v1
    temperature: 0.3
    max-tokens: 4000
    timeout: 30000
  
  output:
    api-doc: silence-content-api-docs
    service: silence-content-service/src/main/java
    service-api: silence-content-service-api/src/main/java
    console: silence-content-console/src/main/java
    enum: silence-content-service-enums/src/main/java
    frontend: frontend/src
```

### 3.2 使用示例

```java
@RestController
@RequestMapping("/api/v1/generator")
public class GeneratorResource {
    
    private final CodeGenerationOrchestrator orchestrator;
    
    @PostMapping("/from-sql")
    public ResponseEntity<?> generateFromSQL(@RequestBody SQLGenerationRequest request) {
        GeneratorConfig config = createConfig(request);
        GenerationResult result = orchestrator.generateFromSQL(request.getSql(), config);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/from-database")
    public ResponseEntity<?> generateFromDatabase(@RequestBody DatabaseGenerationRequest request) {
        GeneratorConfig config = createConfig(request);
        GenerationResult result = orchestrator.generateFromDatabase(request.getTableName(), config);
        return ResponseEntity.ok(result);
    }
}
```

## 四、提示词工程

### 4.1 提示词模板

建议将提示词模板化，便于调整：

```yaml
code-generator:
  prompts:
    service:
      system: "你是一个经验丰富的Java开发工程师，擅长Spring Boot项目开发。"
      template: |
        根据以下表结构和API文档，生成Service层代码：
        
        表名：{{tableName}}
        字段：
        {{#columns}}
        - {{fieldName}} ({{fieldType}}) - {{comment}}
        {{/columns}}
        
        API文档：
        {{#endpoints}}
        - {{name}}: {{method}} {{path}}
        {{/endpoints}}
        
        要求：
        1. 遵循Spring Boot最佳实践
        2. 使用{{basePackage}}包结构
        3. 实现所有CRUD操作
        4. 代码要清晰、可维护
```

### 4.2 Few-Shot Learning

提供示例代码，帮助大模型更好地理解需求：

```java
private String buildPromptWithExamples(TableInfo tableInfo, CodeLayer layer) {
    String prompt = buildPrompt(tableInfo, layer);
    
    // 添加示例
    String example = getExampleCode(layer);
    prompt += "\n\n## 参考示例\n" + example;
    
    return prompt;
}
```

## 五、代码质量保证

### 5.1 代码格式化

使用Google Java Format格式化生成的代码：

```java
@Component
public class CodeFormatter {
    
    public String format(String code) {
        // 使用google-java-format格式化
        Formatter formatter = new Formatter();
        return formatter.formatSource(code);
    }
}
```

### 5.2 代码验证

生成后自动验证代码：

```java
public boolean validateCode(String code) {
    // 1. 语法检查
    if (!isValidJavaSyntax(code)) {
        return false;
    }
    
    // 2. 编译检查（可选）
    // 3. 静态分析（可选）
    
    return true;
}
```

## 六、性能优化

### 6.1 缓存机制

缓存大模型生成结果，避免重复调用：

```java
@Service
public class CachedLLMService implements LLMService {
    
    private final Cache<String, String> codeCache;
    
    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        String cacheKey = generateCacheKey(prompt, context);
        return codeCache.get(cacheKey, () -> {
            return llmService.generateCode(prompt, context);
        });
    }
}
```

### 6.2 批量生成

支持批量生成多个表的代码：

```java
public List<GenerationResult> generateBatch(List<String> tableNames, GeneratorConfig config) {
    return tableNames.parallelStream()
        .map(tableName -> orchestrator.generateFromDatabase(tableName, config))
        .toList();
}
```

## 七、扩展点

### 7.1 自定义策略

实现 `CodeGenerationStrategy` 接口，创建自定义生成策略：

```java
@Service
public class CustomCodeGenerationStrategy implements CodeGenerationStrategy {
    // 实现自定义逻辑
}
```

### 7.2 自定义大模型服务

实现 `LLMService` 接口，支持其他大模型提供商：

```java
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "CUSTOM")
public class CustomLLMService implements LLMService {
    // 实现自定义大模型调用逻辑
}
```

## 八、测试建议

### 8.1 单元测试

```java
@SpringBootTest
class CodeGenerationStrategyTest {
    
    @Autowired
    private TemplateCodeGenerationStrategy templateStrategy;
    
    @Test
    void testTemplateGeneration() {
        // 测试模板生成
    }
}
```

### 8.2 集成测试

```java
@SpringBootTest
class CodeGenerationOrchestratorTest {
    
    @Autowired
    private CodeGenerationOrchestrator orchestrator;
    
    @Test
    void testGenerateFromSQL() {
        String sql = "CREATE TABLE user (id BIGINT PRIMARY KEY, name VARCHAR(100))";
        GenerationResult result = orchestrator.generateFromSQL(sql, config);
        assertTrue(result.isSuccess());
    }
}
```

## 九、后续优化方向

1. **智能提示词优化**：根据历史生成结果自动优化提示词
2. **代码审查集成**：生成后自动进行代码审查
3. **增量生成**：只生成变更部分的代码
4. **多语言支持**：支持生成其他语言的代码
5. **可视化界面**：提供Web界面进行代码生成

