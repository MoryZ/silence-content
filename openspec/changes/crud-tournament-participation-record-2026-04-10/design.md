# Design: tournament_participation_record CRUD

## 架构决策

### 决策 1：状态字段

status 采用 tinyint(3) unsigned，应用层映射 TournamentParticipantStatus。

### 决策 2：查询维度

以 event_game_id + participant_id + participant_type 作为主要检索路径。

### 决策 3：删除策略

统一逻辑删除。
