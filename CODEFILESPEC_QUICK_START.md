# CodeFileSpec 持久化框架 - 快速开始指南

**版本**: 1.0  
**更新时间**: 2025-12-12

---

## 🚀 5 分钟快速入门

### 1️⃣ 理解架构 (1 分钟)

```
REST API 请求 (HTTP)
    ↓
Resource 层 (接收 HTTP 请求)
    ↓
Mapper 层 (DTO → Entity)
    ↓
Repository 层 (业务操作接口)
    ↓
DAO 层 (MyBatis 数据库操作)
    ↓
数据库 (MySQL/PostgreSQL)
```

### 2️⃣ 快速查找代码 (1 分钟)

要找到特定的代码，使用这个对应关系：

| 想做什么 | 找这个文件 |
|---------|-----------|
| 暴露 HTTP API | **CodeFileSpecResource.java** (api/) |
| 定义数据库操作 | **CodeFileSpecDao.java** (infrastructure/persistence/dao/) |
| 定义业务契约 | **CodeFileSpecRepository.java** (domain/repository/) |
| 定义数据结构 | **CodeFileSpecEntity.java** (code/generator/entity/) |
| 定义请求数据 | **CodeFileSpecCommand.java** (api/dto/) |
| 定义查询条件 | **CodeFileSpecQuery.java** (api/dto/) |
| 定义返回数据 | **CodeFileSpecView.java** (api/dto/) |
| 数据转换规则 | **CodeFileSpecMapper.java** (api/assembler/) |

### 3️⃣ API 使用示例 (3 分钟)

#### 创建规格
```bash
curl -X POST http://localhost:8080/api/v1/codeFileSpecs \
  -H "Content-Type: application/json" \
  -d '{
    "templateName": "mytemplate.ftl",
    "moduleType": "SERVICE",
    "packageSuffix": ".my",
    "relativeDir": "my",
    "fileNameSuffix": "My",
    "fileTypeTag": "my",
    "generationCondition": "ALWAYS",
    "displayName": "我的模板",
    "enabled": true
  }'
```

#### 查询规格
```bash
# 按 ID 查询
curl http://localhost:8080/api/v1/codeFileSpecs/1

# 分页查询所有
curl "http://localhost:8080/api/v1/codeFileSpecs?pageNo=0&pageSize=10"

# 按模块类型过滤
curl "http://localhost:8080/api/v1/codeFileSpecs?moduleType=SERVICE&pageNo=0&pageSize=10"

# 按启用状态过滤
curl "http://localhost:8080/api/v1/codeFileSpecs?enabled=true&pageNo=0&pageSize=10"
```

#### 更新规格
```bash
curl -X PUT http://localhost:8080/api/v1/codeFileSpecs/1 \
  -H "Content-Type: application/json" \
  -d '{
    "displayName": "更新的名称",
    "enabled": true,
    "description": "这是更新后的描述",
    "templateName": "mytemplate.ftl",
    "moduleType": "SERVICE",
    "packageSuffix": ".my",
    "relativeDir": "my",
    "fileNameSuffix": "My",
    "fileTypeTag": "my",
    "generationCondition": "ALWAYS"
  }'
```

#### 删除规格
```bash
curl -X DELETE http://localhost:8080/api/v1/codeFileSpecs/1
```

---

## 📚 文件结构一览

### 核心类关系
```
CodeFileSpecCommand (输入)
    ↓ MapStruct
CodeFileSpecMapper
    ↓ 转换为
CodeFileSpecEntity (数据库)
    ↓ JdbcRepository
CodeFileSpecDao
    ↓ 委托给
CodeFileSpecRepository (接口)
    ↓ 实现
CodeFileSpecMyBatisRepository
    ↓ 返回
CodeFileSpecView (输出)
```

### 模块分布
```
silence-content-service-api/
└── api/dto/
    ├── CodeFileSpecCommand.java
    ├── CodeFileSpecQuery.java
    └── CodeFileSpecView.java

silence-content-service/
├── api/
│   ├── CodeFileSpecResource.java
│   └── assembler/
│       └── CodeFileSpecMapper.java
├── code/generator/entity/
│   └── CodeFileSpecEntity.java
├── domain/repository/
│   └── CodeFileSpecRepository.java
└── infrastructure/persistence/
    ├── CodeFileSpecMyBatisRepository.java
    └── dao/
        └── CodeFileSpecDao.java
```

