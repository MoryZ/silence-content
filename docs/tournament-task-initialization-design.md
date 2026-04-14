# Tournament Task 初始化与场次结束设计方案

## 1. 背景

赛事结算链路需要自动触发 task 任务的初始化。本方案重点设计两个核心问题：
1. **报名结束任务**如何加入 task 表
2. **场次结束任务**如何避免重复扫描并确保幂等性

## 2. 核心设计原则

1. **最小化扫描**：避免重复地扫描已处理的赛事
2. **预埋优先**：每个任务完成后主动预埋下一个任务
3. **幂等保证**：依靠 UNIQUE KEY 与 UPSERT 机制确保重复操作安全
4. **简化异常**：预埋失败概率极小，不设计复杂补偿机制

---

## 3. 问题 1：报名结束任务 - 初始化 Stage 任务

### 3.1 触发条件

```
赛事配置:
  - tournament_start_time = 2026-04-14 10:00:00（比赛开始时间）
  - 触发窗口 = tournament_start_time - 10 分钟 = 2026-04-14 09:50:00

定时 Job 行为（每分钟执行一次）:
  09:49 → 判断 now >= 09:50? 否 → 跳过
  09:50 → 判断 now >= 09:50? 是 → 执行 handleRegistrationEnd()
  09:51 → 判断 now >= 09:50? 是 → 重复执行（被 UNIQUE KEY 拦截）
```

### 3.2 幂等性机制

**使用 UNIQUE KEY 保护**：

```sql
-- tournament_task 表需要以下 UNIQUE 约束
UNIQUE KEY uk_task_stage_unique (
    tournament_id,           -- 赛事 ID
    stage_no,                -- 场次号
    task_type                -- 任务类型
)
```

**幂等执行逻辑**：

```
handleRegistrationEnd(tournamentId) {
  now = current_timestamp
  tournament = query tournament_config(tournamentId)
  
  // 判断是否到达触发窗口
  if (now >= tournament.tournament_start_time - 10 minutes) {
    // 尝试插入第一个 STAGE_SETTLE 任务
    // 如果已存在，INSERT 被 UNIQUE KEY 拦截，操作安全失败
    INSERT INTO tournament_task (
      tournament_id, 
      stage_no = 0, 
      task_type = STAGE_SETTLE,
      trigger_time = tournament.tournament_start_time - 10 minutes,
      status = PENDING,
      ...
    ) VALUES (...)
  }
}
```

**Job 扫描频率**：每分钟执行一次

---

## 4. 问题 2：场次结束任务 - 预埋型模式

### 4.1 场次结束时间的计算

```
Stage N 的结束时间 = tournament_start_time + (stage_no * stage_duration_days) + challenge_end_time

示例：
  tournament_start_time = 2026-04-14 00:00:00
  challenge_end_time = 22:00:00（每天收盘时间）
  stage_duration_days = 1
  
  Stage 0 结束 = 2026-04-14 + 0天 + 22:00 = 2026-04-14 22:00:00
  Stage 1 结束 = 2026-04-14 + 1天 + 22:00 = 2026-04-15 22:00:00
  Stage N 结束 = ... = tournament_end_time（final stage）
```

### 4.2 预埋型流程

```
Timeline:

[09:50] handleRegistrationEnd()
        ↓
        → insert STAGE_0 STAGE_SETTLE task (trigger_time = 2026-04-14 22:00)
        
[Dispatcher 调度]
        ↓
        [2026-04-14 22:00] STAGE_0 STAGE_SETTLE task 触发执行
        ↓
        → 聚合挑战记录 → 写入 STAGE_0 分数
        ↓ success
        ↓
        → appendNextTask(stage_no = 1)
          ↓
          INSERT STAGE_1 STAGE_SETTLE task 
          (trigger_time = 2026-04-15 22:00)
          [UNIQUE KEY 保护，重复 append 被拦截]
        
[Dispatcher 调度]
        ↓
        [2026-04-15 22:00] STAGE_1 STAGE_SETTLE task 触发执行
        ↓
        → appendNextTask(stage_no = 2)
        
[继续循环至 final stage...]

[Last Stage N] 完成
        ↓
        → appendNextTask() 检查：stage_no + 1 >= total_stages?
        → 是 → 不再 append
        → dispatcher 检查所有任务是否完成
        → 都完成 → 发送发奖 MQ 消息
```

### 4.3 预埋任务的职责设计

