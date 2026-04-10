# Proposal: tournament_ranking CRUD

## Why

排行榜记录直接影响赛事结果展示，需要明确 CRUD 能力、排序字段和查询维度，确保读取稳定与更新可追溯。

## What Changes

- 为 tournament_ranking 建立独立 CRUD 能力提案。
- 明确按 group_id、ranking_type、rank_no 的读取维度。
- 统一逻辑删除语义。

## Non-Goals

- 不在本提案中实现排行榜实时重排算法。
- 不引入外键约束或联合主键。
- 不在本提案中实现跨组汇总榜。

## Requirements (RFC 2119)

- Create: 系统 MUST 支持创建排行榜记录，event_game_id、group_id、participant_id、participant_type、ranking_type 必填。
- Read: 系统 MUST 支持按 id 查询，SHOULD 支持按 group_id + ranking_type 查询榜单。
- Update: 系统 MUST 支持更新 score、rank_no、avatar_url、nickname。
- Delete: 系统 MUST 使用逻辑删除。
- Enum Mapping: participant_type、ranking_type MUST 以 tinyint(3) unsigned 存储。
- Audit: 系统 MUST 维护审计字段。
