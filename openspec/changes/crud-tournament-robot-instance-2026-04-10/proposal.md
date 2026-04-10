# Proposal: tournament_robot_instance CRUD

## Why

机器人实例记录用于赛事机器人生命周期管理，需明确 CRUD 语义与状态字段约束，以支持机器人池维护和赛事接入。

## What Changes

- 为 tournament_robot_instance 建立独立 CRUD 能力提案。
- 明确按 event_game_id、robot_id、status 的查询能力。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中实现机器人生成策略。
- 不引入外键约束或联合主键。
- 不在本提案中处理机器人行为参数自动调优。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持创建机器人实例，event_game_id、robot_instance_type、robot_id、status 必填。
- Read: 系统 MUST 支持按 id 查询，SHOULD 支持按 event_game_id、status 查询。
- Update: 系统 MUST 支持更新 nickname、avatar_url、status、template_id。
- Delete: 系统 MUST 使用逻辑删除。
- Enum Mapping: robot_instance_type、status MUST 以 tinyint(3) unsigned 存储。
- Audit: 系统 MUST 维护审计字段。
