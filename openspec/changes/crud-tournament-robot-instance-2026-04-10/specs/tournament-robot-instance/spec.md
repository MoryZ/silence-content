# Specs: tournament_robot_instance CRUD

## ADDED Requirements

### Requirement: Create tournament_robot_instance

系统 MUST 支持创建机器人实例，event_game_id、robot_instance_type、robot_id、status 必填。

#### Scenario: Create Robot

Given 机器人模板可用。

When 创建机器人实例。

Then 成功写入机器人记录。

### Requirement: Read tournament_robot_instance

系统 MUST 支持按 id 查询；SHOULD 支持按 event_game_id、status 查询。

#### Scenario: Query Active Robots

Given 赛事下存在多条机器人记录。

When 按 event_game_id 与 status 查询。

Then 返回匹配状态的机器人列表。

### Requirement: Update tournament_robot_instance

系统 MUST 支持更新 nickname、avatar_url、status、template_id。

#### Scenario: Disable Robot

Given 机器人处于启用状态。

When 更新 status 为停用。

Then 机器人状态更新成功。

### Requirement: Delete tournament_robot_instance

系统 MUST 使用逻辑删除。

#### Scenario: Logical Delete Robot

Given 机器人记录存在。

When 删除记录。

Then is_deleted=1。
