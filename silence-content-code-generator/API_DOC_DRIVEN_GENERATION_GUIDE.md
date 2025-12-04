# ApiDoc驱动的代码生成指南

## 概述

本文档说明如何使用 `ApiDocument` 来驱动代码生成，实现按需生成而非固定模板生成。系统会根据 `ApiDocument` 中声明的 endpoints 来决定生成哪些代码文件。

## 设计理念

### 原有方式
之前的代码生成是**固定模板**驱动，无论API文档中定义了哪些接口，都会生成所有文件：
- Command、Query、View
- Service、Client、Mapper
- Resource、DAO、Repository
- Console层的所有文件

### 新方式
现在改为**ApiDocument驱动**，根据API文档中实际定义的endpoints来决定生成哪些文件：
- 有"创建"或"更新"接口 → 生成Command相关文件
- 有"分页查询"接口 → 生成Query相关文件
- 有查询类接口 → 生成View相关文件
- 没有任何CRUD接口 → 只生成基础的Model、DAO、Repository

## ApiDocument结构

### Endpoints定义

`ApiDocument` 中默认包含5个标准CRUD endpoints：

| Endpoint名称 | HTTP方法 | 路径 | 说明 |
|-------------|---------|------|------|
| 分页查询 | GET | /api/v1/{tableName} | 获取列表，支持分页 |
| 创建 | POST | /api/v1/{tableName} | 创建新记录 |
| 根据主键查询 | GET | /api/v1/{tableName}/{id} | 根据主键获取详情 |
| 更新 | PUT | /api/v1/{tableName}/{id} | 更新记录 |
| 删除 | DELETE | /api/v1/{tableName}/{id} | 删除记录 |

### 示例

```java
ApiDocument apiDoc = new ApiDocument();
apiDoc.setTableName("content");

Map<String, ApiEndpoint> endpoints = new LinkedHashMap<>();
endpoints.put("分页查询", createQueryEndpoint());
endpoints.put("创建", createCreateEndpoint());
endpoints.put("根据主键查询", createDetailEndpoint());
// 注意：这里没有"更新"和"删除"

apiDoc.setEndpoints(endpoints);
```

## 代码生成规则

### 1. Interface层（API接口定义）

| 文件类型 | 生成条件 | Endpoint依赖 |
|---------|---------|-------------|
| Command.java | 有创建或更新接口 | "创建" 或 "更新" |
| Query.java | 有分页查询接口 | "分页查询" |
| View.java | 有任何查询接口 | "根据主键查询" 或 "分页查询" |
| Service.java | 至少有一个endpoint | 任意endpoint |
| Client.java | 至少有一个endpoint | 任意endpoint |

**示例日志输出：**
```
检测到创建/更新接口，生成Command
检测到分页查询接口，生成Query
检测到查询接口，生成View
生成Service接口定义
生成Feign Client
```

### 2. Service层（业务逻辑实现）

| 文件类型 | 生成条件 | Endpoint依赖 |
|---------|---------|-------------|
| Model.java | 始终生成 | 无（基础数据模型） |
| Mapper.java | 有CRUD接口 | "创建"、"更新"、"查询"任意一个 |
| Dao.java | 始终生成 | 无（数据访问层） |
| Repository.java | 始终生成 | 无（仓储接口） |
| MyBatisRepository.java | 始终生成 | 无（仓储实现） |
| Resource.java | 至少有一个endpoint | 任意endpoint |

**示例日志输出：**
```
生成实体类Model
检测到CRUD接口，生成Mapper转换类
生成DAO层
生成Repository接口
生成Repository实现类
生成REST Resource
```

### 3. Console层（管理后台）

| 文件类型 | 生成条件 | Endpoint依赖 |
|---------|---------|-------------|
| ConsoleCommand.java | 有创建或更新接口 | "创建" 或 "更新" |
| ConsoleQuery.java | 有分页查询接口 | "分页查询" |
| ConsoleView.java | 有任何查询接口 | "根据主键查询" 或 "分页查询" |
| CommandMapper.java | 有创建或更新接口 | "创建" 或 "更新" |
| QueryMapper.java | 有分页查询接口 | "分页查询" |
| Resource.java | 至少有一个endpoint | 任意endpoint |

