# API接口文档

**数据表：** live_room

## 接口列表

### 1. 分页查询/api/v1/liveRooms

**请求方法：** `GET`  
**接口描述：** 获取直播间表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
| title | string | 否 | 根据直播标题过滤 | 示例标题 |
| startTimeStart | string | 否 | 开始时间开始时间（范围查询，UTC时间） | 2025\-11\-25T00:00:00Z |
| startTimeEnd | string | 否 | 开始时间结束时间（范围查询，UTC时间） | 2025\-11\-25T23:59:59Z |
| anchorUmAccount | string | 否 | 根据主播人UM账号过滤 | 主播人UM账号 |
| endTimeStart | string | 否 | 结束时间开始时间（范围查询，UTC时间） | 2025\-11\-25T00:00:00Z |
| endTimeEnd | string | 否 | 结束时间结束时间（范围查询，UTC时间） | 2025\-11\-25T23:59:59Z |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/liveRooms?pageNo=1&pageSize=20&sort=-createdDate&title=示例标题&startTimeStart=2025-11-25T00:00:00Z&startTimeEnd=2025-11-25T23:59:59Z&anchorUmAccount=主播人UM账号&endTimeStart=2025-11-25T00:00:00Z&endTimeEnd=2025-11-25T23:59:59Z
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
    "platform" : 1,
    "channel" : 1,
    "anchorUmAccount" : "主播人UM账号",
    "title" : "示例标题",
    "backgroundImage" : "https://www.example.com/image.jpg",
    "shareImage" : "https://www.example.com/image.jpg",
    "promotionImage" : "https://www.example.com/image.jpg",
    "startTime" : "2025-11-25T17:28:03Z",
    "endTime" : "2025-11-25T17:28:03Z",
    "roomType" : 1,
    "content" : "这是示例内容正文",
    "attributes" : "扩展属性",
    "deleted" : true,
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
        "platform" : 1,
        "channel" : 1,
        "anchorUmAccount" : "主播人UM账号",
        "title" : "示例标题",
        "backgroundImage" : "https://www.example.com/image.jpg",
        "shareImage" : "https://www.example.com/image.jpg",
        "promotionImage" : "https://www.example.com/image.jpg",
        "startTime" : "2025-11-25T17:28:03Z",
        "endTime" : "2025-11-25T17:28:03Z",
        "roomType" : 1,
        "content" : "这是示例内容正文",
        "attributes" : "扩展属性",
        "deleted" : true,
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

### 2. 创建/api/v1/liveRooms

**请求方法：** `POST`  
**接口描述：** 创建直播间表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| platform | number | 是 | 直播平台：1\-微信小程序直播 | 1 |
| channel | number | 是 | 直播渠道：1\-好助手微信小程序 2\-随身易微信小程序 | 1 |
| anchorUmAccount | string | 是 | 主播人UM账号 | 主播人UM账号 |
| title | string | 是 | 直播标题 | 示例标题 |
| backgroundImage | string | 是 | 背景图 | https://www\.example\.com/image\.jpg |
| shareImage | string | 是 | 直播间分享图 | https://www\.example\.com/image\.jpg |
| promotionImage | string | 是 | 直播宣传图 | https://www\.example\.com/image\.jpg |
| startTime | string | 是 | 开始时间 | 2025\-11\-25T17:28:03Z |
| endTime | string | 是 | 结束时间 | 2025\-11\-25T17:28:03Z |
| roomType | number | 是 | 直播间类型：1\-推流 2\-手机直播 | 1 |
| content | string | 否 | 直播内容 | 这是示例内容正文 |
| attributes | string | 否 | 扩展属性 | 扩展属性 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/liveRooms
Content-Type: application/json

{
  "platform" : 1,
  "channel" : 1,
  "anchorUmAccount" : "主播人UM账号",
  "title" : "示例标题",
  "backgroundImage" : "https://www.example.com/image.jpg",
  "shareImage" : "https://www.example.com/image.jpg",
  "promotionImage" : "https://www.example.com/image.jpg",
  "startTime" : "2025-11-25T17:28:03Z",
  "endTime" : "2025-11-25T17:28:03Z",
  "roomType" : 1,
  "content" : "这是示例内容正文",
  "attributes" : "扩展属性"
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

### 3. 根据主键查询/api/v1/liveRooms/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取直播间表详情

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
GET /api/v1/liveRooms/{id}
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
  "platform" : 1,
  "channel" : 1,
  "anchorUmAccount" : "主播人UM账号",
  "title" : "示例标题",
  "backgroundImage" : "https://www.example.com/image.jpg",
  "shareImage" : "https://www.example.com/image.jpg",
  "promotionImage" : "https://www.example.com/image.jpg",
  "startTime" : "2025-11-25T17:28:03Z",
  "endTime" : "2025-11-25T17:28:03Z",
  "roomType" : 1,
  "content" : "这是示例内容正文",
  "attributes" : "扩展属性",
  "deleted" : true,
  "createdBy" : "创建人",
  "createdDate" : "2025-11-25T17:28:03Z",
  "updatedBy" : "更新人",
  "updatedDate" : "2025-11-25T17:28:03Z"
}
```
</details>

### 4. 更新/api/v1/liveRooms/{id}

**请求方法：** `PUT`  
**接口描述：** 更新直播间表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| platform | number | 是 | 直播平台：1\-微信小程序直播 | 1 |
| channel | number | 是 | 直播渠道：1\-好助手微信小程序 2\-随身易微信小程序 | 1 |
| anchorUmAccount | string | 是 | 主播人UM账号 | 主播人UM账号 |
| title | string | 是 | 直播标题 | 示例标题 |
| backgroundImage | string | 是 | 背景图 | https://www\.example\.com/image\.jpg |
| shareImage | string | 是 | 直播间分享图 | https://www\.example\.com/image\.jpg |
| promotionImage | string | 是 | 直播宣传图 | https://www\.example\.com/image\.jpg |
| startTime | string | 是 | 开始时间 | 2025\-11\-25T17:28:03Z |
| endTime | string | 是 | 结束时间 | 2025\-11\-25T17:28:03Z |
| roomType | number | 是 | 直播间类型：1\-推流 2\-手机直播 | 1 |
| content | string | 否 | 直播内容 | 这是示例内容正文 |
| attributes | string | 否 | 扩展属性 | 扩展属性 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/liveRooms/{id}
Content-Type: application/json

{
  "platform" : 1,
  "channel" : 1,
  "anchorUmAccount" : "主播人UM账号",
  "title" : "示例标题",
  "backgroundImage" : "https://www.example.com/image.jpg",
  "shareImage" : "https://www.example.com/image.jpg",
  "promotionImage" : "https://www.example.com/image.jpg",
  "startTime" : "2025-11-25T17:28:03Z",
  "endTime" : "2025-11-25T17:28:03Z",
  "roomType" : 1,
  "content" : "这是示例内容正文",
  "attributes" : "扩展属性"
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

### 5. 删除/api/v1/liveRooms/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除直播间表记录

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
DELETE /api/v1/liveRooms/{id}
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

