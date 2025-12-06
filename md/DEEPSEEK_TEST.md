# DeepSeek 代码生成测试指南

## 一、配置 DeepSeek API Key

### 方式1：环境变量（推荐）
```bash
export DEEPSEEK_API_KEY=your-deepseek-api-key
```

### 方式2：配置文件
在 `bootstrap.yml` 中配置：
```yaml
code-generator:
  llm:
    provider: DEEPSEEK
    deepseek:
      api-key: your-deepseek-api-key
      base-url: https://api.deepseek.com
      model: deepseek-coder
```

## 二、测试接口说明

### 1. 检查配置状态
```bash
GET http://localhost:8081/api/v1/test/deepseek/config
```

**响应示例：**
```json
{
  "configured": true,
  "apiKeySet": true,
  "provider": "DEEPSEEK",
  "baseUrl": "https://api.deepseek.com",
  "model": "deepseek-coder",
  "message": "DeepSeek已配置，可以开始测试"
}
```

### 2. 测试提示词构建
```bash
POST http://localhost:8081/api/v1/test/deepseek/test-prompt
Content-Type: application/json

{
  "sql": "CREATE TABLE `content` (`id` bigint PRIMARY KEY, `title` varchar(100), `content` text) ENGINE=InnoDB COMMENT='内容表';",
  "layer": "SERVICE"
}
```

**响应示例：**
```json
{
  "success": true,
  "prompt": "你是一个经验丰富的Java开发工程师...",
  "promptLength": 1234,
  "layer": "SERVICE",
  "tableName": "content"
}
```

### 3. 直接生成代码
```bash
POST http://localhost:8081/api/v1/test/deepseek/generate-code
Content-Type: application/json

{
  "sql": "CREATE TABLE `content` (`id` bigint PRIMARY KEY, `title` varchar(100), `content` text) ENGINE=InnoDB COMMENT='内容表';",
  "layer": "SERVICE"
}
```

**支持的层级：**
- `SERVICE` - Service层代码
- `SERVICE_API` - Service-API层代码
- `CONSOLE` - Console层代码
- `ENUM` - 枚举类
- `FRONTEND` - 前端代码

**响应示例：**
```json
{
  "success": true,
  "message": "代码生成成功（请查看日志获取生成的代码）",
  "layer": "SERVICE",
  "tableName": "content",
  "promptLength": 1234
}
```

### 4. 完整流程测试（从SQL到代码）
```bash
POST http://localhost:8081/api/v1/test/deepseek/generate-from-sql
Content-Type: application/json

{
  "sql": "CREATE TABLE `content` (`id` bigint PRIMARY KEY, `title` varchar(100), `content` text) ENGINE=InnoDB COMMENT='内容表';",
  "tableName": "content",
  "basePackage": "com.old.silence.content"
}
```

**响应示例：**
```json
{
  "success": true,
  "message": "代码生成成功",
  "tableInfo": {
    "tableName": "content",
    "comment": "内容表",
    "columnCount": 3
  },
  "apiDoc": {
    "endpointCount": 5
  },
  "generationResult": {
    "success": true,
    "message": "从SQL生成成功"
  }
}
```

## 三、使用 curl 测试

### 测试配置
```bash
curl -X GET http://localhost:8081/api/v1/test/deepseek/config
```

### 测试提示词
```bash
curl -X POST http://localhost:8081/api/v1/test/deepseek/test-prompt \
  -H "Content-Type: application/json" \
  -d '{
    "sql": "CREATE TABLE `content` (`id` bigint PRIMARY KEY, `title` varchar(100)) ENGINE=InnoDB;",
    "layer": "SERVICE"
  }'
```

### 生成代码
```bash
curl -X POST http://localhost:8081/api/v1/test/deepseek/generate-code \
  -H "Content-Type: application/json" \
  -d '{
    "sql": "CREATE TABLE `content` (`id` bigint PRIMARY KEY, `title` varchar(100)) ENGINE=InnoDB;",
    "layer": "SERVICE"
  }'
```

## 四、查看生成的代码

生成的代码会在日志中输出，格式如下：
```
INFO  - 大模型生成的代码: 1234 字符
INFO  - 格式化后的代码长度: 1200 字符
```

完整的代码内容会在日志中显示，可以复制使用。

## 五、常见问题

### 1. API Key 未配置
**错误信息：** `DeepSeek未配置API Key，返回回退内容`

**解决方法：**
- 设置环境变量 `DEEPSEEK_API_KEY`
- 或在配置文件中配置 `code-generator.llm.deepseek.api-key`

### 2. 网络连接失败
**错误信息：** `调用DeepSeek失败: Connection refused`

**解决方法：**
- 检查网络连接
- 确认 DeepSeek API 地址正确：`https://api.deepseek.com`

### 3. 模型调用失败
**错误信息：** `DeepSeek返回非预期状态码: 401`

**解决方法：**
- 检查 API Key 是否正确
- 确认 API Key 有足够的余额

## 六、获取 DeepSeek API Key

1. 访问 [DeepSeek 官网](https://platform.deepseek.com/)
2. 注册/登录账号
3. 在控制台创建 API Key
4. 复制 API Key 并配置到环境变量或配置文件中

## 七、测试示例 SQL

### 简单表结构
```sql
CREATE TABLE `content` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-已保存，2-发布中，3-已发布',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB COMMENT='内容表';
```

### 复杂表结构
```sql
CREATE TABLE `book` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '书名',
  `author` varchar(100) COMMENT '作者',
  `isbn` varchar(20) UNIQUE COMMENT 'ISBN',
  `price` decimal(10,2) COMMENT '价格',
  `published_at` datetime COMMENT '出版时间',
  `is_published` bit NOT NULL DEFAULT 0 COMMENT '是否发布',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='图书表';
```

