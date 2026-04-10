# Specs: tournament_group_record CRUD

## ADDED Requirements

### Requirement: Create tournament_group_record

系统 MUST 支持新增分组成员记录，group_id、participant_id、participant_type 必填。

#### Scenario: Add Member To Group

Given 分组存在且参赛单位有效。

When 调用新增成员接口。

Then 成功写入成员映射记录。

### Requirement: Read tournament_group_record

系统 MUST 支持按 id 查询；SHOULD 支持按 group_id 查询成员列表。

#### Scenario: Query Group Members

Given group_id 下存在多个成员记录。

When 按 group_id 查询。

Then 返回该组下 is_deleted=0 的成员列表。

### Requirement: Update tournament_group_record

系统 MUST 支持纠错更新 participant_type。

#### Scenario: Fix Participant Type

Given 成员类型录入错误。

When 更新 participant_type。

Then 记录更新成功。

### Requirement: Delete tournament_group_record

系统 MUST 使用逻辑删除。

#### Scenario: Remove Member

Given 成员记录存在。

When 删除成员记录。

Then 该记录 is_deleted=1。
