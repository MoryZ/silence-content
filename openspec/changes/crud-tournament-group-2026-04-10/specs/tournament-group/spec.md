# Specs: tournament_group CRUD

## ADDED Requirements

### Requirement: Create tournament_group

系统 MUST 支持创建分组记录，event_game_id、stage_type、stage_number、group_date、group_number 必填。

#### Scenario: Create Group Success

Given 输入包含完整必填字段。

When 调用创建接口。

Then 成功生成分组记录并返回 id。

### Requirement: Read tournament_group

系统 MUST 支持按 id 查询；SHOULD 支持按 event_game_id、stage_type、stage_number、group_date 查询列表。

#### Scenario: Query Group List

Given 同一赛事下存在多个场次与分组。

When 按 event_game_id + stage_type + stage_number + group_date 查询。

Then 仅返回匹配维度且 is_deleted=0 的分组记录。

### Requirement: Update tournament_group

系统 MUST 支持更新 tier_name、tier_order。

#### Scenario: Update Tier

Given 分组已存在。

When 更新 tier_name 与 tier_order。

Then 分组段位信息更新成功。

### Requirement: Delete tournament_group

系统 MUST 使用逻辑删除。

#### Scenario: Logical Delete Group

Given 分组已存在。

When 删除分组。

Then 该分组 is_deleted=1。
