# API接口文档

**数据表：** live_broadcaster

## 接口列表

### 1. 分页查询/api/v1/liveBroadcasters

**请求方法：** `GET`  
**接口描述：** 获取直播人员表列表

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| pageNo | number | 是 | 页码 | 1 |
| pageSize | number | 是 | 每页大小 | 20 |
| sort | string | 是 | 排序字段 | \-createdDate |
| wechatId | string | 否 | 根据微信号过滤 | 微信号 |
| umAccount | string | 否 | 根据UM账号过滤 | UM账号 |
| channel | number | 否 | 根据直播渠道：1\-好助手微信小程序 2\-随身易微信小程序过滤 | 1 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
GET /api/v1/liveBroadcasters?pageNo=1&pageSize=20&sort=-createdDate&wechatId=微信号&umAccount=UM账号&channel=1
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
    "channelAppid" : "直播渠道appid",
    "role" : 1,
    "umAccount" : "UM账号",
    "nickname" : "张三",
    "wechatId" : "微信号",
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
        "channelAppid" : "直播渠道appid",
        "role" : 1,
        "umAccount" : "UM账号",
        "nickname" : "张三",
        "wechatId" : "微信号",
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

### 2. 创建/api/v1/liveBroadcasters

**请求方法：** `POST`  
**接口描述：** 创建直播人员表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| platform | number | 是 | 直播平台：1\-微信小程序直播 | 1 |
| channel | number | 是 | 直播渠道：1\-好助手微信小程序 2\-随身易微信小程序 | 1 |
| channelAppid | string | 是 | 直播渠道appid | 直播渠道appid |
| role | number | 是 | 角色：1\-管理员 2\-运营者 3\-主播 | 1 |
| umAccount | string | 是 | UM账号 | UM账号 |
| nickname | string | 是 | 昵称 | 张三 |
| wechatId | string | 是 | 微信号 | 微信号 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
POST /api/v1/liveBroadcasters
Content-Type: application/json

{
  "platform" : 1,
  "channel" : 1,
  "channelAppid" : "直播渠道appid",
  "role" : 1,
  "umAccount" : "UM账号",
  "nickname" : "张三",
  "wechatId" : "微信号"
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

### 3. 根据主键查询/api/v1/liveBroadcasters/{id}

**请求方法：** `GET`  
**接口描述：** 根据主键获取直播人员表详情

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
GET /api/v1/liveBroadcasters/{id}
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
  "channelAppid" : "直播渠道appid",
  "role" : 1,
  "umAccount" : "UM账号",
  "nickname" : "张三",
  "wechatId" : "微信号",
  "deleted" : true,
  "createdBy" : "创建人",
  "createdDate" : "2025-11-25T17:28:03Z",
  "updatedBy" : "更新人",
  "updatedDate" : "2025-11-25T17:28:03Z"
}
```
</details>

### 4. 更新/api/v1/liveBroadcasters/{id}

**请求方法：** `PUT`  
**接口描述：** 更新直播人员表记录

#### 请求参数

<details open>
<summary>📋 参数列表（点击折叠）</summary>

| 参数名 | 类型 | 是否必填 | 描述 | 示例 |
|--------|------|----------|------|------|
| platform | number | 是 | 直播平台：1\-微信小程序直播 | 1 |
| channel | number | 是 | 直播渠道：1\-好助手微信小程序 2\-随身易微信小程序 | 1 |
| channelAppid | string | 是 | 直播渠道appid | 直播渠道appid |
| role | number | 是 | 角色：1\-管理员 2\-运营者 3\-主播 | 1 |
| umAccount | string | 是 | UM账号 | UM账号 |
| nickname | string | 是 | 昵称 | 张三 |
| wechatId | string | 是 | 微信号 | 微信号 |
</details>

<details>
<summary>📄 请求源码（点击展开）</summary>

```http
PUT /api/v1/liveBroadcasters/{id}
Content-Type: application/json

{
  "platform" : 1,
  "channel" : 1,
  "channelAppid" : "直播渠道appid",
  "role" : 1,
  "umAccount" : "UM账号",
  "nickname" : "张三",
  "wechatId" : "微信号"
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

### 5. 删除/api/v1/liveBroadcasters/{id}

**请求方法：** `DELETE`  
**接口描述：** 删除直播人员表记录

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
DELETE /api/v1/liveBroadcasters/{id}
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

