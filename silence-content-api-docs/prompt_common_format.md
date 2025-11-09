# API接口文档

**数据表：** prompt_common_format

## 接口列表

### 1. 分页查询/api/v1/promptCommonFormats/{id}

**请求方法：** `GET`  
**接口描述：** 获取通用格式说明表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/promptCommonFormats/{id}?pageNo=1&pageSize=20&sort=-createdDate
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
    "formatName" : "字符串示例值",
    "formatContent" : "字符串示例值",
    "description" : "字符串示例值",
    "active" : true,
    "createdBy" : "字符串示例值",
    "createdDate" : "2024-01-01",
    "updatedBy" : "字符串示例值",
    "updatedDate" : "2024-01-01"
  } ]
}
```
</details>

### 2. 根据主键查询/api/v1/promptCommonFormats/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取通用格式说明表详情

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 记录ID | 示例值 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/promptCommonFormats/{id}
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
  "formatName" : "字符串示例值",
  "formatContent" : "字符串示例值",
  "description" : "字符串示例值",
  "active" : true,
  "createdBy" : "字符串示例值",
  "createdDate" : "2024-01-01",
  "updatedBy" : "字符串示例值",
  "updatedDate" : "2024-01-01"
}
```
</details>

### 3. 创建/api/v1/promptCommonFormats/{id}

**请求方法：** `POST`  
**接口描述：** 创建通用格式说明表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| formatName | string | 是 | 格式名称 | 字符串示例值 |
| formatContent | string | 是 | 格式内容 | 字符串示例值 |
| description | string | 否 | 格式说明 | 字符串示例值 |
| active | boolean | 是 | 是否启用 | true |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/promptCommonFormats/{id}
Content-Type: application/json

{
  "formatName" : "字符串示例值",
  "formatContent" : "字符串示例值",
  "description" : "字符串示例值",
  "active" : true
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

### 4. 更新/api/v1/promptCommonFormats/{id}

**请求方法：** `PUT`  
**接口描述：** 创建通用格式说明表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| formatName | string | 是 | 格式名称 | 字符串示例值 |
| formatContent | string | 是 | 格式内容 | 字符串示例值 |
| description | string | 否 | 格式说明 | 字符串示例值 |
| active | boolean | 是 | 是否启用 | true |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/promptCommonFormats/{id}
Content-Type: application/json

{
  "formatName" : "字符串示例值",
  "formatContent" : "字符串示例值",
  "description" : "字符串示例值",
  "active" : true
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

### 5. 删除/api/v1/promptCommonFormats/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除通用格式说明表记录

#### 请求参数

<details>
<summary>📋 参数列表（点击展开）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 记录ID | 示例值 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
DELETE /api/v1/promptCommonFormats/{id}
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