**示例日志输出：**
```
检测到创建/更新接口，生成ConsoleCommand
检测到分页查询接口，生成ConsoleQuery
检测到查询接口，生成ConsoleView
检测到创建/更新接口，生成CommandMapper
检测到分页查询接口，生成QueryMapper
生成Console Resource
```

### 4. Enum层（枚举类）

| 文件类型 | 生成条件 | 配置依赖 |
|---------|---------|---------|
| Enum.java | 有枚举配置 | GeneratorConfig.enumConfigs |

**生成条件：**
```java
if (hasEnumConfig(config, tableInfo.getTableName())) {
    generateEnumCode(codeGenerator, tableInfo, apiDoc, config);
}
```

## 使用场景

### 场景1：完整CRUD API

**ApiDocument配置：**
```java
endpoints.put("分页查询", ...);
endpoints.put("创建", ...);
endpoints.put("根据主键查询", ...);
endpoints.put("更新", ...);
endpoints.put("删除", ...);
```

**生成文件列表：**
- Interface层: Command, Query, View, Service, Client
- Service层: Model, Mapper, Dao, Repository, MyBatisRepository, Resource
- Console层: ConsoleCommand, ConsoleQuery, ConsoleView, CommandMapper, QueryMapper, Resource

### 场景2：只读API（仅查询）

**ApiDocument配置：**
```java
endpoints.put("分页查询", ...);
endpoints.put("根据主键查询", ...);
// 没有"创建"、"更新"、"删除"
```

**生成文件列表：**
- Interface层: Query, View, Service, Client（无Command）
- Service层: Model, Mapper, Dao, Repository, MyBatisRepository, Resource
- Console层: ConsoleQuery, ConsoleView, QueryMapper, Resource（无ConsoleCommand、CommandMapper）

### 场景3：只写API（仅创建/更新）

**ApiDocument配置：**
```java
endpoints.put("创建", ...);
endpoints.put("更新", ...);
// 没有查询相关接口
```

**生成文件列表：**
- Interface层: Command, Service, Client（无Query、View）
- Service层: Model, Mapper, Dao, Repository, MyBatisRepository, Resource
- Console层: ConsoleCommand, CommandMapper, Resource（无ConsoleQuery、ConsoleView、QueryMapper）

### 场景4：纯数据模型（无API）

**ApiDocument配置：**
```java
endpoints = new LinkedHashMap<>(); // 空的endpoints
```

**生成文件列表：**
- Interface层: 无（没有endpoint）
- Service层: Model, Dao, Repository, MyBatisRepository（只有数据访问层）
- Console层: 无（没有endpoint）

## 核心实现

### SpringCodeGeneratorService类

```java
@Service
public class SpringCodeGeneratorService {
    
    /**
     * 检查ApiDocument中是否存在指定的endpoint
     */
    private boolean hasEndpoint(Map<String, ApiEndpoint> endpoints, String endpointName) {
        if (endpoints == null || endpoints.isEmpty()) {
            return false;
        }
        return endpoints.containsKey(endpointName);
    }
    
    /**
     * 根据ApiDoc决定生成哪些Interface层文件
     */
    public void generateInterfaceCode(...) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        
        // 条件生成Command
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            codeGenerator.generateFile(..., "command.ftl", "Command");
        }
        
        // 条件生成Query
        if (hasEndpoint(endpoints, "分页查询")) {
            codeGenerator.generateFile(..., "query.ftl", "Query");
        }
        
        // 条件生成View
        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            codeGenerator.generateFile(..., "view.ftl", "View");
        }
    }
}
```

## 日志输出示例

