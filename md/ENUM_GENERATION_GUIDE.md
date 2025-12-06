# 枚举生成使用指南

## 问题背景

从SQL字段判断是否需要生成枚举存在以下挑战：
1. `tinyint` 类型不一定都是枚举（可能是年龄、数量等）
2. 注释中可能不包含完整的枚举值信息
3. 多个表可能有相同字段名，枚举类命名容易冲突

## 解决方案

采用**两阶段流程**：智能识别 + 用户确认配置

### 阶段1：智能识别枚举字段

调用枚举检测接口，系统会自动分析表结构并给出建议：

```http
POST /api/v1/enum/detect
Content-Type: application/json

{
  "dbUrl": "jdbc:mysql://localhost:3306/silence-content",
  "username": "root",
  "password": "admin123456",
  "tableName": "user"  // 可选，不填则检测所有表
}
```

**响应示例：**

```json
{
  "suggestions": [
    {
      "tableName": "user",
      "columnName": "status",
      "dbType": "tinyint(1)",
      "comment": "状态：0-禁用 1-启用",
      "suggestedEnumClassName": "UserStatusEnum",
      "parsedValues": [
        {
          "code": "0",
          "name": "DISABLED",
          "description": "禁用"
        },
        {
          "code": "1",
          "name": "ENABLED",
          "description": "启用"
        }
      ],
      "strongSuggestion": true,
      "reason": "字段类型为 tinyint，通常用于存储枚举值；注释中包含 2 个枚举值定义；"
    },
    {
      "tableName": "order",
      "columnName": "type",
      "dbType": "tinyint(2)",
      "comment": "订单类型",
      "suggestedEnumClassName": "OrderTypeEnum",
      "parsedValues": [],
      "strongSuggestion": false,
      "reason": "字段类型为 tinyint，通常用于存储枚举值；字段注释包含状态/类型等枚举相关关键词；"
    }
  ]
}
```

### 阶段2：用户确认并配置

用户根据识别结果，在生成请求中添加枚举配置：

```http
POST /api/v1/generate/batch
Content-Type: application/json

{
  "tableNames": ["user", "order"],
  "config": {
    "dbUrl": "jdbc:mysql://localhost:3306/silence-content",
    "username": "root",
    "password": "admin123456",
    "basePackage": "com.old.silence.content",
    "serviceOutputDir": "silence-content-service/src/main/java",
    "interfaceOutputDir": "silence-content-service-api/src/main/java",
    "consoleOutputDir": "silence-content-console/src/main/java",
    "enumOutputDir": "silence-content-service-enums/src/main/java",
    
    "enumConfigs": [
      {
        "tableName": "user",
        "columnName": "status",
        "enumClassName": "UserStatusEnum",
        "generateEnum": true,
        "description": "用户状态",
        "values": [
          {
            "code": "0",
            "name": "DISABLED",
            "description": "禁用"
          },
          {
            "code": "1",
            "name": "ENABLED",
            "description": "启用"
          }
        ]
      },
      {
        "tableName": "order",
        "columnName": "type",
        "enumClassName": "OrderTypeEnum",
        "generateEnum": true,
        "description": "订单类型",
        "values": [
          {
            "code": "1",
            "name": "NORMAL",
            "description": "普通订单"
          },
          {
            "code": "2",
            "name": "PRESALE",
            "description": "预售订单"
          },
          {
            "code": "3",
            "name": "GROUP_BUY",
            "description": "团购订单"
          }
        ]
      },
      {
        "tableName": "user",
        "columnName": "age",
        "generateEnum": false,
        "description": "年龄，不生成枚举"
      }
    ]
  }
}
```

## 识别规则

系统会将以下字段识别为潜在枚举：

1. **类型判断**：`tinyint` 类型字段
2. **注释判断**：注释中包含枚举格式（如：`1-待处理 2-已处理`）
3. **关键词判断**：注释包含"状态"、"类型"、"标识"、"flag"等关键词

## 枚举类命名规则

- 单表字段：`{表名驼峰}{字段名驼峰}Enum`
  - 例如：`user.status` → `UserStatusEnum`
  
- 避免冗余：如果字段名已包含表业务含义，则简化
  - 例如：`user.user_status` → `UserStatusEnum`（不是 `UserUserStatusEnum`）

- 表名前缀处理：自动去除 `t_`、`tb_`、`sys_` 等前缀
  - 例如：`t_user.status` → `UserStatusEnum`

## 注释解析支持的格式

系统支持多种注释格式：

```
1:待处理 2:已处理
1-待处理 2-已处理
1.待处理 2.已处理
1=待处理 2=已处理
1：待处理，2：已处理
```

## 最佳实践

1. **先识别后生成**：使用 `/enum/detect` 接口获取建议，确认后再生成代码
2. **明确配置**：对于需要生成枚举的字段，明确配置所有枚举值
3. **灵活命名**：检测接口给出的类名只是建议，可以根据业务语义调整
4. **选择性生成**：不是所有 `tinyint` 都要生成枚举，通过 `generateEnum: false` 跳过

## 工作流程建议

```
1. 连接数据库
   ↓
2. 调用 /enum/detect 识别潜在枚举字段
   ↓
3. 前端展示识别结果，用户确认/编辑
   ↓
4. 用户补充缺失的枚举值信息
   ↓
5. 调用 /generate/batch 生成完整代码（包含枚举）
```

这样既保证了灵活性，又避免了完全依赖SQL语句的局限性。
