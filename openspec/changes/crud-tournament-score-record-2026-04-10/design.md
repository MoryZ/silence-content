# Design: tournament_score_record CRUD

## 架构决策

### 决策 1：得分类型字段

`scoreType` 采用 `tinyint(3) unsigned`，应用层映射 `TournamentScoreType`（STAGE=1 / SEGMENT=2 / CYCLE=3）。

### 决策 2：主要查询维度

以 `event_game_id + participant_id + participant_type` 作为主要检索路径；`score_type` 作为可选过滤维度。为此两组字段在 DB 层应建联合索引。

### 决策 3：删除策略

统一逻辑删除（`is_deleted`），与项目其他表保持一致。

### 决策 4：数值精度

`score` 采用 `DECIMAL(18,4)` 保留四位小数，避免浮点精度问题。

### 决策 5：层级维度字段

`stageNumber`（场次）、`segmentNumber`（片）、`cycleNumber`（周期）三者允许为空，由调用方根据 `scoreType` 传入对应层级编号。

## API 设计

| 方法   | 路径                                        | 说明             |
|--------|---------------------------------------------|------------------|
| POST   | /api/v1/tournament-score-records            | 创建得分记录     |
| GET    | /api/v1/tournament-score-records/{id}       | 按 id 查询       |
| GET    | /api/v1/tournament-score-records            | 条件分页查询     |
| PUT    | /api/v1/tournament-score-records/{id}       | 更新得分         |
| DELETE | /api/v1/tournament-score-records/{id}       | 逻辑删除         |

## 数据模型

```
tournament_score_record
  id               BIGINT UNSIGNED AUTO_INCREMENT PK
  event_game_id    BIGINT UNSIGNED NOT NULL
  participant_id   VARCHAR(64)     NOT NULL
  participant_type TINYINT(3) UNSIGNED NOT NULL
  score_type       TINYINT(3) UNSIGNED NOT NULL
  cycle_number     INT             NULL
  segment_number   INT             NULL
  stage_number     INT             NULL
  group_id         BIGINT UNSIGNED NULL
  score            DECIMAL(18,4)   NOT NULL
  created_by       VARCHAR(64)
  created_date     DATETIME(3)
  updated_by       VARCHAR(64)
  updated_date     DATETIME(3)
  is_deleted       BIT(1)
```

索引：`idx_event_participant (event_game_id, participant_id, participant_type)`

## 时序图

```
Console → TournamentScoreRecordClient → TournamentScoreRecordResource
  → TournamentScoreRecordMyBatisRepository
      → TournamentScoreRecordDao (MyBatis)
          → tournament_score_record (DB)
```
