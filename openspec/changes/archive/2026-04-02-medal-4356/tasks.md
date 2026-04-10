# Tasks: 勋章权益

## Checklist

### DB 变更（无依赖，可优先执行）

- [x] 新增 Liquibase changelog `changelog-20260402-medal.xml`，创建 `user_medal_record` 表（含所有字段与注释）
  验收：changelog 可在本地库成功执行，表结构与 specs.md 中 Data Schema 一致。

- [x] 在 `user_medal_record` 上追加唯一索引 `uk_user_medal_record_uid_mid (user_type, user_id, medal_id)`
  验收：重复插入同一组合键时，数据库报唯一约束错误。

- [x] 在 `medal` 定义表上确认并补齐唯一索引 `uk_medal_category_name (category, name)`
  验收：同分类重名插入失败，跨分类同名可成功。

- [x] 在 `changelog-master.xml` 中注册新 changelog 文件
  验收：`mvn -pl silence-content-service -am test` 通过（H2 模式跑通 Liquibase）。

---

### service-enums 模块

- [x] 新增枚举 `MedalStatus`：`HELD`、`REVOKED`
  验收：枚举类在 `com.old.silence.enums` 包下，可被 service 模块引用。

---

### service-api 模块

- [x] 新增 `MedalDistributeRequest` DTO（含 `userType`、`userId`、`medalId`）
  验收：字段与 specs.md 中 user_medal_record 对应字段一致，含 Bean Validation 注解。

- [x] 新增 `MedalDistributeResponse` DTO（含 `success`、`message`）
  验收：响应结构可直接序列化为 JSON。

- [x] 新增 `MedalDistributionService` 接口（声明 `distribute` 方法）
  验收：接口位于 service-api 模块，签名使用上述 Request/Response DTO。

---

### service 模块

- [x] 实现 `MedalDistributionServiceImpl#distribute`：先查再插，捕获 `DuplicateKeyException` 返回"用户已领取"
  验收：单测覆盖正常下发、重复下发、并发插入冲突三种场景。

- [x] 实现勋章删除时批量更新 `user_medal_record.status = REVOKED`
  验收：删除勋章后，对应持有记录全部变为 `REVOKED`，其他勋章不受影响。

- [x] 实现用户勋章查询接口：JOIN `medal` 表返回最新内容，不冗余内容字段
  验收：修改 `medal.description` 后，查询结果即刻反映新内容。

---

### console 模块

- [x] 通用权益列表查询加过滤：排除 `medal` 类型数据
  验收：列表页不出现勋章数据，勋章管理页可正常展示勋章列表。
  说明：当前实现中勋章采用独立 `medal` 表与独立 `/medals` 管理接口，未进入通用内容/权益列表查询链路，天然满足隔离要求。

- [x] 勋章创建接口校验：同分类名称唯一，失败返回 400 并提示具体错误信息
  验收：重名创建返回 400，错误信息包含"该分类下勋章名称已存在"。

---

### 测试

- [x] 新增集成测试类 `MedalDistributionIntegrationTest`，覆盖：重复下发、删除回收、重建重获、编辑同步 4 个场景
  验收：4 个用例全部通过，测试名可直接映射到 specs.md 中对应 Scenario 名称。

- [x] 回归验证：运行全模块测试，确认其他权益类型行为无回归
  验收：`mvn clean test` 全量通过，无新增失败用例。
