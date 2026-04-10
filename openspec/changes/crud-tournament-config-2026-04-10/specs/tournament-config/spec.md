# Specs: tournament_config CRUD

## ADDED Requirements

### Requirement: Create tournament_config

系统 MUST 支持创建赛事配置记录，且 event_game_id、tournament_type、division_mode、match_mode 为必填。

#### Scenario: Create Success

Given 请求体包含完整必填参数。

When 调用创建接口。

Then 成功写入一条 is_deleted=0 的配置记录。

### Requirement: Read tournament_config

系统 MUST 支持按 id 查询；SHOULD 支持按 event_game_id 查询列表并过滤 is_deleted=0。

#### Scenario: Read By Event Game Id

Given event_game_id 下存在多条配置，其中部分已逻辑删除。

When 按 event_game_id 查询。

Then 返回结果不包含 is_deleted=1 的记录。

### Requirement: Update tournament_config

系统 MUST 支持按 id 更新配置字段，且 created_by、created_date MUST NOT 被覆盖。

#### Scenario: Update Fields

Given 已存在配置记录。

When 更新 max_participants 与 challenge_timeout_minutes。

Then 记录更新成功，updated_* 字段变化，created_* 保持不变。

### Requirement: Delete tournament_config

系统 MUST 采用逻辑删除，将 is_deleted 置为 1。

#### Scenario: Logical Delete

Given 已存在配置记录。

When 调用删除接口。

Then 该记录 is_deleted 变为 1。

### Requirement: Attributes JSON

系统 MUST 将 attributes 作为 JSON 完整读写；CycleStageConfig MUST 作为 attributes 内嵌结构存储。

#### Scenario: Save CycleStageConfig In Attributes

Given attributes 中包含 cycleStageConfig 节点。

When 创建或更新配置。

Then JSON 结构被完整持久化且可按原结构读取。
