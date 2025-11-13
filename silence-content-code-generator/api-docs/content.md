# API接口文档

**数据表：** content

## 接口列表

### 1. 分页查询/api/v1/contents

**请求方法：** `GET`  
**接口描述：** 获取内容表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
| title | string | 否 | 根据标题过滤 | 示例标题 |
| status | number | 否 | 根据状态：1\-已保存，2\-发布中，3\-已发布过滤 | 1 |
| publishedAtStart | string | 否 | 发布时间开始时间（范围查询，UTC时间） | 2025\-11\-13T00:00:00Z |
| publishedAtEnd | string | 否 | 发布时间结束时间（范围查询，UTC时间） | 2025\-11\-13T23:59:59Z |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/contents?pageNo=1&pageSize=20&sort=-createdDate&title=示例标题&status=1&publishedAtStart=2025-11-13T00:00:00Z&publishedAtEnd=2025-11-13T23:59:59Z
Content-Type: application/json
```
</details>

#### 响应信息

**状态码：** 200  
**提示信息：** 获取成功列表  

<details open>
<summary>📄 响应示例（点击折叠）</summary>

```json
{
  "total" : 100,
  "data" : [ {
    "id" : 1,
    "title" : "示例标题",
    "subtitle" : "示例标题",
    "content" : "这是示例内容正文",
    "metadata" : {
      "key1" : "value1",
      "key2" : "value2"
    },
    "status" : 1,
    "price" : 99.99,
    "publishedAt" : "2025-11-13T11:16:48Z",
    "createdAt" : "2025-11-13T11:16:48Z",
    "published" : true,
    "description" : "这是一个示例描述信息",
    "author" : "张三"
  } ]
}
```
</details>

<details>
<summary>📄 分页响应示例（点击展开）</summary>

```json
{
  "code" : "200",
  "message" : "获取成功列表",
  "data" : {
    "total" : 100,
    "pageNo" : 1,
    "pageSize" : 20,
    "totalPages" : 5,
    "data" : [ {
      "total" : 100,
      "data" : [ {
        "id" : 1,
        "title" : "示例标题",
        "subtitle" : "示例标题",
        "content" : "这是示例内容正文",
        "metadata" : {
          "key1" : "value1",
          "key2" : "value2"
        },
        "status" : 1,
        "price" : 99.99,
        "publishedAt" : "2025-11-13T11:16:48Z",
        "createdAt" : "2025-11-13T11:16:48Z",
        "published" : true,
        "description" : "这是一个示例描述信息",
        "author" : "张三"
      } ]
    } ]
  }
}
```
</details>

### 2. 创建/api/v1/contents

**请求方法：** `POST`  
**接口描述：** 创建内容表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| title | string | 是 | 标题 | 示例标题 |
| subtitle | string | 否 | 副标题 | 示例标题 |
| content | string | 否 | 内容正文 | 这是示例内容正文 |
| metadata | string | 否 | 元数据（JSON格式） | {key1=value1, key2=value2} |
| status | number | 是 | 状态：1\-已保存，2\-发布中，3\-已发布 | 1 |
| price | number | 否 | 价格 | 99\.99 |
| publishedAt | string | 否 | 发布时间 | 2025\-11\-13T11:16:48Z |
| createdAt | string | 是 | 创建时间 | 2025\-11\-13T11:16:48Z |
| published | boolean | 是 | 是否已发布 | true |
| description | string | 否 | 描述信息 | 这是一个示例描述信息 |
| author | string | 否 | 作者 | 张三 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/contents
Content-Type: application/json

{
  "title" : "示例标题",
  "subtitle" : "示例标题",
  "content" : "这是示例内容正文",
  "metadata" : {
    "key1" : "value1",
    "key2" : "value2"
  },
  "status" : 1,
  "price" : 99.99,
  "publishedAt" : "2025-11-13T11:16:48Z",
  "createdAt" : "2025-11-13T11:16:48Z",
  "published" : true,
  "description" : "这是一个示例描述信息",
  "author" : "张三"
}
```
</details>

#### 响应信息

**状态码：** 200  
**提示信息：** 创建成功记录  

<details>
<summary>📄 响应示例（点击展开）</summary>

```json
null
```
</details>

### 3. 根据主键查询/api/v1/contents/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取内容表详情

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 内容ID | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/contents/{id}
Content-Type: application/json
```
</details>

#### 响应信息

**状态码：** 200  
**提示信息：** 根据主键获取成功详情  

<details open>
<summary>📄 响应示例（点击折叠）</summary>

```json
{
  "id" : 1,
  "title" : "示例标题",
  "subtitle" : "示例标题",
  "content" : "这是示例内容正文",
  "metadata" : {
    "key1" : "value1",
    "key2" : "value2"
  },
  "status" : 1,
  "price" : 99.99,
  "publishedAt" : "2025-11-13T11:16:48Z",
  "createdAt" : "2025-11-13T11:16:48Z",
  "published" : true,
  "description" : "这是一个示例描述信息",
  "author" : "张三"
}
```
</details>

### 4. 更新/api/v1/contents/{id}

**请求方法：** `PUT`  
**接口描述：** 更新内容表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| title | string | 是 | 标题 | 示例标题 |
| subtitle | string | 否 | 副标题 | 示例标题 |
| content | string | 否 | 内容正文 | 这是示例内容正文 |
| metadata | string | 否 | 元数据（JSON格式） | {key1=value1, key2=value2} |
| status | number | 是 | 状态：1\-已保存，2\-发布中，3\-已发布 | 1 |
| price | number | 否 | 价格 | 99\.99 |
| publishedAt | string | 否 | 发布时间 | 2025\-11\-13T11:16:48Z |
| createdAt | string | 是 | 创建时间 | 2025\-11\-13T11:16:48Z |
| published | boolean | 是 | 是否已发布 | true |
| description | string | 否 | 描述信息 | 这是一个示例描述信息 |
| author | string | 否 | 作者 | 张三 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/contents/{id}
Content-Type: application/json

{
  "title" : "示例标题",
  "subtitle" : "示例标题",
  "content" : "这是示例内容正文",
  "metadata" : {
    "key1" : "value1",
    "key2" : "value2"
  },
  "status" : 1,
  "price" : 99.99,
  "publishedAt" : "2025-11-13T11:16:48Z",
  "createdAt" : "2025-11-13T11:16:48Z",
  "published" : true,
  "description" : "这是一个示例描述信息",
  "author" : "张三"
}
```
</details>

#### 响应信息

**状态码：** 200  
**提示信息：** 更新成功记录  

<details>
<summary>📄 响应示例（点击展开）</summary>

```json
null
```
</details>

### 5. 删除/api/v1/contents/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除内容表记录

#### 请求参数

<details>
<summary>📋 参数列表（点击展开）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 内容ID | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
DELETE /api/v1/contents/{id}
Content-Type: application/json
```
</details>

#### 响应信息

**状态码：** 200  
**提示信息：** 删除成功记录  

<details>
<summary>📄 响应示例（点击展开）</summary>

```json
null
```
</details>

