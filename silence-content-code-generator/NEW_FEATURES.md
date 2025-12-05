# 代码生成器新增功能说明

本次更新新增了以下四个重要功能：

## 1. 表级配置复用（TableGeneratorConfig）

### 功能说明
支持从全局配置复制并自定义表级配置，每张表可以有独立的配置。

### 使用示例
```java
// 从全局配置复制并自定义
TableGeneratorConfig tableConfig = TableGeneratorConfig.fromGlobalConfig("user_table", globalConfig);

// 自定义渲染配置
tableConfig.getRenderConfig().setAuthorName("custom-author");
tableConfig.getRenderConfig().setPrimaryType("Long");

// 自定义数据库配置
tableConfig.getDatabaseConfig().setDbUrl("jdbc:mysql://localhost:3306/custom_db");

// 自定义输出目录
tableConfig.getOutputDirectoryConfig().setServiceOutputDir("/custom/service/dir");

// 自定义枚举配置
EnumConfig customEnum = new EnumConfig();
customEnum.setTableName("user_table");
customEnum.setColumnName("status");
customEnum.setEnumName("UserStatus");
tableConfig.getEnumConfigs().add(customEnum);
```

### 核心类
- `TableGeneratorConfig` - 表级配置容器
- `TableGeneratorConfig.fromGlobalConfig(tableName, globalConfig)` - 配置复制方法

---

## 2. 步骤拆解验证接口

### 功能说明
将代码生成流程拆分为3个独立步骤，每个步骤都可以验证并返回结果，避免一次性生成后发现问题。

### 接口列表

#### 步骤1：查看表结构信息
**接口**: `POST /api/v1/validate/step1-table-info`

**请求示例**:
```json
{
  "tableName": "user",
  "globalConfig": {
    "dbUrl": "jdbc:mysql://localhost:3306/mydb",
    "username": "root",
    "password": "password",
    "basePackage": "com.example.project"
  }
}
```

**响应示例**:
```json
{
  "tableName": "user",
  "tableComment": "用户表",
  "primaryKeys": ["id"],
  "columns": [
    {
      "columnName": "id",
      "dataType": "bigint",
      "comment": "主键ID",
      "nullable": false,
      "defaultValue": null
    },
    {
      "columnName": "username",
      "dataType": "varchar",
      "comment": "用户名",
      "nullable": false,
      "defaultValue": null
    }
  ],
  "indexes": [
    {
      "indexName": "idx_username",
      "columns": ["username"],
      "unique": true
    }
  ],
  "foreignKeys": []
}
```

---

#### 步骤2：查看生成的API文档
**接口**: `POST /api/v1/validate/step2-api-doc`

**请求示例**:
```json
{
  "tableName": "user",
  "globalConfig": { ... },
  "customApiDoc": null  // 可选：传入自定义API文档，否则使用标准CRUD
}
```

**响应示例**:
```json
{
  "tableName": "user",
  "tableComment": "用户表",
  "customApi": false,
  "apiDocument": {
    "tableName": "user",
    "endpoints": [
      {
        "name": "创建",
        "path": "/user",
        "method": "POST",
        "description": "创建用户",
        "requestBody": { ... },
        "response": { ... }
      },
      {
        "name": "分页查询",
        "path": "/user/page",
        "method": "GET",
        "description": "分页查询用户列表",
        "parameters": [ ... ],
        "response": { ... }
      }
    ]
  }
}
```

---

#### 步骤3：预览生成的代码（包含导入和排序分析）
**接口**: `POST /api/v1/validate/step3-preview-code`

**请求示例**:
```json
{
  "tableName": "user",
  "globalConfig": { ... },
  "customApiDoc": null
}
```

**响应示例**:
```json
{
  "tableName": "user",
  "tableComment": "用户表",
  "files": [
    {
      "module": "interface",
      "fileName": "UserDTO.java",
      "filePath": "/path/to/UserDTO.java",
      "content": "package com.example.dto;\n\npublic class UserDTO { ... }",
      "fileType": "dto"
    },
    {
      "module": "service",
      "fileName": "UserService.java",
      "filePath": "/path/to/UserService.java",
      "content": "package com.example.service;\n\npublic interface UserService { ... }",
      "fileType": "service"
    }
  ],
  "importSuggestions": {
    "/path/to/UserDTO.java": [
      "java.time.LocalDateTime",
      "javax.validation.constraints.NotNull"
    ]
  },
  "missingImportWarnings": {
    "/path/to/UserDTO.java": [
      "缺失类型导入：LocalDateTime -> java.time.LocalDateTime",
      "缺失注解导入：@NotNull -> javax.validation.constraints.NotNull"
    ]
  },
  "sortingSuggestions": [
    "建议按以下顺序组织代码：常量 → 字段 → 构造器 → public方法 → protected方法 → private方法",
    "建议按以下顺序组织导入：java.* → javax.* → com.* → org.* → 自定义包"
  ]
}
```

---

## 3. 导入分析（ImportAnalyzer）

### 功能说明
自动分析生成的代码，检测缺失的导入语句，并提供导入建议。

### 支持的分析
- **注解导入**: Spring注解、Lombok注解、Validation注解等
- **类型导入**: 集合类、日期时间类、Spring类型等
- **导入组织**: 按包名分组排序（java.* → javax.* → org.springframework.* → com.* → org.* → other）

### 识别的常见注解
```java
// Spring MVC
@RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
@RequestBody, @PathVariable, @RequestParam

// Spring Core
@Service, @Component, @Autowired, @Transactional

// Lombok
@Data, @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder, @Slf4j

// Validation
@Valid, @NotNull, @NotBlank, @Size, @Min, @Max
```

