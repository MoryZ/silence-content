# Proposal: tournament_group CRUD

## Why

赛事分组是挑战与排行榜的核心关联维度，需先明确 CRUD 行为和查询维度，避免后续分组计算与读写语义不一致。

## What Changes

- 为 tournament_group 建立独立 CRUD 能力提案。
- 明确按 event_game_id + stage_type + stage_number + group_date 作为主要查询维度。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中实现自动分组算法。
- 不引入外键约束或联合主键。
- 不在本提案中处理跨玩法分组迁移。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持创建分组记录，event_game_id、stage_type、stage_number、group_date、group_number 必填。
- Read: 系统 MUST 支持按 id 查询，且 SHOULD 支持按 event_game_id、stage_type、stage_number、group_date 组合过滤查询。
- Update: 系统 MUST 支持更新 tier_name、tier_order 等业务字段。
- Delete: 系统 MUST 使用逻辑删除（is_deleted=1）。
- Enum Mapping: stage_type MUST 以 tinyint(3) unsigned 存储。
- Audit: 系统 MUST 维护审计字段。
