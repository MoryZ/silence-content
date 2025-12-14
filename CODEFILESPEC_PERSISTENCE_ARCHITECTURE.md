# CodeFileSpec 完整持久化框架架构

## 概述

按照 `silence-content-service` 模块现有的完整数据库持久化框架，为 `CodeFileSpec` 构建了从数据库到 API 的完整调用链。

## 架构分层

### 1. **Entity 层** (数据库映射)
**文件**: `CodeFileSpecEntity.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/code/generator/entity/`

- `@Entity` 标注的 JPA 实体类
- 包含 15 个字段对应数据库列
- 包含审计字段 (createdDate, updatedDate, createdBy, updatedBy)
- 包含业务字段 (id, templateName, moduleType, packageSuffix, relativeDir, fileNameSuffix, fileTypeTag, generationCondition, endpointNames, displayName, description, version, enabled)
- 通过 Liquibase 迁移初始化数据库表结构

### 2. **DAO 层** (数据库操作)
**接口**: `CodeFileSpecDao.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/infrastructure/persistence/dao/`

继承 `JdbcRepository<CodeFileSpecEntity, Long>`，提供通用 CRUD + 自定义查询方法：
- `findById(id, projectionType)` - 主键查询
- `findByCriteria(criteria, pageable, projectionType)` - 条件查询
- `findByModuleTypeAndEnabledTrue(moduleType)` - 按模块类型查询
- `findByTemplateName(templateName)` - 按模板名称查询
- `findByModuleTypeAndGenerationConditionAndEnabledTrue(moduleType, condition)` - 条件组合查询
- `findAllEnabled()` - 查询所有启用的规格
- `existsByModuleTypeAndTemplateName(moduleType, templateName)` - 存在性检查
- `insert(entity)`, `update(entity)`, `deleteById(id)`, `deleteByIds(ids)` - CRUD 操作

### 3. **Repository 接口** (DDD 领域仓储)
**接口**: `CodeFileSpecRepository.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/domain/repository/`

定义仓储契约，对应 DAO 层的所有方法，业务代码依赖仓储接口而非 DAO：
```java
public interface CodeFileSpecRepository {
    <T> Optional<T> findById(Long id, Class<T> projectionType);
    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);
    List<CodeFileSpecEntity> findByModuleTypeAndEnabledTrue(String moduleType);
    // ... 其他查询方法
    int create(CodeFileSpecEntity entity);
    int update(CodeFileSpecEntity entity);
    int deleteById(Long id);
    int deleteByIds(List<Long> ids);
}
```

### 4. **Repository 实现** (MyBatis 实现)
**实现类**: `CodeFileSpecMyBatisRepository.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/infrastructure/persistence/`

- `@Repository` 标注
- 依赖注入 `CodeFileSpecDao`
- 实现 `CodeFileSpecRepository` 接口
- 所有方法都直接委托给 DAO 层

```java
@Repository
public class CodeFileSpecMyBatisRepository implements CodeFileSpecRepository {
    private final CodeFileSpecDao codeFileSpecDao;
    
    @Override
    public int create(CodeFileSpecEntity entity) {
        return codeFileSpecDao.insert(entity);
    }
    // ... 其他方法
}
```

### 5. **DTO 层** (数据传输对象)
**位置**: `silence-content-service-api/src/main/java/com/old/silence/content/api/dto/`

#### 5.1 创建/编辑命令: `CodeFileSpecCommand.java`
- 包含 `@NotBlank`, `@NotNull` 等校验注解
- 字段：templateName, moduleType, packageSuffix, relativeDir, fileNameSuffix, fileTypeTag, generationCondition, endpointNames, displayName, description, enabled
- 用于接收 API 创建/编辑请求

#### 5.2 查询条件: `CodeFileSpecQuery.java`
- 包含 `@RelationalQueryProperty` 注解（支持自动转换为数据库查询条件）
- 字段：templateName, moduleType, displayName, generationCondition, enabled
- 用于 API 查询请求参数

