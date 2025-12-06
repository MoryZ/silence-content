# 代码生成器扩展方案总结

## 📋 方案概述

本方案在保留现有FreeMarker模板生成能力的基础上，扩展了大模型代码生成能力，采用策略模式实现灵活切换。

## 🎯 核心目标

1. ✅ **保留现有能力**：完全保留FreeMarker模板生成功能
2. ✅ **扩展大模型能力**：支持使用大模型生成代码
3. ✅ **灵活切换**：支持在模板生成和大模型生成之间灵活选择
4. ✅ **统一流程**：所有生成方式共享同一套接口文档生成流程
5. ✅ **前端支持**：扩展前端代码生成能力

## 📁 新增文件结构

```
silence-content-code-generator/
├── src/main/java/com/old/silence/code/generator/
│   ├── strategy/                          # 策略模式实现
│   │   ├── CodeGenerationStrategy.java    # 策略接口
│   │   ├── TemplateCodeGenerationStrategy.java  # 模板策略（保留现有能力）
│   │   ├── LLMCodeGenerationStrategy.java      # 大模型策略（新增）
│   │   └── CodeGenerationStrategyManager.java   # 策略管理器
│   ├── orchestrator/                      # 编排器
│   │   └── CodeGenerationOrchestrator.java     # 统一流程编排
│   ├── llm/                               # 大模型服务
│   │   └── LLMService.java                # 大模型服务接口
│   ├── parser/                            # SQL解析器
│   │   └── SQLParser.java                 # SQL解析接口
│   └── util/                              # 工具类
│       ├── PromptBuilder.java             # 提示词构建器
│       └── CodeFormatter.java             # 代码格式化器
├── ARCHITECTURE_EXTENSION_PLAN.md         # 架构扩展方案
├── IMPLEMENTATION_GUIDE.md                # 实现指南
└── README_EXTENSION.md                   # 本文档
```

## 🏗️ 架构设计

### 核心设计模式

1. **策略模式**：代码生成采用策略模式，支持多种生成方式
2. **编排器模式**：统一管理代码生成流程
3. **依赖注入**：使用Spring的依赖注入管理组件

### 工作流程

```
┌─────────────────┐
│   SQL输入/数据库 │
└────────┬────────┘
         │
    ┌────▼────┐
    │ SQL解析  │
    └────┬────┘
         │
    ┌────▼────┐
    │ 表结构分析 │
    └────┬────┘
         │
    ┌────▼────┐
    │ API文档生成│
    └────┬────┘
         │
    ┌────▼────────────┐
    │ 策略选择         │
    │ - TEMPLATE      │
    │ - LLM           │
    │ - HYBRID        │
    └────┬────────────┘
         │
    ┌────▼────┐
    │ 代码生成 │
    └─────────┘
```

## 🔧 已实现功能

### ✅ 基础架构

- [x] 代码生成策略接口（CodeGenerationStrategy）
- [x] 模板生成策略（TemplateCodeGenerationStrategy）- 保留现有能力
- [x] 大模型生成策略框架（LLMCodeGenerationStrategy）
- [x] 策略管理器（CodeGenerationStrategyManager）
- [x] 代码生成编排器接口（CodeGenerationOrchestrator）
- [x] 大模型服务接口（LLMService）
- [x] SQL解析器接口（SQLParser）
- [x] 提示词构建器（PromptBuilder）
- [x] 代码格式化器（CodeFormatter）

### ✅ 配置扩展

- [x] GeneratorConfig增加frontendOutputDir配置

## 🚧 待实现功能

### 阶段一：SQL解析器实现

- [ ] 实现MySQLSQLParser（解析CREATE TABLE语句）
- [ ] 支持ALTER TABLE语句解析
- [ ] 支持从SQL文件读取

**推荐工具**：
- JSqlParser（Java SQL解析库）
- ANTLR4（语法解析器生成工具）

### 阶段二：大模型服务实现

- [ ] 实现OpenAI LLM服务
- [ ] 实现Claude LLM服务
- [ ] 实现Ollama LLM服务（本地部署）
- [ ] 支持自定义大模型服务

**依赖添加**：
```xml
<!-- OpenAI SDK -->
<dependency>
    <groupId>com.theokanning.openai-gpt3-java</groupId>
    <artifactId>service</artifactId>
    <version>0.18.2</version>
</dependency>
```

### 阶段三：代码生成编排器实现