### 完整CRUD场景
```
检测到创建/更新接口，生成Command
检测到分页查询接口，生成Query
检测到查询接口，生成View
生成Service接口定义
生成Feign Client
生成实体类Model
检测到CRUD接口，生成Mapper转换类
生成DAO层
生成Repository接口
生成Repository实现类
生成REST Resource
检测到创建/更新接口，生成ConsoleCommand
检测到分页查询接口，生成ConsoleQuery
检测到查询接口，生成ConsoleView
检测到创建/更新接口，生成CommandMapper
检测到分页查询接口，生成QueryMapper
生成Console Resource
```

### 只读API场景
```
检测到分页查询接口，生成Query
检测到查询接口，生成View
生成Service接口定义
生成Feign Client
生成实体类Model
检测到CRUD接口，生成Mapper转换类
生成DAO层
生成Repository接口
生成Repository实现类
生成REST Resource
检测到分页查询接口，生成ConsoleQuery
检测到查询接口，生成ConsoleView
检测到分页查询接口，生成QueryMapper
生成Console Resource
```

## 扩展建议

### 1. 自定义Endpoint

如果需要添加自定义的endpoint类型，可以：

```java
// 在ApiDocumentGeneratorService中添加
endpoints.put("批量删除", createBatchDeleteEndpoint(...));
endpoints.put("导出Excel", createExportEndpoint(...));
```

然后在 `SpringCodeGeneratorService` 中添加相应的生成逻辑：

```java
// 批量删除需要额外的BatchCommand
if (hasEndpoint(endpoints, "批量删除")) {
    codeGenerator.generateFile(..., "batchCommand.ftl", "BatchCommand");
}
```

### 2. 更细粒度的控制

可以在 `ApiEndpoint` 的 `metadata` 中添加额外的元数据：

```java
ApiEndpoint endpoint = new ApiEndpoint();
endpoint.setMethod("POST");
endpoint.setPath("/api/v1/users");

Map<String, Object> metadata = new HashMap<>();
metadata.put("generateDTO", true);      // 是否生成DTO
metadata.put("generateMapper", true);   // 是否生成Mapper
metadata.put("useCache", true);         // 是否使用缓存
endpoint.setMetadata(metadata);
```

### 3. 模板数据增强

在 `SpringCodeGenerator` 中可以将 `ApiDocument` 信息传递给模板：

```java
public void generateFile(...) {
    Map<String, Object> dataModel = createBaseDataModel(tableInfo);
    
    // 添加ApiDoc信息到模板
    dataModel.put("apiEndpoints", apiDoc.getEndpoints());
    dataModel.put("hasCreateEndpoint", apiDoc.getEndpoints().containsKey("创建"));
    dataModel.put("hasUpdateEndpoint", apiDoc.getEndpoints().containsKey("更新"));
    
    // 在模板中可以这样使用：
    // <#if hasCreateEndpoint>
    //     // 生成创建相关代码
    // </#if>
}
```

## 相关文件

- 核心服务: `SpringCodeGeneratorService.java`
- API文档生成: `ApiDocumentGeneratorService.java`
- 代码生成器: `SpringCodeGenerator.java`
- API文档模型: `ApiDocument.java`, `ApiEndpoint.java`

## 优势

1. **按需生成**：只生成实际需要的文件，避免冗余代码
2. **灵活可控**：通过修改ApiDocument来控制生成的文件
3. **易于扩展**：新增endpoint类型时只需修改判断逻辑
4. **日志清晰**：生成过程中的日志明确说明为什么生成某个文件
5. **向后兼容**：现有的模板文件无需修改

## 注意事项

1. **Endpoint名称固定**：当前依赖固定的endpoint名称（"创建"、"更新"等），如需修改需同步更新判断逻辑
2. **基础文件始终生成**：Model、DAO、Repository等基础文件会始终生成，因为它们是数据访问的基础
3. **依赖关系**：某些文件有依赖关系（如Mapper依赖Command/View），系统会自动处理这些依赖

## 变更历史

| 版本 | 日期 | 说明 |
|------|------|------|
| 1.0 | 2025-12-04 | 初始版本，实现ApiDoc驱动的代码生成 |
