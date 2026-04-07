# Tasks

回答**"具体要干哪几件事"**——带复选框的实现清单，AI 在 `/opsx:apply` 时逐条执行。

## 格式

```markdown
- [ ] 任务描述（尽量原子化，单个任务不超过 2 小时）
- [ ] 另一个任务
```

## 原则

- 每条任务单一职责，可独立验证
- 优先排列无依赖任务，便于并行执行
- 涉及数据库变更的任务单独列出

## 勋章权益变更任务清单

- [ ] 新增 `user_medal_record` 表 DDL（含字段与注释）
	验收：包含 `id/user_type/user_id/medal_id/status/created_by/updated_by/created_at/updated_at`，可在本地库成功执行。

- [ ] 为 `user_medal_record` 增加唯一索引 `(user_type, user_id, medal_id)`
	验收：重复插入同一组合键时报唯一约束错误。

- [ ] 在勋章定义表上确认并补齐唯一索引 `(category, name)`
	验收：同分类重名插入失败，不同分类同名可成功。

- [ ] `service-api` 增加勋章下发请求/响应 DTO（含 `user_type`）
	验收：DTO 字段与 `medal_entitlement_delta` 一致，接口文档可见。

- [ ] `service` 实现勋章下发幂等逻辑（按 `(user_type, user_id, medal_id)` 判重）
	验收：首次下发成功，重复下发返回“用户已领取”。

- [ ] `service` 实现勋章删除后的状态回收（`HELD -> REVOKED`）
	验收：删除勋章后历史持有记录状态更新为 `REVOKED`。

- [ ] `service` 实现重建同名勋章后的重新达成逻辑（新 `medal_id` 可再次获得）
	验收：旧 `REVOKED` 记录不阻止新 `medal_id` 下发。

- [ ] `service` 实现勋章编辑后持有者可见内容同步
	验收：修改勋章描述后，已持有用户查询结果展示最新描述。

- [ ] `console` 过滤通用权益列表中的勋章配置
	验收：通用权益列表不出现勋章数据。

- [ ] `console` 勋章管理页创建时校验“同分类名称唯一”
	验收：同分类重名创建返回 400 并提示名称已存在。

- [ ] 新增集成测试：重复下发、删除回收、重建重获、编辑同步
	验收：4 类用例全部通过，且每条 Given/When/Then 可映射到测试名。

- [ ] 回归验证并记录变更影响模块（console/service/service-api/db）
	验收：输出变更清单与测试结果，确认无其他权益类型行为回归。