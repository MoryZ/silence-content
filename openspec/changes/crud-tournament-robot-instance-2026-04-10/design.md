# Design: tournament_robot_instance CRUD

## 架构决策

### 决策 1：机器人身份字段

robot_id 作为业务标识，id 作为主键。

### 决策 2：状态管理

status 采用 tinyint(3) unsigned，支持启用/停用生命周期。

### 决策 3：删除策略

采用逻辑删除，避免历史挑战追溯丢失。