---

## 🔍 常见操作

### 添加新的查询方法

**步骤 1**: 在 `CodeFileSpecDao` 添加方法
```java
public interface CodeFileSpecDao extends JdbcRepository<CodeFileSpecEntity, Long> {
    // 现有方法...
    
    // 新增：按创建人查询
    List<CodeFileSpecEntity> findByCreatedBy(String createdBy);
}
```

**步骤 2**: 在 `CodeFileSpecRepository` 接口添加
```java
public interface CodeFileSpecRepository {
    // 现有方法...
    
    List<CodeFileSpecEntity> findByCreatedBy(String createdBy);
}
```

**步骤 3**: 在 `CodeFileSpecMyBatisRepository` 实现
```java
@Override
public List<CodeFileSpecEntity> findByCreatedBy(String createdBy) {
    return codeFileSpecDao.findByCreatedBy(createdBy);
}
```

**步骤 4**: 在 `CodeFileSpecResource` 调用（如果要暴露 API）
```java
@GetMapping("/codeFileSpecs/createdBy/{createdBy}")
public List<CodeFileSpecEntity> findByCreatedBy(@PathVariable String createdBy) {
    return codeFileSpecRepository.findByCreatedBy(createdBy);
}
```

### 添加新的数据库字段

**步骤 1**: 在 `CodeFileSpecEntity` 添加字段
```java
@Column(name = "custom_field", length = 255)
private String customField;

public String getCustomField() { return customField; }
public void setCustomField(String customField) { this.customField = customField; }
```

**步骤 2**: 在 `CodeFileSpecCommand` 添加（如果需要创建/编辑）
```java
private String customField;

public String getCustomField() { return customField; }
public void setCustomField(String customField) { this.customField = customField; }
```

**步骤 3**: 在 `CodeFileSpecView` 添加（如果需要查询返回）
```java
private String customField;

public String getCustomField() { return customField; }
public void setCustomField(String customField) { this.customField = customField; }
```

**步骤 4**: 创建 Liquibase 迁移脚本
```xml
<changeSet id="add-custom-field" author="you">
    <addColumn tableName="cg_code_file_spec">
        <column name="custom_field" type="varchar(255)" remarks="自定义字段"/>
    </addColumn>
</changeSet>
```

**步骤 5**: MapStruct 自动处理转换（无需修改 CodeFileSpecMapper）

---

## 🧪 测试示例

### 单元测试 Repository
```java
@SpringBootTest
class CodeFileSpecRepositoryTest {
    @Autowired
    private CodeFileSpecRepository repository;
    
    @Test
    void testFindByTemplateName() {
        // Arrange
        CodeFileSpecEntity entity = new CodeFileSpecEntity();
        entity.setTemplateName("test.ftl");
        entity.setModuleType("SERVICE");
        // ... 设置其他必要字段
        
        // Act
        repository.create(entity);
        Optional<CodeFileSpecEntity> result = repository.findByTemplateName("test.ftl");
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("test.ftl", result.get().getTemplateName());
    }
}
```

### 集成测试 REST API
```java
@SpringBootTest
@AutoConfigureMockMvc
class CodeFileSpecResourceTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testCreate() throws Exception {
        String request = "{\"templateName\":\"test.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".test\",\"relativeDir\":\"test\",\"fileNameSuffix\":\"Test\",\"fileTypeTag\":\"test\",\"generationCondition\":\"ALWAYS\",\"enabled\":true}";
        
        mockMvc.perform(post("/api/v1/codeFileSpecs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }
}
```

---

## 📖 详细文档索引

| 文档 | 用途 |
|------|------|
| CODEFILESPEC_PERSISTENCE_ARCHITECTURE.md | 深入了解架构设计 |
| CODEFILESPEC_IMPLEMENTATION_SUMMARY.md | 了解实现细节和统计 |
| CODEFILESPEC_VS_CODEGENMODULE.md | 与现有框架对标 |
| CODEFILESPEC_COMPLETION_REPORT.md | 项目完成总结报告 |

---

## 🎯 常见问题

### Q1: 如何修改规格而不影响正在生成的代码？
**A**: 新增一个新的 CodeFileSpec 记录，保持旧的不删除。应用代码可以根据 version 字段区分。