### 识别的常见类型
```java
// 集合类
List, ArrayList, Map, HashMap, Set, HashSet, Collection, Optional

// 日期时间
Date, LocalDate, LocalDateTime, LocalTime

// 数值类
BigDecimal, BigInteger

// Spring类型
ResponseEntity, HttpStatus
```

### 使用示例
```java
@Autowired
private ImportAnalyzer importAnalyzer;

public void analyzeCode(List<GeneratedFile> files) {
    ImportAnalysisResult result = importAnalyzer.analyzeImports(files);
    
    // 获取导入建议
    Map<String, List<String>> suggestions = result.getSuggestions();
    // "/path/to/UserDTO.java" -> ["java.time.LocalDateTime", "javax.validation.constraints.NotNull"]
    
    // 获取警告
    Map<String, List<String>> warnings = result.getWarnings();
    // "/path/to/UserDTO.java" -> ["缺失类型导入：LocalDateTime -> java.time.LocalDateTime"]
}
```

---

## 4. 代码排序建议

### 功能说明
提供代码组织和导入语句排序的建议。

### 排序规则

#### 导入语句排序
```
java.*
[空行]
javax.*
[空行]
org.springframework.*
[空行]
com.*
[空行]
org.*
[空行]
其他包
```

#### 类成员排序
```
1. 常量（static final）
2. 静态变量（static）
3. 实例变量（按可见性：public → protected → private）
4. 构造器
5. public方法
6. protected方法
7. private方法
```

### 使用示例
```java
@Autowired
private ImportAnalyzer importAnalyzer;

public List<String> getSortingSuggestions() {
    List<String> suggestions = Arrays.asList(
        "建议按以下顺序组织代码：常量 → 字段 → 构造器 → public方法 → protected方法 → private方法",
        "建议按以下顺序组织导入：java.* → javax.* → com.* → org.* → 自定义包"
    );
    return suggestions;
}

// 组织导入
Set<String> imports = extractImportsFromCode(code);
List<String> organizedImports = importAnalyzer.organizeImports(imports);
```

---

## 工作流程建议

### 推荐流程
```
1. 步骤1：查看表结构
   ↓ 验证表结构正确
   
2. 步骤2：查看API文档
   ↓ 确认接口定义符合需求
   
3. 步骤3：预览代码
   ↓ 检查生成的代码
   ↓ 查看导入建议
   ↓ 查看排序建议
   
4. 最终生成
   调用 /api/v1/generate/batch 或
   /api/v1/generate/batch-with-custom-api
```

### 错误处理
每个步骤都可以独立验证，如果发现问题：
- **步骤1问题**: 检查表结构、索引、注释是否正确
- **步骤2问题**: 调整自定义API文档或修改表结构
- **步骤3问题**: 根据导入建议和排序建议调整模板或配置

---

## 核心类说明

### 新增DTO类
- `Step1TableInfoResponse` - 步骤1响应（表信息）
- `Step2ApiDocResponse` - 步骤2响应（API文档）
- `Step3CodePreviewResponse` - 步骤3响应（代码预览+分析）
- `ValidationRequest` - 统一验证请求
- `TableGeneratorConfig` - 表级配置

### 新增Service类
- `ValidationService` - 验证服务（协调3个步骤）
- `ImportAnalyzer` - 导入分析器

### 新增Resource接口
- `POST /api/v1/validate/step1-table-info` - 步骤1
- `POST /api/v1/validate/step2-api-doc` - 步骤2
- `POST /api/v1/validate/step3-preview-code` - 步骤3

---

## 配置示例

### 完整的ValidationRequest配置
```json
{
  "tableName": "user",
  "globalConfig": {
    "dbUrl": "jdbc:mysql://localhost:3306/mydb",
    "username": "root",
    "password": "password",
    "basePackage": "com.example.project",
    "serviceOutputDir": "/path/to/service",
    "interfaceOutputDir": "/path/to/interface",
    "consoleOutputDir": "/path/to/console",
    "renderConfig": {
      "authorName": "moryzang",
      "applicationName": "my-app",
      "primaryType": "BigInteger",
      "useLombok": true,
      "persistencePackage": "com.old.silence.data"
    },
    "enumConfigs": [
      {
        "tableName": "user",
        "columnName": "status",
        "enumName": "UserStatus",
        "generateEnum": true
      }
    ]
  },
  "customApiDoc": null
}
```

---

## 注意事项

1. **配置验证**: 所有接口都会验证数据库连接和输出目录配置
2. **异常处理**: 如果表不存在或配置不完整，会抛出详细的异常信息
3. **日志记录**: 每个步骤都有详细的日志记录，便于问题排查
4. **独立性**: 3个验证步骤完全独立，可以单独调用
5. **兼容性**: 新功能与现有的生成接口完全兼容，不影响原有功能

---

## 未来扩展

### 计划中的功能
- [ ] 自动修复缺失导入
- [ ] 代码格式化（基于Google Java Style或Alibaba Java Coding Guidelines）
- [ ] 字段排序（按类型、可见性、字母顺序）
- [ ] 方法排序（按可见性、字母顺序）
- [ ] 导入去重和优化
- [ ] 代码质量检查（命名规范、注释完整性）

### 可扩展点
- `ImportAnalyzer` 可以添加更多注解和类型识别
- `ValidationService` 可以添加更多验证规则
- `TableGeneratorConfig` 可以添加更多表级配置项

---

## 技术栈

- **Spring Boot 3.x** - REST API框架
- **FreeMarker 2.3.31** - 模板引擎
- **JDBC** - 数据库元数据读取
- **Java 17+** - 语言版本

---

## 反馈与贡献

如有问题或建议，请联系开发团队或提交Issue。