**关键要求**："任务需要知道自己做什么"

```java
// Stage 成功后的预埋逻辑
onStageSettleSuccess(task) {
  // 任务需要清晰地知道：
  // 1. 当前 stage_no 是多少
  // 2. 总共有多少个 stage (total_stages)
  // 3. 是否还有下一个 stage
  
  TournamentConfig config = queryById(task.tournament_id);
  
  if (task.stage_no + 1 < config.total_stages) {
    // 还有下个 stage，计算下个 stage 的结束时间
    nextStageNo = task.stage_no + 1;
    stageEndTime = config.tournament_start_time 
                 + (nextStageNo * config.stage_duration_days) 
                 + config.challenge_end_time;
    
    // 预埋下一个任务
    insertNextStageTask(
      tournament_id = task.tournament_id,
      stage_no = nextStageNo,
      task_type = STAGE_SETTLE,
      trigger_time = stageEndTime
    );
  }
  // 否则，该赛事所有 stage 已完成，不再预埋
}
```

### 4.4 幂等性保证

```sql
-- 预埋时也使用 UNIQUE KEY 保护
UNIQUE KEY uk_task_stage_unique (
    tournament_id,
    stage_no,
    task_type
)

-- 当 onStageSettleSuccess 多次执行时，appendNextTask 会重复尝试：
-- 第 1 次：INSERT STAGE_1 task → 成功
-- 第 2 次：INSERT STAGE_1 task → UNIQUE KEY 冲突，被拦截（安全失败）
-- 第 3 次：INSERT STAGE_1 task → UNIQUE KEY 冲突，被拦截（安全失败）
```

### 4.5 可选的全量预录策略

如果赛事的 Stage/Segment/Cycle 触发时点都可以事先计算出来，
则可以在报名结束阶段一次性预录整个任务图，而不是依赖成功后逐级插入。

优点：
- 减少“手工在成功后插入下一个任务”的必要性
- 任务执行顺序由 trigger_time + depends_on_task_id 联合保证
- 只要 task_type、stage_no、segment_no、cycle_no 唯一范围明确，就不会产生冲突

关键点：
- `STAGE_SETTLE`、`SEGMENT_SETTLE`、`CYCLE_SETTLE` 使用不同 `task_type`
- 每个任务的 `trigger_time` 按对应的实际结束时间计算
- 下游任务通过 `depends_on_task_id` 关联到上游任务的 `SUCCESS`

例如：
- 预先创建 `STAGE_0`、`STAGE_1` ... `STAGE_N`
- 预先创建 `SEGMENT_0_0`、`SEGMENT_0_1` ...，trigger_time 设为片结束时间
- 预先创建 `CYCLE_0_0`、`CYCLE_0_1` ...，trigger_time 设为周期结束时间

只要依赖关系正确，这种“全部预录”方式在调度器中也不会与 Stage/Segment/Cycle 冲突。

---

## 5. Segment 和 Cycle 任务的预埋

同 Stage 的逻辑：

```java
onSegmentSettleSuccess(task) {
  config = queryById(task.tournament_id);
  if (task.segment_no + 1 < config.total_segments) {
    insertNextSegmentTask(
      tournament_id = task.tournament_id,
      segment_no = task.segment_no + 1,
      task_type = SEGMENT_SETTLE,
      trigger_time = calculateSegmentEndTime(...)
    );
  }
}

onCycleSettleSuccess(task) {
  config = queryById(task.tournament_id);
  if (task.cycle_no + 1 < config.total_cycles) {
    insertNextCycleTask(
      tournament_id = task.tournament_id,
      cycle_no = task.cycle_no + 1,
      task_type = CYCLE_SETTLE,
      trigger_time = calculateCycleEndTime(...)
    );
  }
}
```

---

## 5.1 赛季终态排名与奖励发放

### 5.1.1 业务职责

最后一个周期的最后一个场次结束时，`CYCLE_SETTLE` 需要承担赛季终态结算和奖励触发。

职责包括：
- 计算赛季最终总分排行榜（全国排行）
- 计算组内排名（分组内排行）
- 读取奖励配置（rank interval -> prize item）
- 按最终排名区间生成奖励发放事件
- 通过 MQ 异步发送奖励分发消息

### 5.1.2 排名维度

- **全国排行**：基于 `tournament_participation_record.total_score` 或最终累计总分排序
- **组内排行**：基于同组成员的最终总分排序

