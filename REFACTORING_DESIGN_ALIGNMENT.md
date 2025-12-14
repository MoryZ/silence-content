# 代码生成框架设计对齐方案

## 📊 当前问题与解决方案

### 问题对比表

| 维度 | 当前状况 | 不足点 | 解决方案 |
|:---:|:---:|:---:|:---:|
| **模板文件名** | ✅ 在代码中写死 | 分散硬编码 | `CodeFileSpec.templateName` |
| **模块归属** | ✅ `ModuleType` 枚举 | 不完整 | `CodeFileSpec.moduleType` |
| **包名** | ❌ 硬编码字符串 | 24+处硬编码 | `CodeFileSpec.packageSuffix` |
| **目录路径** | ❌ 硬编码字符串 | 与包名一致导致重复 | `CodeFileSpec.relativeDir` |
| **文件名后缀** | ❌ 硬编码字符串 | 与端点条件混合 | `CodeFileSpec.fileNameSuffix` |

---

## 🔧 建议的数据库建模方案

### 现有表结构分析
根据 `changelog-20251212-code-generator.xml`，存在以下表：
- `cg_code_generation_history` - 生成历史（包含 `generation_metadata` JSON字段）
- 缺少：模板规格配置表

### 建议增加的数据库表

#### 1. `cg_code_file_spec` 表
存储所有代码文件规格配置，替代硬编码：

```sql
CREATE TABLE cg_code_file_spec (
    id BIGINT UNSIGNED PRIMARY KEY,
    
    -- 基础信息
    template_name VARCHAR(100) NOT NULL UNIQUE,
    module_type VARCHAR(50) NOT NULL,
    
    -- 路径和包名配置
    package_suffix VARCHAR(200) NOT NULL,      -- 如 ".api.dto"
    relative_dir VARCHAR(200) NOT NULL,        -- 如 "api/dto"
    file_name_suffix VARCHAR(100) NOT NULL,    -- 如 "Command"
    file_type_tag VARCHAR(50) NOT NULL,        -- 如 "dto"，用于预览分组
    
    -- 生成条件
    generation_condition VARCHAR(50) NOT NULL,  -- 如 "ALWAYS", "HAS_ANY_ENDPOINT", "HAS_CREATE_UPDATE"
    endpoint_names VARCHAR(500),                -- JSON 数组，如 ["创建", "更新"]
    
    -- 元数据
    display_name VARCHAR(100),
    description TEXT,
    version INT DEFAULT 1,
    
    -- 审计字段
    created_by VARCHAR(100),
    created_date TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP,
    
    UNIQUE KEY uk_module_template (module_type, template_name),
    INDEX idx_module_type (module_type)
);
```

#### 2. `cg_module_config` 表（可选扩展）
存储不同模块的默认配置：

```sql
CREATE TABLE cg_module_config (
    id BIGINT UNSIGNED PRIMARY KEY,
    
    -- 模块基础信息
    module_type VARCHAR(50) NOT NULL UNIQUE,
    module_name VARCHAR(100) NOT NULL,
    
    -- 默认配置
    default_base_package VARCHAR(200),
    default_out_directory VARCHAR(200),
    
    -- 是否启用此模块的生成
    enabled BOOLEAN DEFAULT TRUE,
    
    -- 审计字段
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_module_type (module_type)
);
```

---

## 🏗️ 重构后的代码结构

### 代码层级关系

```
CodeFileSpec (单个文件规格元数据)
    ↓
CodeFileSpecRegistry (规格注册表)
    ↓
RefactoredCodeGeneratorService (统一生成服务)
    ├── generateCode() (生成所有文件)
    └── previewCode() (预览所有文件)
```

### 核心改进