#### 5.3 返回视图: `CodeFileSpecView.java`
- 包含所有实体字段
- 用于 API 查询结果返回

### 6. **Mapper 层** (数据转换)
**接口**: `CodeFileSpecMapper.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/api/assembler/`

```java
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileSpecMapper extends Converter<CodeFileSpecCommand, CodeFileSpecEntity> {
}
```

使用 MapStruct 自动完成 Command → Entity 的数据转换

### 7. **Resource 层** (REST API)
**控制器**: `CodeFileSpecResource.java`
**位置**: `silence-content-service/src/main/java/com/old/silence/content/api/`

```java
@RestController
@RequestMapping("/api/v1")
public class CodeFileSpecResource {
    // 依赖注入
    private final CodeFileSpecRepository codeFileSpecRepository;
    private final CodeFileSpecMapper codeFileSpecMapper;
    
    // GET /api/v1/codeFileSpecs/{id} - 查询单条
    @GetMapping("/codeFileSpecs/{id}")
    public CodeFileSpecView findById(@PathVariable Long id) { ... }
    
    // GET /api/v1/codeFileSpecs?pageNo=1&pageSize=10 - 分页查询
    @GetMapping(value = "/codeFileSpecs", params = {"pageNo", "pageSize"})
    public Page<CodeFileSpecView> queryPage(CodeFileSpecQuery query, Pageable pageable) { ... }
    
    // POST /api/v1/codeFileSpecs - 创建
    @PostMapping("/codeFileSpecs")
    public Long create(@RequestBody CodeFileSpecCommand command) { ... }
    
    // PUT /api/v1/codeFileSpecs/{id} - 更新
    @PutMapping("/codeFileSpecs/{id}")
    public void update(@PathVariable Long id, @RequestBody CodeFileSpecCommand command) { ... }
    
    // DELETE /api/v1/codeFileSpecs/{id} - 删除
    @DeleteMapping("/codeFileSpecs/{id}")
    public void deleteById(@PathVariable Long id) { ... }
}
```

## 调用流程

### 创建规格流程
```
POST /api/v1/codeFileSpecs
  ↓ (CodeFileSpecCommand 反序列化)
CodeFileSpecResource.create()
  ↓ (MapStruct 转换)
CodeFileSpecMapper.convert(command) → CodeFileSpecEntity
  ↓ (委托仓储)
CodeFileSpecRepository.create(entity)
  ↓ (仓储委托)
CodeFileSpecMyBatisRepository.create(entity)
  ↓ (DAO 插入)
CodeFileSpecDao.insert(entity)
  ↓ (MyBatis 执行)
INSERT INTO cg_code_file_spec (...)
```

### 查询规格流程
```
GET /api/v1/codeFileSpecs/{id}
  ↓
CodeFileSpecResource.findById(id)
  ↓ (仓储查询)
CodeFileSpecRepository.findById(id, CodeFileSpecView.class)
  ↓ (仓储委托)
CodeFileSpecMyBatisRepository.findById(id, CodeFileSpecView.class)
  ↓ (DAO 查询)
CodeFileSpecDao.findById(id, CodeFileSpecView.class)
  ↓ (MyBatis 执行 + 结果映射)
SELECT * FROM cg_code_file_spec WHERE id = ?
  ↓ (返回视图)
CodeFileSpecView (JSON 序列化)
```

## 依赖关系

### 编译时依赖
```
Resource
  ↓ 依赖
Repository (接口) + Mapper (接口)
  ↓ 依赖
Entity + DTO
  ↓ 依赖
公共库 (validation, exception 等)
```

### 运行时依赖
```
Resource (请求入口)
  ↓ 注入
Repository 实现 + Mapper 实现
  ↓ 注入
DAO (数据库操作)
  ↓ 调用
MyBatis (SQL 执行)
  ↓ 操作
数据库
```

## 模块分布