这两条排行可同时计算，用于不同奖励维度或展示维度。

### 5.1.3 奖励发放

推荐的执行方式：

1. `CYCLE_SETTLE` 处理完成后，如果是最后一个周期的最后一个场次：
   - 读取最终参赛记录
   - 计算全国排名与组内排名
   - 读取奖品配置
   - 生成 `RewardDispatchMessage` 或 `SeasonRewardDispatchMessage`
   - 通过 MQ 发送给奖励系统

2. 奖励配置形式：
   - `rank_start`, `rank_end`, `award_payload`
   - 按不同排名区间发放对应奖励
   - 例如：1-3 名一档、4-10 名二档、11-50 名三档

3. MQ 方式：
   - 保持异步发放，避免在结算 Handler 内同步调用发奖业务
   - 通过 `runTraceId + tournamentId + rewardType` 做幂等键

### 5.1.4 推荐扩展

如果奖励发放逻辑复杂，建议额外创建一个单独的任务类型：
- `SEASON_REWARD` 或 `AWARD_DISPATCH`

该任务由最终 `CYCLE_SETTLE` 成功后创建，专门负责：
- 最终排名计算
- 奖品配置解析
- MQ 发放

这样可以把“得分聚合”与“奖励发放”两个语义分开，降低循环依赖风险。

---

## 6. 任务追踪关系

```
Stage_0 SUCCESS
  ↓ (STAGE_SETTLE 完成后，依赖表关系自动建立)
SEGMENT_0_0 (PENDING, depends_on_task_id = Stage_0.id)
  ↓ (SEGMENT 完成后)
CYCLE_0_0_0 (PENDING, depends_on_task_id = SEGMENT_0_0.id)
  ↓ (CYCLE 完成，dispatcher 发奖)
MQ: 发奖消息 (runTraceId = 整个链路的追踪 ID)
```

---

## 7. 幂等性总结

### 7.1 表的 UNIQUE 约束

```sql
ALTER TABLE tournament_task ADD UNIQUE KEY uk_task_stage_unique (
    tournament_id,    -- 赛事
    stage_no,         -- 场次
    segment_no,       -- 片（可为 NULL）
    cycle_no,         -- 周期（可为 NULL）
    task_type         -- 任务类型
);
```

### 7.2 重复触发场景

**场景 A**：Job 在 09:50 多次触发
```
09:50:00 → handleRegistrationEnd() → INSERT STAGE_0
09:50:15 → handleRegistrationEnd() → INSERT STAGE_0 (UNIQUE KEY 冲突，安全失败)
09:50:30 → handleRegistrationEnd() → INSERT STAGE_0 (UNIQUE KEY 冲突，安全失败)
```

**场景 B**：Stage_0 完成后，多个 dispatcher 实例同时调用 appendNextTask()
```
Dispatcher-1: onStageSettleSuccess() → INSERT STAGE_1 → 成功
Dispatcher-2: onStageSettleSuccess() → INSERT STAGE_1 → UNIQUE KEY 冲突，安全失败
Dispatcher-3: onStageSettleSuccess() → INSERT STAGE_1 → UNIQUE KEY 冲突，安全失败
```

**场景 C**：多 Segment 的情况
```
SEGMENT_0_0 完成 → appendNextCycleTask(cycle_no=0) → INSERT CYCLE_0_0_0
SEGMENT_0_1 完成 → appendNextCycleTask(cycle_no=1) → INSERT CYCLE_0_1_0
... 各自独立，UNIQUE KEY 保护各个 cycle 的唯一性
```

---

## 8. 异常处理

### 8.1 预埋失败处理

基于"预埋失败概率极小"的假设，**不设计复杂的补偿逻辑**。

但建议监控：

```
如果 onStageSettleSuccess() 抛异常：
  - 记录 ERROR 日志，包含 tournament_id, stage_no, exception detail
  - 该任务本身仍标记为 SUCCESS（业务数据已写入）
  - 下一个 stage 任务不会被创建，但不影响当前 stage 任务
  - 运维人工干预：
    a) 检查异常原因（DB 故障？业务逻辑错误？）
    b) 如果是瞬时故障，等待恢复后重新触发该赛事的编排
    c) 如果是逻辑错误，修复代码后手工插入该阶段的 next task
```

### 8.2 任务状态管理

