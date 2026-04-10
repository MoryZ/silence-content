# Design: tournament_group CRUD

## 架构决策

### 决策 1：分组查询维度

选择：以 event_game_id + stage_type + stage_number + group_date 作为核心筛选维度。

### 决策 2：主键策略

选择：仅使用单列 id 作为主键，不使用联合主键。

### 决策 3：枚举持久化

stage_type 统一为 tinyint(3) unsigned。