| 组件 | 模块 | 路径 |
|------|------|------|
| Entity | silence-content-service | `code/generator/entity/` |
| DAO 接口 | silence-content-service | `infrastructure/persistence/dao/` |
| Repository 接口 | silence-content-service | `domain/repository/` |
| Repository 实现 | silence-content-service | `infrastructure/persistence/` |
| Mapper | silence-content-service | `api/assembler/` |
| Resource | silence-content-service | `api/` |
| DTO | silence-content-service-api | `api/dto/` |

## 与现有框架对齐

本架构完全遵循 `silence-content-service` 模块现有的数据库持久化框架：

1. **Entity 继承**: CodeGenModule 也继承 AbstractAutoGeneratedIdAuditable
2. **DAO 继承**: CodeGenModuleDao 也继承 JdbcRepository
3. **Repository 模式**: CodeGenModuleRepository 定义领域仓储接口
4. **Repository 实现**: CodeGenModuleMyBatisRepository 实现接口
5. **Mapper 使用**: CodeGenModuleMapper 使用 MapStruct
6. **Resource 层**: CodeGenModuleResource 同样的 REST API 设计
7. **DTO 设计**: Command/Query/View 三类 DTO

## 优势

### 1. **分层清晰**
- 职责分明：Entity 专注数据库映射，DAO 专注数据库操作，Repository 定义业务契约，Resource 暴露 API
- 易于测试：每一层都可以独立单元测试

### 2. **解耦合**
- 业务代码依赖 Repository 接口而非 DAO，易于替换实现
- 可轻松切换为 JPA、Hibernate 等实现

### 3. **类型安全**
- 投影类型支持：`findById(id, CodeFileSpecView.class)` 自动转换
- 条件查询安全：QueryCriteriaConverter 自动生成条件

### 4. **可扩展**
- 新增查询方法只需在 DAO 接口添加，实现类自动生成
- Mapper 接口极简，MapStruct 自动处理转换逻辑

### 5. **框架一致性**
- 与现有 CodeGenModule 等业务模块使用完全相同的持久化框架
- 新同学可以直接参考现有代码完成扩展

## 后续扩展

### 1. **数据库 Mapper XML**
在 resources 下创建 `mapper/CodeFileSpecMapper.xml`，配置 MyBatis SQL 映射：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper namespace="com.old.silence.content.infrastructure.persistence.dao.CodeFileSpecDao">
    <select id="findByTemplateName" resultType="com.old.silence.content.code.generator.entity.CodeFileSpecEntity">
        SELECT * FROM cg_code_file_spec WHERE template_name = #{templateName}
    </select>
    <!-- 其他 SQL 映射 -->
</mapper>
```

### 2. **动态加载规格**
创建 `CodeFileSpecLoader` 服务在应用启动时从数据库加载规格到内存：
```java
@Component
public class CodeFileSpecLoader {
    @PostConstruct
    public void loadFromDatabase() {
        List<CodeFileSpecEntity> specs = codeFileSpecRepository.findAllEnabled();
        CodeFileSpecRegistry.getInstance().merge(specs);
    }
}
```

### 3. **业务服务层**
创建 `CodeFileSpecService` 专注于业务逻辑：
```java
@Service
public class CodeFileSpecService {
    public List<CodeFileSpec> getSpecsByModule(String moduleType) {
        return codeFileSpecRepository.findByModuleTypeAndEnabledTrue(moduleType)
            .stream()
            .map(CodeFileSpec::fromEntity)
            .collect(toList());
    }
}
```

## 总结

CodeFileSpec 完整持久化框架包括：
- ✅ Entity 实体类（数据库映射）
- ✅ DAO 接口+实现（数据库操作）
- ✅ Repository 接口+实现（DDD 仓储）
- ✅ DTO 三类（Command/Query/View）
- ✅ Mapper 转换器（MapStruct）
- ✅ Resource REST API（HTTP 接口）
- ✅ Liquibase 迁移（数据库初始化）

整个架构与 `CodeGenModule` 等现有业务完全一致，易于维护和扩展。
