# 赛事模块问题清单 - 代码分析答案

> 创建时间: 2026-04-23
> 最后更新: 2026-04-23
> 状态: 已分析，待确认

---

## 一、配置与初始化相关

### 问题1: 赛事时间段校验规则
**涉及文件:** `TournamentStageCalculationService`

**答案:**
```java
// 第115-128行
public static void checkTournamentRegisterTime(Instant registrationTime, Instant registrationStartTime, Instant registrationEndTime) {
    if (registrationTime.isBefore(registrationStartTime)) {
        throw MarketingBizException(TOURNAMENT_REGISTRATION_NOT_READY);
    }
    if (registrationTime.isAfter(registrationEndTime)) {
        throw MarketingBizException(TOURNAMENT_REGISTRATION_END);
    }
}
```
- 校验逻辑：报名时间必须在 `registrationStartTime` 和 `registrationEndTime` 之间
- 赛事状态校验：比赛开始前/后分别校验 `TOURNAMENT_MARKETING_EVENT_NOT_READY` / `TOURNAMENT_MARKETING_EVENT_END`

---

### 问题2: 场次数量计算
**涉及文件:** `TournamentStageCalculationService.resolveTotalStages()`

**答案 (第147-150行):**
```java
public static long resolveTotalStages(Instant tournamentStartTime, Instant tournamentEndTime) {
    var daysBetween = ChronoUnit.DAYS.between(tournamentStartTime, tournamentEndTime);
    return daysBetween + 1;  // 简单计算：天数+1
}
```
**规则:** 场次数 = 结束日期 - 开始日期 + 1（本次写死，后续可能优化）

---

### 问题3: cycle/segment/stage 三者关系
**涉及文件:** `TournamentTaskManageService.initSettlementTasks()`

**答案 (第84-95行):**
```java
for (int stageNo = 1; stageNo <= totalStages; stageNo++) {
    // 1. 计算当前场次的触发时间
    Instant triggerTime = getJobTriggerTime(...).plus(Duration.ofDays(stageNo));
    // 2. 计算 cycle/segment/stage 编号（目前写死）
    var triple = TournamentStageCalculationService.calculateCycleSegmentStage(...);
    // 3. 为每种任务类型创建任务（串联依赖）
    for (TournamentTaskType type : [STAGE_SETTLE, SEGMENT_SETTLE, CYCLE_SETTLE]) {
        // 任务依赖链：REGISTRATION_END → STAGE_SETTLE → SEGMENT_SETTLE → CYCLE_SETTLE → ISSUE_REWARD
    }
}
```
**三者关系 (目前写死):**
- `cycleNo` = 赛程第几天 (1, 2, 3...)
- `segmentNo` = 1 (固定)
- `stageNo` = 1 (固定)

---

### 问题4: 触发时间计算
**涉及文件:** `TournamentStageCalculationService.buildTriggerTime()`

**答案 (第159-166行):**
```java
public static Instant buildTriggerTime(Instant startTime, LocalTime stageEndTime, Duration buffer) {
    var localDate = startTime.atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate.atTime(stageEndTime)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .plus(buffer);  // 场次结束时间 + 超时buffer
}
```
**规则:** `触发时间 = 场次开始日期的结束时间 + buffer(超时分钟)`

---

### 问题5: 扫描Job频率
**涉及文件:** `TournamentStartScanJob`

**答案:**
- Job 由外部调度器触发（`@JobExecutor(name = "tournamentStartScanJob")`）
- 扫描条件：`tournamentStartTime >= now` (第34行)
- 每次查询：`BATCH_SIZE = 100` (第20行)
- 处理逻辑：遍历所有 `tournamentStartTime >= now` 的赛事，初始化任务链

---

## 二、任务调度相关

### 问题6: 任务依赖链校验
**涉及文件:** `TournamentTaskManageService`

**答案 (第76-105行):**
```java
// 报名结束任务
var registrationTournamentTask = buildTask(...);
registrationTournamentTask.setDependsOnStatus(TournamentTaskStatus.SUCCESS);
tournamentTaskRepository.create(registrationTournamentTask);

// 场次/片/周期任务（链式依赖）
for (...) {
    tournamentTaskByTaskType.setDependsOnTaskId(dependsOnTaskId);
    tournamentTaskByTaskType.setDependsOnStatus(TournamentTaskStatus.SUCCESS);
}
```
**依赖校验:** 通过 `dependsOnTaskId` 和 `dependsOnStatus` 字段关联

---

### 问题7: CYCLE_SETTLE 放 BP 层执行
**涉及文件:** `CycleSettleTaskService`

**答案:**
- `CYCLE_SETTLE` 任务在 BP 层执行（第46-55行）
- **原因:** 需要调用外部服务 `tournamentRankService.globalRank()` 等
- **数据量:** 使用分页查询，每页 1000 条（第39行 `PAGE_SIZE = 1000`）
- **其他任务:** `STAGE_SETTLE` 也有分页处理（第53行 `PAGE_SIZE = 1000`）