```
status 流转：
  PENDING → RUNNING (dispatcher pick)
          → SUCCESS (handler 完成，预埋下一个任务)
          → FAILED (handler 抛异常，retry_count < max)
          → TERMINAL_FAILED (retry_count >= max)

terminal_failed 需要人工重试：
  POST /tournament-tasks/{taskId}/retry → task.status = PENDING
```

---

## 9. 核心查询与索引

### 9.1 关键索引

```sql
-- 调度扫描
CREATE INDEX idx_task_trigger_status ON tournament_task(trigger_time, status);

-- 赛事维度检查
CREATE INDEX idx_task_tournament_status ON tournament_task(tournament_id, status);

-- 场次维度检查（用于预埋时的 UNIQUE KEY 判定）
CREATE INDEX idx_task_stage_unique ON tournament_task(tournament_id, stage_no, segment_no, cycle_no, task_type);

-- 片维度检查
CREATE INDEX idx_task_segment_check ON tournament_task(tournament_id, segment_no, task_type);

-- 周期维度检查
CREATE INDEX idx_task_cycle_check ON tournament_task(tournament_id, cycle_no, task_type);

-- 依赖关系检查
CREATE INDEX idx_task_depends ON tournament_task(depends_on_task_id, depends_on_status);

-- 链路追踪
CREATE INDEX idx_task_trace ON tournament_task(run_trace_id);
```

### 9.2 关键查询

```sql
-- 调度器扫描：查询待执行任务
SELECT * FROM tournament_task 
WHERE status IN (PENDING, FAILED) 
  AND trigger_time <= NOW()
  AND retry_count < max_retry_count
ORDER BY trigger_time ASC
LIMIT batch_size;

-- 赛事完成判定：检查是否还有未完成任务
SELECT COUNT(*) FROM tournament_task 
WHERE tournament_id = ? 
  AND status IN (PENDING, RUNNING, FAILED);

-- 幂等性检查（预埋前）：检查 STAGE_1 是否已存在
SELECT COUNT(*) FROM tournament_task 
WHERE tournament_id = ? 
  AND stage_no = 1 
  AND task_type = STAGE_SETTLE;
```

---

## 10. 三个核心时序图

### 10.1 报名结束时序图

此时序图已拆分到独立文件：

- `docs/tournament-task-registration-end-sequence.mermaid`

主要展示：
- 每分钟触发 `RegistrationEndJob`
- 查询赛事配置
- 补机器人、创建分组、绑定参赛者
- 写入分组缓存
- 初始化第一个 `STAGE_SETTLE` 任务到 `tournament_task`

**关键点：**
- ✅ 补机器人、分组、绑定关系都在 `handleRegistrationEnd()` 中完成
- ✅ 分组关系写入 Redis 缓存供前端查看
- ✅ 最后初始化第一个 `STAGE_SETTLE` task，由 Dispatcher 后续执行

---

### 10.2 场次结束时序图（Dispatcher 调度链路）

此时序图已拆分到独立文件：

- `docs/tournament-task-dispatcher-flow-sequence.mermaid`

主要展示：
- Dispatcher 每 3-5 秒拉取待执行任务
- 依赖检查、状态更新、任务执行
- `STAGE_SETTLE` 完成后预埋 `SEGMENT_SETTLE`
- `SEGMENT_SETTLE` 完成后预埋 `CYCLE_SETTLE`
- `CYCLE_SETTLE` 完成后视情况刷新排行榜或发奖

**关键点：**
- ✅ Stage 完成 → 预埋 Segment 任务（UNIQUE KEY 保护）
- ✅ Segment 完成 → 预埋 Cycle 任务
- ✅ Cycle 完成 → 刷排行榜、重新分组（如果有下个 cycle）
- ✅ 所有任务完成 → 发送发奖 MQ 消息（仅一条）

---

### 10.3 每日缓存重构时序图

此时序图已拆分到独立文件：

- `docs/tournament-task-cache-rebuild-sequence.mermaid`

主要展示：
- 每日凌晨触发 `DailyCacheRebuildJob`
- 查询所有赛事配置
- 查询已报名参赛人员并排序
- 生成排行榜列表并写入 Redis

**关键点：**
- ✅ 查询所有进行中的赛事（不过滤，系统会自然处理过期赛事）
- ✅ 对每个赛事查询 REGISTERED 的参赛人员
- ✅ 按 `totalScore` 排序生成排行榜
- ✅ 存入 Redis，TTL = 1 天（每天凌晨重建）
- ✅ 用途：前端排行榜展示、实时排名查询

