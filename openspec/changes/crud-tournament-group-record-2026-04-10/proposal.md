# Proposal: tournament_group_record CRUD

## Why

分组成员记录承载参赛单位与分组的映射关系，必须独立约束 CRUD 语义，保证分组结果可追踪、可重建。

## What Changes

- 为 tournament_group_record 建立独立 CRUD 能力提案。
- 明确 group_id、participant_id、participant_type 的查询能力。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中实现批量随机分配策略。
- 不引入外键约束或联合主键。
- 不处理跨分组历史迁移修复。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持新增分组成员记录，group_id、participant_id、participant_type 必填。
- Read: 系统 MUST 支持按 id 查询，SHOULD 支持按 group_id 查询成员列表。
- Update: 系统 SHOULD 支持成员记录纠错更新（例如 participant_type 纠正）。
- Delete: 系统 MUST 采用逻辑删除。
- Enum Mapping: participant_type MUST 以 tinyint(3) unsigned 存储。
- Audit: 系统 MUST 维护审计字段。
