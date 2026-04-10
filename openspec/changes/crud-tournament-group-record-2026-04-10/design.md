# Design: tournament_group_record CRUD

## 架构决策

### 决策 1：关系约束

选择：不使用外键，应用层保障 group_id 有效性。

### 决策 2：主键策略

选择：使用单列 id，自增主键；不使用联合主键。

### 决策 3：枚举持久化

participant_type 统一为 tinyint(3) unsigned。