### Q2: 如何实现规格的版本控制？
**A**: 使用 version 字段，每次更新时递增。在查询时指定 version。

### Q3: 如何缓存规格提高性能？
**A**: 在 Repository 实现或 Resource 层添加 @Cacheable 注解：
```java
@Cacheable(value = "codeFileSpecs", key = "#moduleType")
public List<CodeFileSpecEntity> findByModuleTypeAndEnabledTrue(String moduleType) {
    return codeFileSpecRepository.findByModuleTypeAndEnabledTrue(moduleType);
}
```

### Q4: 如何处理并发更新？
**A**: 使用 version 字段实现乐观锁，在 UPDATE 时检查 version。

### Q5: 如何与 CodeFileSpecRegistry 集成？
**A**: 创建一个 @PostConstruct 初始化方法，从数据库加载规格到 Registry：
```java
@Component
public class CodeFileSpecLoader {
    @PostConstruct
    public void loadSpecs() {
        List<CodeFileSpecEntity> specs = repository.findAllEnabled();
        CodeFileSpecRegistry.getInstance().merge(specs);
    }
}
```

---

## 🔧 故障排查

### 问题：编译错误 "Cannot resolve import..."
**解决**: 检查 pom.xml 中是否包含所有必需的依赖
```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
</dependency>
```

### 问题：数据库表不存在
**解决**: 运行 Liquibase 迁移
```bash
mvn liquibase:update
```

### 问题：REST API 返回 404
**解决**: 检查 @RequestMapping 路径是否正确，是否包含 /api/v1 前缀

### 问题：数据无法保存到数据库
**解决**: 
1. 检查所有 @NotNull 字段是否都有值
2. 检查唯一约束是否冲突 (moduleType + templateName)
3. 查看数据库日志了解具体错误

---

## 📋 检查清单

在使用 CodeFileSpec 前，确保：
- [ ] 数据库迁移已执行 (changelog-20251212-code-file-spec.xml)
- [ ] 所有依赖已添加到 pom.xml
- [ ] 应用可以连接到数据库
- [ ] 已配置 MyBatis 扫描路径
- [ ] 已配置 Spring Data JPA 自动检测

---

## 📞 寻求帮助

1. **查看现有框架** - 参考 CodeGenModule 的实现
2. **查看设计文档** - 读取 CODEFILESPEC_VS_CODEGENMODULE.md
3. **查看代码注释** - 每个类都有详细 JavaDoc
4. **查看测试代码** - 单元测试体现了使用方法

---

## 🎓 学习路径

### 入门 (30 分钟)
1. 读这个快速入门指南
2. 查看 REST API 端点列表
3. 用 curl 或 Postman 试试 API

### 初级 (2 小时)
1. 读 CODEFILESPEC_PERSISTENCE_ARCHITECTURE.md
2. 看源代码，理解分层结构
3. 了解 DTO、Entity、Repository 的关系

### 中级 (1 天)
1. 读 CODEFILESPEC_VS_CODEGENMODULE.md
2. 理解与现有框架的对齐
3. 学会添加新的查询方法
4. 学会添加新的数据库字段

### 高级 (2+ 天)
1. 读完整的项目完成报告
2. 研究如何集成到 CodeFileSpecRegistry
3. 计划迁移现有的硬编码规格
4. 实施性能优化

---

## 🚀 下一步

1. **运行应用**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

2. **验证表创建**
   ```sql
   SELECT * FROM cg_code_file_spec;
   ```

3. **测试 API**
   ```bash
   curl http://localhost:8080/api/v1/codeFileSpecs?pageNo=0&pageSize=10
   ```

4. **创建测试数据**
   ```bash
   curl -X POST http://localhost:8080/api/v1/codeFileSpecs \
     -H "Content-Type: application/json" \
     -d '{"templateName":"test.ftl","moduleType":"SERVICE",...}'
   ```

5. **整合到业务代码**
   - 修改 CodeFileSpecRegistry 支持 DB 加载
   - 修改 SpringCodeGeneratorService 使用 Registry
   - 逐步删除硬编码的规格定义

---

**祝你使用愉快! 🎉**

如有问题，查阅详细文档或参考现有 CodeGenModule 框架的实现。
