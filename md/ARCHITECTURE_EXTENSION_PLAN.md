# 代码生成器扩展方案 - 集成大模型能力

## 一、设计目标

1. **保留现有能力**：继续支持FreeMarker模板生成代码
2. **扩展大模型能力**：支持使用大模型生成代码
3. **灵活切换**：支持在模板生成和大模型生成之间灵活选择
4. **统一接口**：保持API接口文档生成流程不变
5. **支持前端**：扩展前端代码生成能力

## 二、架构设计

### 2.1 核心设计原则

- **策略模式**：代码生成器采用策略模式，支持多种生成策略
- **单一职责**：每个生成器只负责一种生成方式
- **开闭原则**：对扩展开放，对修改关闭
- **依赖倒置**：依赖抽象接口，而非具体实现

### 2.2 架构分层

```
┌─────────────────────────────────────────────────────────┐
│                    GeneratorResource                     │
│                    (API入口层)                           │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│              CodeGenerationOrchestrator                  │
│              (编排层 - 统一流程控制)                      │
└──────┬───────────────────────┬──────────────────────────┘
       │                       │
       │                       │
┌──────▼──────────┐   ┌────────▼──────────────┐
│ SQL输入解析器    │   │ 数据库表结构分析器     │
│ SQLParser       │   │ SQLAnalyzer (现有)    │
└──────┬──────────┘   └────────┬──────────────┘
       │                       │
       └───────────┬───────────┘
                   │
         ┌─────────▼─────────┐
         │   TableInfo       │
         │   (表结构模型)     │
         └─────────┬─────────┘
                   │
         ┌─────────▼─────────┐
         │  ApiDocument      │
         │  (接口文档模型)    │
         └─────────┬─────────┘
                   │
    ┌──────────────┼──────────────┐
    │              │              │
┌───▼────┐   ┌────▼─────┐   ┌───▼──────┐
│模板生成│   │大模型生成│   │混合生成  │
│策略    │   │策略      │   │策略      │
└────────┘   └──────────┘   └──────────┘
```

### 2.3 核心接口设计

#### 2.3.1 代码生成策略接口

```java
public interface CodeGenerationStrategy {
    /**
     * 生成代码
     * @param tableInfo 表信息
     * @param apiDoc 接口文档
     * @param config 配置
     * @param layer 生成层级 (CONSOLE, SERVICE, SERVICE_API, ENUM, FRONTEND)
     */
    void generateCode(TableInfo tableInfo, ApiDocument apiDoc, 
                     GeneratorConfig config, CodeLayer layer);
    
    /**
     * 是否支持该层级
     */
    boolean supports(CodeLayer layer);
    
    /**
     * 策略名称
     */
    String getStrategyName();
}
```

#### 2.3.2 代码生成编排器

```java
public interface CodeGenerationOrchestrator {
    /**
     * 从SQL输入生成
     */
    GenerationResult generateFromSQL(String sql, GeneratorConfig config);
    
    /**
     * 从数据库表生成
     */
    GenerationResult generateFromDatabase(String tableName, GeneratorConfig config);
    
    /**
     * 从接口文档生成代码
     */
    GenerationResult generateFromApiDocument(ApiDocument apiDoc, GeneratorConfig config);
}
```

## 三、实现方案

### 3.1 SQL输入解析器

**功能**：解析用户输入的SQL语句，提取表结构信息

```java
public class SQLParser {
    /**
     * 解析CREATE TABLE语句
     */
    public TableInfo parseCreateTable(String sql);
    
    /**
     * 解析ALTER TABLE语句
     */
    public TableInfo parseAlterTable(String sql);
    
    /**
     * 从SQL文件读取
     */
    public List<TableInfo> parseFromFile(String filePath);
}
```

### 3.2 代码生成策略实现

#### 3.2.1 模板生成策略（现有能力保留）

```java
@Service
public class TemplateCodeGenerationStrategy implements CodeGenerationStrategy {
    private final SpringCodeGenerator codeGenerator;
    
    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc, 
                           GeneratorConfig config, CodeLayer layer) {
        // 使用现有的FreeMarker模板生成逻辑
    }
}
```

#### 3.2.2 大模型生成策略（新增）

```java
@Service
public class LLMCodeGenerationStrategy implements CodeGenerationStrategy {
    private final LLMService llmService;
    
    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc, 
                           GeneratorConfig config, CodeLayer layer) {
        // 1. 构建提示词
        String prompt = buildPrompt(tableInfo, apiDoc, layer);
        
        // 2. 调用大模型
        String generatedCode = llmService.generate(prompt);
        
        // 3. 代码后处理（格式化、验证等）
        String formattedCode = postProcessCode(generatedCode);
        
        // 4. 保存代码文件
        saveCodeFile(formattedCode, config, layer);
    }
    
    private String buildPrompt(TableInfo tableInfo, ApiDocument apiDoc, CodeLayer layer) {
        // 根据层级和表信息构建提示词
    }
}
```

