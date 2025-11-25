# API接口文档

**数据表：** live_room_organization

## 接口列表

### 1. 分页查询/api/v1/liveRoomOrganizations

**请求方法：** `GET`  
**接口描述：** 获取直播间直播对象关联表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
| liveRoomId | number | 否 | 根据直播间ID过滤 | 1 |
| orgCode | string | 否 | 根据机构编码过滤 | CODE001 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/liveRoomOrganizations?pageNo=1&pageSize=20&sort=-createdDate&liveRoomId=1&orgCode=CODE001
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
    "liveRoomId" : 1,
    "orgCode" : "CODE001",
    "createdBy" : "创建人",
    "createdDate" : "2025-11-25T17:28:03Z",
    "updatedBy" : "更新人",
    "updatedDate" : "2025-11-25T17:28:03Z"
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
        "liveRoomId" : 1,
        "orgCode" : "CODE001",
        "createdBy" : "创建人",
        "createdDate" : "2025-11-25T17:28:03Z",
        "updatedBy" : "更新人",
        "updatedDate" : "2025-11-25T17:28:03Z"
      } ]
    } ]
  }
}
```
</details>

### 2. 创建/api/v1/liveRoomOrganizations

**请求方法：** `POST`  
**接口描述：** 创建直播间直播对象关联表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| liveRoomId | number | 是 | 直播间ID | 1 |
| orgCode | string | 是 | 机构编码 | CODE001 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/liveRoomOrganizations
Content-Type: application/json

{
  "liveRoomId" : 1,
  "orgCode" : "CODE001"
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

### 3. 根据主键查询/api/v1/liveRoomOrganizations/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取直播间直播对象关联表详情

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 主键ID | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/liveRoomOrganizations/{id}
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
  "liveRoomId" : 1,
  "orgCode" : "CODE001",
  "createdBy" : "创建人",
  "createdDate" : "2025-11-25T17:28:03Z",
  "updatedBy" : "更新人",
  "updatedDate" : "2025-11-25T17:28:03Z"
}
```
</details>

### 4. 更新/api/v1/liveRoomOrganizations/{id}

**请求方法：** `PUT`  
**接口描述：** 更新直播间直播对象关联表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| liveRoomId | number | 是 | 直播间ID | 1 |
| orgCode | string | 是 | 机构编码 | CODE001 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/liveRoomOrganizations/{id}
Content-Type: application/json

{
  "liveRoomId" : 1,
  "orgCode" : "CODE001"
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

### 5. 删除/api/v1/liveRoomOrganizations/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除直播间直播对象关联表记录

#### 请求参数

<details>
<summary>📋 参数列表（点击展开）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| id | number | 是 | 主键ID | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
DELETE /api/v1/liveRoomOrganizations/{id}
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

