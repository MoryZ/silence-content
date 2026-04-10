# Proposal: tournament_config CRUD

## Why

当前赛事配置缺少统一的增删改查变更提案，导致后续接口定义、校验规则和实现边界不清晰，难以保证跨模块一致性。

## What Changes

- 为 tournament_config 建立独立 CRUD 能力提案。
- 明确枚举字段以 tinyint(3) unsigned 存储并在应用层做双向映射。
- 明确 attributes 作为扩展属性 JSON 字段管理；其中包含 CycleStageConfig，不单独建表。
- 统一逻辑删除语义，删除操作仅变更 is_deleted。

## Non-Goals

- 不在本提案中新增 tournament_config 之外的新表。
- 不在本提案中引入外键约束或联合主键。
- 不在本提案中定义跨赛事复制配置能力。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持按 event_game_id 创建赛事配置，且 tournament_type、division_mode、match_mode 必填。
- Read: 系统 MUST 支持按 id 查询单条配置，并支持按 event_game_id 查询配置列表（过滤 is_deleted=0）。
- Update: 系统 MUST 支持按 id 更新配置，且更新后保留 created_* 审计字段不变。
- Delete: 系统 MUST 采用逻辑删除，将 is_deleted 置为 1，MUST NOT 物理删除。
- Enum Mapping: 系统 MUST 将 tournament_type、division_mode、match_mode、current_stage 以 tinyint(3) unsigned 持久化。
- Attributes: 系统 MUST 将 attributes 作为 JSON 完整读写；CycleStageConfig 作为 attributes 内嵌结构存储，MUST NOT 拆分为独立表。
- Audit: 系统 MUST 维护 created_by、created_date、updated_by、updated_date。
