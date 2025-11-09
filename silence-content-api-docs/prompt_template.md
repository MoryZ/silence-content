# API接口文档

**数据表：** prompt_template

## 接口列表

### 1. 分页查询/api/v1/promptTemplates/{id}

**请求方法：** `GET`  
**接口描述：** 获取AI提示词模板表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
| templateCode | string | 否 | 根据模板编码（唯一标识）过滤 | 字符串示例值 |
| subCategoryId | number | 否 | 根据关联的子分类ID（为空表示通用模板）过滤 | 1 |
| active | boolean | 否 | 根据是否启用过滤 | true |
| sortOrder | number | 否 | 根据排序权重（越大越靠前）过滤 | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/promptTemplates/{id}?pageNo=1&pageSize=20&sort=-createdDate&templateCode=字符串示例值&subCategoryId=1&active=true&sortOrder=1
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
    "templateName" : "字符串示例值",
    "templateCode" : "字符串示例值",
    "subCategoryId" : 1,
    "templateContent" : "字符串示例值",
    "variableDefinitions" : "字符串示例值",
    "exampleOutput" : "字符串示例值",
    "difficultySettings" : "字符串示例值",
    "questionTypes" : "字符串示例值",
    "version" : "字符串示例值",
    "active" : true,
    "sortOrder" : 1,
    "createdBy" : "字符串示例值",
    "createdDate" : "2024-01-01",
    "updatedBy" : "字符串示例值",
    "updatedDate" : "2024-01-01"
  } ]
}
```
</details>

### 2. 根据主键查询/api/v1/promptTemplates/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取AI提示词模板表详情

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
GET /api/v1/promptTemplates/{id}
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
  "templateName" : "字符串示例值",
  "templateCode" : "字符串示例值",
  "subCategoryId" : 1,
  "templateContent" : "字符串示例值",
  "variableDefinitions" : "字符串示例值",
  "exampleOutput" : "字符串示例值",
  "difficultySettings" : "字符串示例值",
  "questionTypes" : "字符串示例值",
  "version" : "字符串示例值",
  "active" : true,
  "sortOrder" : 1,
  "createdBy" : "字符串示例值",
  "createdDate" : "2024-01-01",
  "updatedBy" : "字符串示例值",
  "updatedDate" : "2024-01-01"
}
```
</details>

### 3. 创建/api/v1/promptTemplates/{id}

**请求方法：** `POST`  
**接口描述：** 创建AI提示词模板表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| templateName | string | 是 | 模板名称 | 字符串示例值 |
| templateCode | string | 是 | 模板编码（唯一标识） | 字符串示例值 |
| subCategoryId | number | 否 | 关联的子分类ID（为空表示通用模板） | 1 |
| templateContent | string | 是 | 提示词模板内容 | 字符串示例值 |
| variableDefinitions | string | 否 | 变量定义（JSON格式） | 字符串示例值 |
| exampleOutput | string | 否 | 示例输出（JSON格式） | 字符串示例值 |
| difficultySettings | string | 否 | 难度设置（JSON格式） | 字符串示例值 |
| questionTypes | string | 否 | 支持的题型（JSON数组） | 字符串示例值 |
| version | string | 是 | 模板版本 | 字符串示例值 |
| active | boolean | 是 | 是否启用 | true |
| sortOrder | number | 否 | 排序权重（越大越靠前） | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/promptTemplates/{id}
Content-Type: application/json

{
  "templateName" : "字符串示例值",
  "templateCode" : "字符串示例值",
  "subCategoryId" : 1,
  "templateContent" : "字符串示例值",
  "variableDefinitions" : "字符串示例值",
  "exampleOutput" : "字符串示例值",
  "difficultySettings" : "字符串示例值",
  "questionTypes" : "字符串示例值",
  "version" : "字符串示例值",
  "active" : true,
  "sortOrder" : 1
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

### 4. 更新/api/v1/promptTemplates/{id}

**请求方法：** `PUT`  
**接口描述：** 创建AI提示词模板表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| templateName | string | 是 | 模板名称 | 字符串示例值 |
| templateCode | string | 是 | 模板编码（唯一标识） | 字符串示例值 |
| subCategoryId | number | 否 | 关联的子分类ID（为空表示通用模板） | 1 |
| templateContent | string | 是 | 提示词模板内容 | 字符串示例值 |
| variableDefinitions | string | 否 | 变量定义（JSON格式） | 字符串示例值 |
| exampleOutput | string | 否 | 示例输出（JSON格式） | 字符串示例值 |
| difficultySettings | string | 否 | 难度设置（JSON格式） | 字符串示例值 |
| questionTypes | string | 否 | 支持的题型（JSON数组） | 字符串示例值 |
| version | string | 是 | 模板版本 | 字符串示例值 |
| active | boolean | 是 | 是否启用 | true |
| sortOrder | number | 否 | 排序权重（越大越靠前） | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/promptTemplates/{id}
Content-Type: application/json

{
  "templateName" : "字符串示例值",
  "templateCode" : "字符串示例值",
  "subCategoryId" : 1,
  "templateContent" : "字符串示例值",
  "variableDefinitions" : "字符串示例值",
  "exampleOutput" : "字符串示例值",
  "difficultySettings" : "字符串示例值",
  "questionTypes" : "字符串示例值",
  "version" : "字符串示例值",
  "active" : true,
  "sortOrder" : 1
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

### 5. 删除/api/v1/promptTemplates/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除AI提示词模板表记录

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
DELETE /api/v1/promptTemplates/{id}
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