- [ ] 实现DefaultCodeGenerationOrchestrator
- [ ] 支持从SQL生成
- [ ] 支持从数据库表生成
- [ ] 支持从API文档生成

### 阶段四：前端代码生成

- [ ] Vue代码生成
- [ ] React代码生成
- [ ] API调用代码生成
- [ ] 路由配置生成

## 📝 使用示例

### 示例1：使用模板策略生成代码（现有方式）

```java
@Autowired
private CodeGenerationStrategyManager strategyManager;

public void generateWithTemplate(TableInfo tableInfo, ApiDocument apiDoc, GeneratorConfig config) {
    CodeGenerationStrategy strategy = strategyManager.getStrategy("TEMPLATE");
    strategy.generateCode(tableInfo, apiDoc, config, CodeLayer.SERVICE);
}
```

### 示例2：使用大模型策略生成代码（新方式）

```java
@Autowired
private CodeGenerationStrategyManager strategyManager;

public void generateWithLLM(TableInfo tableInfo, ApiDocument apiDoc, GeneratorConfig config) {
    CodeGenerationStrategy strategy = strategyManager.getStrategy("LLM");
    strategy.generateCode(tableInfo, apiDoc, config, CodeLayer.SERVICE);
}
```

### 示例3：混合策略（不同层级使用不同策略）

```java
// Console层使用模板，Service层使用大模型
CodeGenerationStrategy templateStrategy = strategyManager.getStrategy("TEMPLATE");
CodeGenerationStrategy llmStrategy = strategyManager.getStrategy("LLM");

templateStrategy.generateCode(tableInfo, apiDoc, config, CodeLayer.CONSOLE);
llmStrategy.generateCode(tableInfo, apiDoc, config, CodeLayer.SERVICE);
```

## ⚙️ 配置说明

### application.yml配置示例

```yaml
code-generator:
  strategy:
    default: TEMPLATE  # 默认策略
    layers:
      console: TEMPLATE
      service: LLM
      service-api: TEMPLATE
      enum: TEMPLATE
      frontend: LLM
  
  llm:
    provider: OPENAI
    api-key: ${LLM_API_KEY}
    model: gpt-4
    temperature: 0.3
    max-tokens: 4000
```

## 🔄 迁移建议

### 渐进式迁移

1. **第一阶段**：继续使用模板策略，保持现有功能不变
2. **第二阶段**：在部分层级（如Service层）尝试使用大模型策略
3. **第三阶段**：根据效果逐步扩展大模型使用范围
4. **第四阶段**：优化提示词，提升生成质量

### 最佳实践

- **基础代码**（DTO、Entity等）：使用模板策略，保证一致性
- **业务逻辑**（Service层）：使用大模型策略，处理复杂逻辑
- **前端代码**：使用大模型策略，生成现代化UI代码

## 📚 相关文档

- [架构扩展方案](ARCHITECTURE_EXTENSION_PLAN.md) - 详细的架构设计文档
- [实现指南](IMPLEMENTATION_GUIDE.md) - 具体的实现步骤和代码示例

## 🎓 设计原则

本方案遵循以下设计原则：

1. **单一职责原则**：每个类只负责一个功能
2. **开闭原则**：对扩展开放，对修改关闭
3. **依赖倒置原则**：依赖抽象接口，而非具体实现
4. **KISS原则**：保持简单，避免过度设计
5. **SOLID原则**：遵循面向对象设计原则

## 💡 优势

1. **向后兼容**：完全保留现有模板生成能力
2. **灵活扩展**：易于添加新的生成策略
3. **智能增强**：大模型可以处理复杂业务逻辑
4. **统一流程**：所有生成方式共享同一套接口文档
5. **渐进式迁移**：可以逐步从模板迁移到大模型

## ⚠️ 注意事项

1. **大模型成本**：注意API调用成本，建议使用缓存机制
2. **生成质量**：需要不断优化提示词，提升生成质量
3. **网络依赖**：大模型服务依赖网络，建议支持本地模型（Ollama）
4. **代码安全**：生成的代码需要经过审查和测试

## 🚀 下一步行动

1. 选择并集成大模型SDK（推荐OpenAI或Ollama）
2. 实现SQL解析器（推荐使用JSqlParser）
3. 实现代码生成编排器
4. 编写单元测试和集成测试
5. 优化提示词模板
6. 实现前端代码生成

---

**创建时间**：2024年
**作者**：moryzang
**版本**：1.0.0

