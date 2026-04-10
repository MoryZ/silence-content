# Proposal: 勋章权益

## Why

扩展现有权益系统，支持"勋章"类型权益。勋章在管理侧与通用权益完全隔离，在发放侧保证"同用户同勋章仅发放一次"的幂等约束，并支持删除后的状态回收与重新达成。

唯一性约束 MUST 在数据库层通过 UNIQUE INDEX 保证，不依赖应用层校验。

## What Changes

- 新增独立勋章定义与持有记录模型（`medal` / `user_medal_record`）。
- 管理侧将勋章与通用权益列表隔离，并保证同分类名称唯一。
- 发放侧保证同一 `(user_type, user_id, medal_id)` 幂等下发。
- 删除勋章后回收持有状态，重建新勋章可重新获得。
- 补齐 service-api DTO/接口、service 实现、console 管理与测试覆盖。

## Non-Goals

- 不涉及批量导入勋章定义。
- 勋章不能归属多个分类。
- 不支持持有者主动放弃勋章。
- 已下发勋章无法撤销（删除勋章后触发的持有状态回收属于系统一致性处理，不视为撤销能力）。
- 不定义勋章在 C 端的展示样式与顺序。

## Requirements (RFC 2119)

### 管理侧（Proposal A 范围）

- **Visibility**: 勋章数据 MUST NOT 出现在通用的"权益配置"列表页。
- **Uniqueness**: 同一分类下的勋章名称 MUST 全局唯一，通过数据库唯一索引 `(category, name)` 保证。
- **Audit**: 所有勋章定义记录 MUST 包含审计字段 `created_by`、`updated_by`、`created_date`、`updated_date`。

### 发放侧（Proposal B 范围）

- **Idempotency**: 系统 MUST 对同一 `(user_type, user_id, medal_id)` 仅允许一次成功下发。
- **State Propagation**: 勋章编辑后，已持有用户看到的内容 MUST 同步更新。
- **Deletion Recovery**: 勋章删除后，原持有者状态 MUST 变为 `REVOKED`（未获得）。
- **Cross-trigger Idempotency**: 多个外部任务可配置到同一勋章；无论哪个外部任务触发，下发系统对同一 `(user_type, user_id, medal_id)` MUST 仅成功一次。

## 影响模块

| 模块 | 变更类型 |
|---|---|
| `silence-content-service` | 核心逻辑（新增/修改） |
| `silence-content-service-api` | DTO 新增（下发请求/响应） |
| `silence-content-service-enums` | 新增 `MedalStatus` 枚举 |
| `silence-content-console` | 管理页列表过滤、重名校验 |
| DB changelogs | 新增 `user_medal_record` 表 + 索引 |