---

## 相关的关键业务缓存说明

**缓存对象及生命周期：**

| 缓存对象 | KEY 模式 | 来源 | 更新时机 | TTL | 说明 |
|---|---|---|---|---|---|
| 排行榜列表 | `ranking:list:{eventGameId}` | DailyCacheRebuildJob | 每日凌晨重建 | 1 day | 前端排行榜展示 |
| 分组关系 | `group:members:{groupId}` | GroupRecordService | 报名结束 / 周期结束重新分组 | 赛事周期 | 前端显示分组成员 |
| 赛事配置 | `tournament:config:{eventGameId}` | 可选 | 配置变更时 | 永久 | 加速配置查询（可选） |

---

## 10. 执行流总体时序图（简化版）

```
[开赛前 10 分钟]
     ↓
[RegistrationEndJob 触发]
     ↓
[handleRegistrationEnd()]
  → 补机器人
  → 创建分组
  → 写入分组缓存
  → 初始化 STAGE_0 task
     ↓
[Dispatcher 循环调度]
     ↓
STAGE_0 成功 → append SEGMENT_0
     ↓
SEGMENT_0 成功 → append CYCLE_0
     ↓
CYCLE_0 成功 → 检查完成 → 发回奖 MQ
     ↓
[每日凌晨]
     ↓
[DailyCacheRebuildJob 触发]
     ↓
[重建所有赛事的排行榜缓存]
     ↓
前端实时查询排行榜
```

---

## 11. 赛事状态判定逻辑

**无 status 字段，使用时间窗判定**：

```java
public boolean isTournamentRunning(TournamentConfig config) {
  // 判断赛事是否进行中
  return System.currentTimeMillis() < config.getTournamentEndTime().getTime();
}

// 使用场景：
// 1. dispatcher 判断是否需要继续处理该赛事的任务
// 2. 监控告警判断赛事是否已结束
```

---

## 12. 实现清单

### 12.1 需要修改/新增的组件

- [ ] `handleRegistrationEnd()` 加入"报名前 10 分钟"的时间检查
- [ ] `onStageSettleSuccess()` 加入预埋 SEGMENT 任务的逻辑
- [ ] `onSegmentSettleSuccess()` 加入预埋 CYCLE 任务的逻辑
- [ ] `onCycleSettleSuccess()` 不预埋（end of chain）
- [ ] Dispatcher 的 `appendNextTask()` 封装幂等插入逻辑
- [ ] 表结构确认 UNIQUE KEY 约束

### 12.2 关键文件位置

```
Service 层:
  - RegistrationEndOrchestrationService.handleRegistrationEnd()
  - TaskDispatcherService.appendNextTaskOnSuccess()
  
Handler 层:
  - StageSettleTaskHandlerStrategy.execute()
  - SegmentSettleTaskHandlerStrategy.execute()
  - CycleSettleTaskHandlerStrategy.execute()

数据库:
  - tournament_task 表的 UNIQUE KEY 约束
```

---

## 13. 质量保证

### 13.1 单元测试

- [ ] 报名前 10 分钟，handleRegistrationEnd() 应该插入 STAGE_0 任务
- [ ] 报名前 11 分钟，handleRegistrationEnd() 应该跳过
- [ ] 重复调用 handleRegistrationEnd() 不应该产生重复任务（UNIQUE KEY 保护）
- [ ] Stage 完成后，appendNextStageTask() 应该插入下一个 stage 任务
- [ ] 最后一个 stage 完成后，不应该预埋任何任务

### 13.2 集成测试

- [ ] 完整链路：Stage_0 → SEGMENT_0_0 → CYCLE_0_0_0 → 发奖 MQ
- [ ] 幂等性：多个 dispatcher 并发处理同一个 stage task，只产生一个 segment task
- [ ] 时间精度：trigger_time 是否正确计算

### 13.3 压测验证

- [ ] 并发执行 dispatcher 时，是否有重复任务产生
- [ ] DB 锁等待是否在可控范围

---

## 14. 后续优化方向

1. 在监控系统中增加"预埋失败"的告警
2. 可选：在 AppendNextTask 失败时，加入重试逻辑（背景 Job 补偿）
3. 优化：用 INSERT ... ON DUPLICATE KEY UPDATE 替代 INSERT IGNORE，记录更新次数便于调试

