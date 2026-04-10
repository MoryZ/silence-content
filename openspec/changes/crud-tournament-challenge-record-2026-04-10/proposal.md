# Proposal: tournament_challenge_record CRUD

## Why

挑战记录是赛事过程明细数据，涉及挑战状态流转、分值写入与明细追踪，需通过独立提案约束 CRUD 与查询口径。

## What Changes

- 为 tournament_challenge_record 建立独立 CRUD 能力提案。
- 明确按 event_game_id、participant_id、cycle_number、stage_number、group_id 的查询能力。
- 明确 exercise_details 作为 JSON 字段读写。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中定义挑战调度策略。
- 不引入外键约束或联合主键。
- 不在本提案中定义异步补偿机制。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持创建挑战记录，event_game_id、participant_id、participant_type、status 必填。
- Read: 系统 MUST 支持按 id 查询，SHOULD 支持按 event_game_id 与周期场次维度检索。
- Update: 系统 MUST 支持更新 status、start_time、end_time、base_score、final_score、exercise_details。
- Delete: 系统 MUST 使用逻辑删除。
- Enum Mapping: participant_type、status MUST 以 tinyint(3) unsigned 存储。
- JSON: exercise_details MUST 以 JSON 字段存储并保持结构完整。
- Audit: 系统 MUST 维护审计字段。
