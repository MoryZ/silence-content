# Proposal: tournament_participation_record CRUD

## Why

参赛记录是赛事参与状态与累计得分的主记录，需要明确 CRUD 行为以支撑报名、退赛、状态变更和积分更新。

## What Changes

- 为 tournament_participation_record 建立独立 CRUD 能力提案。
- 明确按 event_game_id、participant_id、participant_type 的检索与更新场景。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中定义积分结算算法。
- 不引入外键约束或联合主键。
- 不在本提案中处理历史排行榜重算。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持创建参赛记录，event_game_id、participant_id、participant_type、status 必填。
- Read: 系统 MUST 支持按 id 查询，SHOULD 支持按 event_game_id + participant_id 查询。
- Update: 系统 MUST 支持更新 status、registration_time、total_score。
- Delete: 系统 MUST 使用逻辑删除。
- Enum Mapping: participant_type、status MUST 以 tinyint(3) unsigned 存储。
- Audit: 系统 MUST 维护审计字段。
