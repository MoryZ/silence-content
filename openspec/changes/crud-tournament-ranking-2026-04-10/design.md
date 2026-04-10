# Design: tournament_ranking CRUD

## 架构决策

### 决策 1：榜单读取维度

group_id + ranking_type 作为榜单主查询条件，rank_no 用于排序。

### 决策 2：头像昵称冗余

保留 avatar_url、nickname 冗余字段以降低榜单查询联表成本。

### 决策 3：枚举持久化

participant_type、ranking_type 均为 tinyint(3) unsigned。
