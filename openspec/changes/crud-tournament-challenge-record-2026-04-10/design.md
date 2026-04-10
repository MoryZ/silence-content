# Design: tournament_challenge_record CRUD

## 架构决策

### 决策 1：明细字段建模

exercise_details 采用 JSON 字段，保留运动项明细扩展性。

### 决策 2：时序字段

start_time、end_time 使用 DATETIME(3) 记录挑战时间边界。

### 决策 3：枚举持久化

participant_type、status 使用 tinyint(3) unsigned。