#### Before: 重复且硬编码
```java
// generateInterfaceCode 方法
if (hasEndpoint(endpoints, ApiEndpointNames.CREATE) || ...) {
    codeGenerator.generateFile(tableInfo, outDirectory + "/api/dto",
        config.getBasePackage(), ".api.dto", "command.ftl", "Command");
}

// previewInterfaceCode 方法 (几乎完全相同)
if (hasEndpoint(endpoints, ApiEndpointNames.CREATE) || ...) {
    String content = codeGenerator.renderTemplate(tableInfo,
        config.getBasePackage(), ".api.dto", "command.ftl");
    response.addFile("interface", className + "Command", "api/dto/...", content, "dto");
}
```

#### After: 元数据驱动
```java
// 一次性配置规格
CodeFileSpec.builder()
    .moduleType(ModuleType.SERVICE_API)
    .templateName("command.ftl")
    .packageSuffix(".api.dto")
    .relativeDir("api/dto")
    .fileNameSuffix("Command")
    .fileTypeTag("dto")
    .whenHasEndpoint(ApiEndpointNames.CREATE, ApiEndpointNames.UPDATE)
    .build();

// 生成和预览使用同一套逻辑
for (CodeFileSpec spec : specs) {
    // 同一份代码既能生成，也能预览
    generateFileBySpec(codeGenerator, tableInfo, config, baseOutputDir, spec);
    previewFileBySpec(codeGenerator, tableInfo, config, className, spec, response);
}
```

---

## 🔄 迁移方案

### 第一阶段：新增模型和注册表
✅ 已完成
- `CodeFileSpec` - 文件规格模型
- `CodeFileSpecRegistry` - 规格注册表（内存管理）
- `RefactoredCodeGeneratorService` - 重构后的服务

### 第二阶段：持久化配置（可选）
需要做的：
1. 创建数据库表 `cg_code_file_spec`
2. 创建 Entity 类 `CodeFileSpecEntity`
3. 创建 Repository 接口
4. 修改 Registry 以支持从数据库加载配置

### 第三阶段：逐步迁移现有代码
1. 修改 `SpringCodeGeneratorService` 使用新架构
2. 删除所有硬编码
3. 运行测试验证

---

## 📋 数据库字段覆盖情况

### 五大要素映射

| 要素 | 数据库字段 | CodeFileSpec | 示例值 |
|:---:|:---:|:---:|:---:|
| 1️⃣ 模板文件名 | `template_name` | `templateName` | `"command.ftl"` |
| 2️⃣ 模块归属 | `module_type` | `moduleType` | `ModuleType.SERVICE_API` |
| 3️⃣ 包名 | `package_suffix` | `packageSuffix` | `".api.dto"` |
| 4️⃣ 目录名 | `relative_dir` | `relativeDir` | `"api/dto"` |
| 5️⃣ 文件名后缀 | `file_name_suffix` | `fileNameSuffix` | `"Command"` |
| 🔧 生成条件 | `generation_condition` + `endpoint_names` | `generateCondition` | `"HAS_CREATE_UPDATE"` |

---

## ✅ 检查清单

### 设计完成度

- [x] **CodeFileSpec** - 元数据模型完整覆盖5大要素
- [x] **CodeFileSpecRegistry** - 所有现有规格注册完整
- [x] **RefactoredCodeGeneratorService** - 统一生成和预览逻辑
- [ ] **数据库表设计** - 用于持久化配置（待确认是否需要）
- [ ] **现有代码迁移** - 逐步替换 SpringCodeGeneratorService
- [ ] **单元测试** - 验证重构后的行为一致

---

## 🎯 对齐建议

### 立即行动
1. 确认是否需要将规格配置持久化到数据库
2. 如需持久化，创建对应的 Entity 和 Repository
3. 创建从数据库加载规格的 Loader 类

### 代码级别
1. 修改 `CodeFileSpecRegistry.getAllSpecs()` 支持从数据库加载
2. 逐步用 `RefactoredCodeGeneratorService` 替换现有的四个 generate 方法
3. 更新所有调用处，验证行为一致

### 优化方向
1. 考虑通过 Spring 配置类使 Registry 变成 Spring Bean
2. 支持动态注册新的文件规格（无需重新编译）
3. 添加规格版本管理，支持多版本并行
