# Specs: tournament_challenge_record CRUD

## ADDED Requirements

### Requirement: Create tournament_challenge_record

系统 MUST 支持创建挑战记录，event_game_id、participant_id、participant_type、status 必填。

#### Scenario: Create Challenge Record

Given 挑战开始。

When 写入挑战记录。

Then 成功保存基础挑战信息。

### Requirement: Read tournament_challenge_record

系统 MUST 支持按 id 查询；SHOULD 支持按 event_game_id + cycle_number + stage_number 查询。

#### Scenario: Query Challenge By Stage

Given 某场次存在多条挑战记录。

When 按 event_game_id + cycle_number + stage_number 查询。

Then 返回该场次下有效挑战记录。

### Requirement: Update tournament_challenge_record

系统 MUST 支持更新 status、start_time、end_time、base_score、final_score、exercise_details。

#### Scenario: Complete Challenge

Given 挑战进行中。

When 更新 status 为完成并写入 final_score。

Then 挑战记录状态与分数正确持久化。

### Requirement: Delete tournament_challenge_record

系统 MUST 使用逻辑删除。

#### Scenario: Logical Delete Challenge

Given 记录存在。

When 调用删除。

Then is_deleted=1。
