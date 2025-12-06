# SQL解析器使用说明

## 一、概述

已实现基于JSqlParser的SQL解析器，支持解析CREATE TABLE语句，提取表结构信息并转换为TableInfo对象。

## 二、功能特性

✅ **CREATE TABLE解析**：完整解析CREATE TABLE语句
✅ **字段信息提取**：字段名、类型、长度、是否可空、默认值、注释等
✅ **主键识别**：自动识别主键字段
✅ **索引解析**：解析普通索引和唯一索引
✅ **外键解析**：解析外键约束（部分支持）
✅ **文件解析**：支持从SQL文件批量解析多个表
✅ **SQL验证**：验证SQL语句是否有效
✅ **表名提取**：从SQL语句中提取表名

## 三、使用示例

### 3.1 基本使用

```java
@Autowired
private SQLParser sqlParser;

public void example() {
    String sql = """
        CREATE TABLE `user` (
            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
            `username` VARCHAR(50) NOT NULL COMMENT '用户名',
            `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
            `status` TINYINT DEFAULT 1 COMMENT '状态',
            PRIMARY KEY (`id`),
            UNIQUE KEY `uk_username` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
        """;
    
    TableInfo tableInfo = sqlParser.parseCreateTable(sql);
    
    // 使用tableInfo进行后续处理
    System.out.println("表名: " + tableInfo.getTableName());
    System.out.println("字段数: " + tableInfo.getColumnInfos().size());
}
```

### 3.2 从文件解析

```java
@Autowired
private SQLParser sqlParser;

public void parseFromFile() {
    String filePath = "db/silence-content.sql";
    List<TableInfo> tableInfos = sqlParser.parseFromFile(filePath);
    
    for (TableInfo tableInfo : tableInfos) {
        System.out.println("表: " + tableInfo.getTableName());
    }
}
```

### 3.3 验证SQL

```java
@Autowired
private SQLParser sqlParser;

public void validateSQL() {
    String sql = "CREATE TABLE test (id INT PRIMARY KEY)";
    boolean isValid = sqlParser.isValidSQL(sql);
    System.out.println("SQL是否有效: " + isValid);
}
```

### 3.4 提取表名

```java
@Autowired
private SQLParser sqlParser;

public void extractTableName() {
    String sql = "CREATE TABLE `user` (id INT)";
    String tableName = sqlParser.extractTableName(sql);
    System.out.println("表名: " + tableName); // 输出: user
}
```

## 四、支持的SQL特性

### 4.1 数据类型

支持常见的数据类型：
- 整数类型：INT, BIGINT, TINYINT, SMALLINT, MEDIUMINT
- 字符串类型：VARCHAR, CHAR, TEXT
- 日期时间：DATETIME, DATE, TIMESTAMP, TIME
- 数值类型：DECIMAL, FLOAT, DOUBLE
- 布尔类型：BOOLEAN, BIT

### 4.2 列约束

- ✅ NOT NULL / NULL
- ✅ AUTO_INCREMENT
- ✅ DEFAULT值
- ✅ COMMENT注释
- ✅ PRIMARY KEY（列级别）

### 4.3 表级约束

- ✅ PRIMARY KEY（表级别）
- ✅ UNIQUE KEY
- ✅ INDEX / KEY
- ⚠️ FOREIGN KEY（部分支持）

### 4.4 表选项

- ✅ ENGINE
- ✅ CHARSET
- ✅ COMMENT（表注释，需要从原始SQL提取）

## 五、解析结果说明

### 5.1 TableInfo对象

解析后的TableInfo包含以下信息：

```java
TableInfo {
    tableName: "user"                    // 表名
    schema: "database_name"              // 数据库名（如果有）
    comment: "用户表"                    // 表注释
    columnInfos: [                       // 字段列表
        ColumnInfo {
            originalName: "id"
            fieldName: "id"              // 驼峰命名
            type: "BIGINT"
            length: null
            nullable: false
            required: true
            autoIncrement: true
            comment: "用户ID"
            isPrimaryKey: true
        },
        ...
    ]
    primaryKeys: ["id"]                  // 主键列表
    indexes: [                           // 索引列表
        IndexInfo {
            indexName: "uk_username"
            unique: true
            columnNames: ["username"]
        }
    ]
    foreignKeys: []                      // 外键列表
}
```

### 5.2 ColumnInfo对象

每个字段包含的完整信息：

- `originalName`: 数据库字段名（原始名称）
- `fieldName`: Java字段名（驼峰命名）
- `type`: 数据类型（如VARCHAR, BIGINT等）
- `length`: 字段长度（如VARCHAR(50)中的50）
- `nullable`: 是否可为空
- `required`: 是否必填（!nullable）
- `autoIncrement`: 是否自增
- `defaultValue`: 默认值
- `comment`: 字段注释
- `isPrimaryKey`: 是否为主键

## 六、集成到代码生成流程

SQL解析器已集成到代码生成流程中，可以在GeneratorResource中使用：

```java
@RestController
@RequestMapping("/api/v1")
public class GeneratorResource {
    
    @Autowired
    private SQLParser sqlParser;
    
    @PostMapping("/generate/from-sql")
    public String generateFromSQL(@RequestBody String sql) {
        // 1. 解析SQL
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);
        
        // 2. 生成API文档
        ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);
        
        // 3. 生成代码
        // ... 后续代码生成逻辑
    }
}
```

## 七、注意事项

1. **表注释提取**：JSqlParser可能不直接支持表注释提取，目前返回null，可以通过正则表达式从原始SQL中提取

2. **外键支持**：外键解析功能有限，部分外键信息可能无法完全提取

3. **复杂SQL**：对于非常复杂的CREATE TABLE语句（如包含存储过程、触发器定义等），可能需要特殊处理

4. **SQL方言**：主要支持MySQL语法，其他数据库的SQL可能需要调整

## 八、后续优化方向

1. ✅ 完善表注释提取（使用正则表达式）
2. ✅ 增强外键解析能力
3. ✅ 支持ALTER TABLE语句的完整解析
4. ✅ 支持更多数据库方言（PostgreSQL、Oracle等）
5. ✅ 错误处理和提示优化

## 九、依赖说明

已在`pom.xml`中添加JSqlParser依赖：

```xml
<dependency>
    <groupId>com.github.jsqlparser</groupId>
    <artifactId>jsqlparser</artifactId>
    <version>4.7</version>
</dependency>
```

## 十、测试建议

建议编写单元测试验证解析功能：

```java
@SpringBootTest
class SQLParserTest {
    
    @Autowired
    private SQLParser sqlParser;
    
    @Test
    void testParseCreateTable() {
        String sql = "CREATE TABLE test (id INT PRIMARY KEY)";
        TableInfo tableInfo = sqlParser.parseCreateTable(sql);
        assertNotNull(tableInfo);
        assertEquals("test", tableInfo.getTableName());
    }
}
```

---

**实现完成时间**：2024年
**实现方式**：JSqlParser 4.7
**状态**：✅ 已完成基础功能

