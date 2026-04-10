# Specs: tournament_participation_record CRUD

## ADDED Requirements

### Requirement: Create tournament_participation_record

系统 MUST 支持创建参赛记录，event_game_id、participant_id、participant_type、status 必填。

#### Scenario: Register Participant

Given 参赛单位满足报名条件。

When 调用创建接口。

Then 成功写入参赛记录。

### Requirement: Read tournament_participation_record

系统 MUST 支持按 id 查询；SHOULD 支持按 event_game_id + participant_id 查询。

#### Scenario: Query Participant Record

Given 用户存在报名记录。

When 按 event_game_id + participant_id 查询。

Then 返回匹配记录且 status 正确。

### Requirement: Update tournament_participation_record

系统 MUST 支持更新 status、registration_time、total_score。

#### Scenario: Update Participant Score

Given 参赛记录存在。

When 更新 total_score。

Then 分数字段更新成功。

### Requirement: Delete tournament_participation_record

系统 MUST 使用逻辑删除。

#### Scenario: Logical Delete Participation

Given 参赛记录存在。

When 删除参赛记录。

Then 该记录 is_deleted=1。