---

### 问题8: 任务失败重试机制
**涉及文件:** `TaskDispatcherService`

**答案 (第26, 53-60行):**
```java
private static final int MAX_RETRY_COUNT = 3;  // 最多重试3次

if (nextRetryCount >= MAX_RETRY_COUNT) {
    tournamentTask.setStatus(TournamentTaskStatus.TERMINAL_FAILED);
} else {
    tournamentTask.setStatus(TournamentTaskStatus.FAILED);
}
```
**规则:**
- `FAILED` → 继续重试（下次扫描时重新执行）
- 重试 3 次后 → `TERMINAL_FAILED`（终态）

---

### 问题9: 任务幂等性
**涉及文件:** 各 Handler

**答案:**
- `REGISTRATION_END`: 检查任务是否存在再初始化（第59行 `hasAnyTask`）
- `STAGE_SETTLE`: 检查场次结束时间（第91行）
- `SEGMENT_SETTLE`: 直接更新状态（第40行）
- `CYCLE_SETTLE`: 检查前置任务是否完成

---

### 问题10: 任务状态流转
**涉及文件:** `TournamentTaskStatus`

**答案:**
```
PENDING → RUNNING → SUCCESS
                  → FAILED → RUNNING (重试)
                           → TERMINAL_FAILED (重试3次后终态)
```

---

### 问题11: 扫描Job频率
**涉及文件:** `TournamentTaskDispatchJob`

**答案:**
- Job 由外部调度器触发
- 查询条件：`triggerTime <= now AND status IN (PENDING, FAILED)`
- 每次查询：`BATCH_SIZE = 100`（第23行）
- 重试任务：`FAILED` 状态的任务也会被重新调度

---

## 三、结算相关

### 问题12: 场次结算 (STAGE_SETTLE)
**涉及文件:** `StageSettleTaskHandlerStrategy`

**答案:**
1. 校验场次是否结束
2. 获取所有分组
3. 遍历分组：
   - 获取真人最低正分
   - 分页获取参与者
   - 查询每个参与者的挑战记录（取最高分）
   - 生成得分记录（真人用实际分，机器人用公式计算）
4. 批量创建得分记录
5. 创建组排行榜（TOP 50）

---

### 问题13: 片结算 (SEGMENT_SETTLE)
**涉及文件:** `SegmentSettleTaskHandlerStrategy`

**答案:**
```java
@Override
public void execute(TournamentTask tournamentTask) {
    tournamentTaskDomainService.updateStatusAsLock(...);  // 直接标记成功
    log.info("SegmentSettleTaskHandler done...");
}
```
**结论:** 片结算当前是空壳，直接标记成功。业务逻辑待实现。

---

### 问题14: 周期结算 (CYCLE_SETTLE)
**涉及文件:** `StageSettleTaskHandlerStrategy` 第84-155行

**答案:** 周期结算不在 `StageSettleTaskHandlerStrategy`，而是在 `CycleSettleTaskService`：
```java
if (isLastCycle()) {
    // 1. 计算赛事累计得分
    // 2. 创建全国排行榜
} else {
    // 重新分组
}
```

---

### 问题15: 机器人得分系数
**涉及文件:** `StageSettleTaskHandlerStrategy`

**答案 (第173-176行):**
```java
private BigDecimal calculateRobotScore(BigDecimal robotScoreCoefficientMin,
                                      BigDecimal robotScoreCoefficientMax,
                                      BigDecimal finalScore, int scale) {
    var random = ThreadLocalRandom.current()
        .nextDouble(robotScoreCoefficientMin.doubleValue(),
                    robotScoreCoefficientMax.doubleValue());
    return BigDecimal.valueOf(random).multiply(finalScore).setScale(scale, RoundingMode.HALF_EVEN);
}
```
**公式:** `机器人得分 = random(系数Min, 系数Max) × 真人最低正分`

---

### 问题16: 得分记录去重
**涉及文件:** `TournamentChallengeDomainService.findCurrentStageGroupParticipantMaxRecords()`

**答案 (第447-455行):**
```java
public List<TournamentChallengeRecord> findCurrentStageGroupParticipantMaxRecords(...) {
    var records = tournamentChallengeRepository.findByEventGameIdAndCycleNumberAndParticipantIdIn(...);
    return CollectionUtils.transformToList(
        records.stream()
            .collect(Collectors.toMap(
                TournamentChallengeRecord::getParticipantId,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(TournamentChallengeRecord::getFinalScore))  // 取最高分
            ))
            .values(),
        Function.identity()
    );
}
```
**规则:** 同一用户多次挑战，取 `finalScore` 最高的记录

---

### 问题17: 排行榜计算
**涉及文件:** `TournamentRankingDomainService.buildGroupRanking()`

**答案 (第43-76行):**
1. 按得分升序排序
2. 取 TOP 50
3. 查询机器人信息（昵称、头像）
4. 填充排行榜数据

---