#### 3.2.3 混合生成策略（可选）

```java
@Service
public class HybridCodeGenerationStrategy implements CodeGenerationStrategy {
    private final TemplateCodeGenerationStrategy templateStrategy;
    private final LLMCodeGenerationStrategy llmStrategy;
    
    @Override
    public void generateCode(TableInfo tableInfo, ApiDocument apiDoc, 
                           GeneratorConfig config, CodeLayer layer) {
        // 根据配置决定使用哪种策略
        // 例如：基础代码用模板，业务逻辑用大模型
    }
}
```

### 3.3 大模型服务接口

```java
public interface LLMService {
    /**
     * 生成代码
     */
    String generateCode(String prompt, CodeGenerationContext context);
    
    /**
     * 生成API文档
     */
    ApiDocument generateApiDocument(TableInfo tableInfo, String requirements);
    
    /**
     * 生成前端代码
     */
    String generateFrontendCode(ApiDocument apiDoc, FrontendConfig config);
}
```

### 3.4 前端代码生成扩展

```java
@Service
public class FrontendCodeGenerator {
    private final CodeGenerationStrategy strategy;
    
    public void generateVueCode(ApiDocument apiDoc, GeneratorConfig config) {
        // 生成Vue组件、API调用、路由等
    }
    
    public void generateReactCode(ApiDocument apiDoc, GeneratorConfig config) {
        // 生成React组件、Hooks、API调用等
    }
}
```

## 四、配置设计

### 4.1 生成策略配置

```yaml
code-generator:
  strategy:
    # 生成策略：TEMPLATE, LLM, HYBRID
    type: HYBRID
    # 各层级使用的策略
    layers:
      console: TEMPLATE
      service: LLM
      service-api: TEMPLATE
      enum: TEMPLATE
      frontend: LLM
  llm:
    provider: OPENAI  # OPENAI, CLAUDE, OLLAMA, 自定义
    api-key: ${LLM_API_KEY}
    model: gpt-4
    temperature: 0.3
    max-tokens: 4000
```

### 4.2 提示词模板配置

支持可配置的提示词模板，便于调整生成效果：

```yaml
code-generator:
  prompts:
    service:
      template: |
        根据以下表结构和API文档，生成Service层代码：
        表名：{{tableName}}
        字段：{{columns}}
        API文档：{{apiDoc}}
        
        要求：
        1. 遵循Spring Boot最佳实践
        2. 使用{{basePackage}}包结构
        3. 实现所有CRUD操作
```

## 五、实现步骤

### 阶段一：基础架构搭建（1-2周）

1. ✅ 创建代码生成策略接口
2. ✅ 重构现有模板生成器为策略实现
3. ✅ 创建代码生成编排器
4. ✅ 实现SQL输入解析器

### 阶段二：大模型集成（2-3周）

1. ✅ 集成大模型SDK（OpenAI/Claude/Ollama等）
2. ✅ 实现大模型生成策略
3. ✅ 设计提示词模板系统
4. ✅ 实现代码后处理（格式化、验证）

### 阶段三：前端代码生成（1-2周）

1. ✅ 扩展前端代码生成能力
2. ✅ 支持Vue/React等框架
3. ✅ 生成API调用代码、路由配置等

### 阶段四：优化和完善（1周）

1. ✅ 性能优化（缓存、批量生成）
2. ✅ 错误处理和重试机制
3. ✅ 代码质量检查
4. ✅ 文档完善

## 六、技术选型建议

### 6.1 大模型服务

- **OpenAI API**：稳定、功能强大，适合生产环境
- **Claude API**：代码生成能力强
- **Ollama**：本地部署，数据安全
- **自定义模型**：支持私有化部署

### 6.2 提示词工程

- 使用模板引擎（如FreeMarker）构建提示词
- 支持few-shot learning（提供示例）
- 支持链式提示（Chain of Thought）

### 6.3 代码后处理

- 使用Java代码格式化工具（Google Java Format）
- 代码静态检查（Checkstyle、SpotBugs）
- 自动修复常见问题

## 七、优势分析

1. **向后兼容**：完全保留现有模板生成能力
2. **灵活扩展**：易于添加新的生成策略
3. **智能增强**：大模型可以处理复杂业务逻辑
4. **统一流程**：所有生成方式共享同一套接口文档
5. **渐进式迁移**：可以逐步从模板迁移到大模型

## 八、风险与应对

1. **大模型成本**：使用缓存、批量生成降低调用次数
2. **生成质量**：结合模板生成基础代码，大模型生成业务逻辑
3. **网络依赖**：支持本地模型（Ollama）作为备选
4. **代码安全**：代码审查、静态检查、人工审核

