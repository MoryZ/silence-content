# SpringCodeGenerator 数据模型使用指南

## 概述
SpringCodeGenerator 为 FreeMarker 模板提供了完整的数据模型，用于生成各种代码文件。

## 数据模型分类详解

### 1. 文件类名相关
```freemarker
${className}          // User, Order, Product (大驼峰，用于类名)
${variableName}       // user, order, product (小驼峰，用于变量名)
```

**使用示例：**
```java
public class ${className}Command {
    private ${variableName}Id;
}
```

### 2. 文件引用其他文件名
直接通过 `${className}` 构建其他文件名：
```freemarker
${className}Command        // UserCommand
${className}Query          // UserQuery
${className}View           // UserView
${className}Service        // UserService
${className}Repository     // UserRepository
${className}Mapper         // UserMapper
${className}Dao            // UserDao
```

**使用示例：**
```java
import ${packageName}.${className}View;

public interface ${className}Service {
    ${className}View findById(BigInteger id);
}
```

### 3. API 路径名和 PathVariable 字段名
```freemarker
${apiName}           // users, orders, products (复数形式)
${apiPath}           // /users, /orders, /products
${primaryKeyField}   // id (主键字段名，用于PathVariable)
```

**使用示例：**
```java
@RestController
@RequestMapping("${apiPath}")
public class ${className}Resource {
    
    @GetMapping("/{${primaryKeyField}}")
    public ${className}View findById(@PathVariable BigInteger ${primaryKeyField}) {
        return service.findById(${primaryKeyField});
    }
}
```

### 4. 主包名相关
```freemarker
${basePackage}           // com.old.silence.content (基础包名)
${packageName}           // com.old.silence.content.api.dto (完整包名)
${persistencePackage}    // jakarta 或 javax (持久化注解包)
```

**使用示例：**
```java
package ${packageName};

import ${basePackage}.domain.model.${className};
import ${persistencePackage}.persistence.*;
```

### 5. 应用名（FeignClient contextId）
```freemarker
${contextId}             // user, order-item (表名下划线转横线)
${applicationName}       // silence-content-service
```

**使用示例：**
```java
@FeignClient(name = "${applicationName}", contextId = "${contextId}")
public interface ${className}Client {
    // ...
}
```

### 6. 主键信息
```freemarker
${primaryKeyType}        // BigInteger, Long, String
${primaryKeyField}       // id (Java字段名)
${primaryKeyColumn}      // id (数据库列名)
```

**使用示例：**
```java
public class ${className} {
    @Id
    @Column(name = "${primaryKeyColumn}")
    private ${primaryKeyType} ${primaryKeyField};
}
```

### 7. 各种文件的包名
包名通过 `generateFile` 方法参数传入：
```java
codeGenerator.generateFile(tableInfo, 
    basePackageDir + "/api/dto",           // 输出目录
    config.getBasePackage(),                // 基础包名
    ".api.dto",                             // 包名后缀
    "command.ftl",                          // 模板名
    "Command");                             // 类名后缀
```

### 8. 查询字段判断
```freemarker
<#if isQueryableField(column)>
    // 该字段适合作为查询条件
    private ${getJavaType(column)} ${column.fieldName};
</#if>
```

**排除规则：**
- 大文本字段（text, blob, json）
- 敏感字段（password, secret, token）
- 文件引用字段（reference, _ref, _url）

### 9. 枚举字段判断
```freemarker
<#if isEnumField(column)>
    @Enumerated(EnumType.STRING)
    private ${className}${toCamelCase(column.originalName, true)}Enum ${column.fieldName};
</#if>

${hasEnumField?string('true', 'false')}  // 表是否有枚举字段
```

**识别规则：**
- tinyint 类型
- 字段名包含：status, type, flag, state, category

### 10-12. 特殊类型检测（用于 import）
```freemarker
<#if hasInstantType>
import java.time.Instant;
</#if>

<#if hasBigDecimalType>
import java.math.BigDecimal;
</#if>

<#if hasBigIntegerType>
import java.math.BigInteger;
</#if>

<#if hasLocalDateType>
import java.time.LocalDate;
</#if>
```

### 13. 必填字段判断（字符串）
```freemarker
<#if hasNotBlank>
import ${persistencePackage}.validation.constraints.NotBlank;
</#if>

<#if hasSize>
import ${persistencePackage}.validation.constraints.Size;
</#if>

<#list columnInfos as column>
    <#if !column.nullable && getJavaType(column) == 'String'>
        @NotBlank(message = "${column.comment}不能为空")
        <#if column.length??>
        @Size(max = ${column.length})
        </#if>
    </#if>
</#list>
```

### 14. 必填字段判断（数字、枚举、布尔等）
```freemarker
<#if hasNotNull>
import ${persistencePackage}.validation.constraints.NotNull;
</#if>

<#list columnInfos as column>
    <#if !column.nullable && getJavaType(column) != 'String'>
        @NotNull(message = "${column.comment}不能为空")
    </#if>
</#list>
```

### 15. 集合类型判断
```freemarker
<#if hasCollectionType>
import java.util.List;
import java.util.Set;
</#if>
```

## FreeMarker 工具方法

### getJavaType(column)
将数据库类型转换为Java类型：
```freemarker
${getJavaType(column)}  // String, Long, BigDecimal, Instant等
```

### toCamelCase(string, capitalizeFirst)
转换为驼峰命名：
```freemarker
${toCamelCase("user_name", true)}   // UserName
${toCamelCase("user_name", false)}  // userName
```

### 类型判断方法
```freemarker
<#if isNumericType(column)>
    // 数字类型：Integer, Long, BigInteger, BigDecimal, Double, Float
</#if>

<#if isBooleanType(column)>
    // 布尔类型
</#if>

<#if isInstantType(column)>
    // Instant 类型
</#if>

<#if isStringType(column)>
    // String 类型
</#if>
```

## 完整模板示例

```freemarker
package ${packageName};

<#if hasInstantType>
import java.time.Instant;
</#if>
<#if hasBigDecimalType>
import java.math.BigDecimal;
</#if>
<#if hasBigIntegerType>
import java.math.BigInteger;
</#if>
<#if hasNotBlank>
import ${persistencePackage}.validation.constraints.NotBlank;
</#if>
<#if hasNotNull>
import ${persistencePackage}.validation.constraints.NotNull;
</#if>

/**
 * ${tableInfo.comment}命令对象
 *
 * @author ${authorName}
 */
public class ${className}Command {

<#list columnInfos as column>
    /**
     * ${column.comment}
     */
    <#if !column.nullable>
        <#if getJavaType(column) == 'String'>
    @NotBlank(message = "${column.comment}不能为空")
        <#else>
    @NotNull(message = "${column.comment}不能为空")
        </#if>
    </#if>
    <#if isEnumField(column)>
    @Enumerated(EnumType.STRING)
    </#if>
    private ${getJavaType(column)} ${column.fieldName};

</#list>

    // Getters and Setters
<#list columnInfos as column>
    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {
        return ${column.fieldName};
    }

    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${column.fieldName}) {
        this.${column.fieldName} = ${column.fieldName};
    }

</#list>
}
```

## 最佳实践

1. **类型检测优先级**：先检查 `has*Type`，再决定是否导入相关包
2. **空值判断**：使用 `!column.nullable` 判断字段是否必填
3. **字段过滤**：审计字段（id, created_date等）已自动过滤
4. **命名规范**：使用 `toCamelCase` 确保命名一致性
5. **条件判断**：使用 `isQueryableField` 过滤不适合查询的字段