## 四、用户参与相关

### 问题18: 报名时分组逻辑
**涉及文件:** `TournamentGroupDomainService`

**答案:** 分组逻辑不在此服务中，需查看 `TournamentGroupRepository.findParticipantGroup()`

---

### 问题19: 挑战次数限制
**涉及文件:** `TournamentChallengeDomainService`

**答案:**
- 代码中只校验了进行中挑战是否存在（第180-184行）
- **没有限制挑战次数**
- 用户可以多次挑战，每次取最高分

---

### 问题20: 挑战超时处理
**涉及文件:** `TournamentChallengeDomainService`

**答案:**
```java
// 缓存时间：挑战超时时间（默认30分钟）
long timeoutSeconds = config.getChallengeTimeoutMinutes() * 60L;
redisClient.set(activeChallengeKey, newActiveStr, timeoutSeconds, TimeUnit.SECONDS);
```
**规则:**
- Redis key 过期后，缓存自动删除
- 过期后 `completeChallenge` 会抛出 `TOURNAMENT_CHALLENGE_NOT_FOUND`
- **DB 记录:** 只有成功的挑战才会写入 DB，超时的不会

---

### 问题21: 运动项目抽取
**涉及文件:** `TournamentChallengeDomainService.extractExerciseItems()`

**答案 (第283-314行):**
```java
private List<ExerciseScoreCoefficient> extractExerciseItems(TournamentConfigCacheDto config) {
    // 1. 从配置获取运动项列表
    List<ExerciseScoreCoefficient> selected = cycleStageConfig.getExerciseScoreCoefficients();
    // 2. 根据 pickMode 决定顺序
    switch (cycleStageConfig.getPickMode()) {
        case RANDOM:
            Collections.shuffle(selected);  // 随机打乱
            break;
        case SEQUENCE:
            // 保持原顺序
            break;
    }
    return selected;  // 返回全部运动项
}
```
**结论:** 不是抽取3个，而是打乱顺序后返回全部

---

### 问题22: 得分计算公式
**涉及文件:** `TournamentChallengeScoreDomainService.calculateBasicScore()`

**答案 (第33-48行):**
```java
公式: totalScore = sum(每个运动项的 result × scoreRate)
```
**示例:**
```
运动项1: result=100, scoreRate=1.5 → 得分=150
运动项2: result=80, scoreRate=2.0  → 得分=160
运动项3: result=60, scoreRate=1.0  → 得分=60
总得分 = 150 + 160 + 60 = 370
```

---

## 五、奖励相关

### 问题23: 奖励发放 (ISSUE_REWARD)
**涉及文件:** `TournamentIssueRewardDomainService`

**答案:** 当前是空类，待实现

---

### 问题24: 奖励规则
**答案:** 待确认

---

### 问题25: 发奖时机
**答案:** 待确认

---

## 六、排行榜相关

### 问题26: 排行榜类型
**涉及文件:** `TournamentRankService`, `TournamentRankingDomainService`

**答案:**
- `GROUP` (组内排行): `TournamentRankingDomainService.buildGroupRanking()`
- `GLOBAL` (全服排行): `TournamentRankService.globalRank()`

---

### 问题27: 排行榜实时性
**涉及文件:** `StageSettleTaskHandlerStrategy`

**答案:**
- 排行榜在 `STAGE_SETTLE` 任务执行时批量生成
- **非实时**，是定时刷新

---

### 问题28: 历史排行
**答案:** 待确认

---

## 七、其他问题

### 问题29: 并发安全/幂等性
**涉及文件:** `TournamentChallengeDomainService.completeChallenge()`

**答案 (第232-254行):**
```java
String countlockKey = TournamentLockKeyUtils.getTournamentChallengeCountLockKey(...);
boolean result = redissonLock.tryLock(countlockKey, TimeUnit.SECONDS, TOURNEY_LOCK_TIMEOUT);
if (!result) {
    throw MarketingBizException(TOURNAMENT_REGISTRATION_OPERATE_FREQUENTLY);
}
```
**规则:** 使用 Redis 分布式锁保证幂等性

---

### 问题30: 缓存一致性
**答案:** 待确认（需要查看各缓存服务的具体实现）

---

### 问题31: 监控报警
**答案:** 待确认

---

### 问题32: 数据清理
**答案:** 待确认

---

## 待确认问题汇总

| 序号 | 问题 | 涉及文件 | 状态 |
|------|------|---------|------|
| 18 | 报名时分组逻辑 | TournamentGroupRepository | 待确认 |
| 23 | 奖励发放实现 | TournamentIssueRewardDomainService | 待实现 |
| 24 | 奖励规则 | - | 待确认 |
| 25 | 发奖时机 | - | 待确认 |
| 28 | 历史排行保留时间 | - | 待确认 |
| 30 | 缓存一致性策略 | 各缓存服务 | 待确认 |
| 31 | 监控报警机制 | - | 待确认 |
| 32 | 数据清理策略 | - | 待确认 |
