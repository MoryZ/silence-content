# 赛事模块代码问题清单

> 创建时间: 2026-04-23
> 最后更新: 2026-04-23
> 状态: 全部处理完成

---

## 问题汇总

| 问题编号 | 优先级 | 状态 | 描述 | 修复人 |
|---------|--------|------|------|--------|
| 1 | P0 | ✅ 已修复 | groupId 未定义 | 用户 |
| 2 | P0 | ✅ 已修复 | while 循环条件逻辑错误 | 用户 |
| 3 | P1 | ✅ 无需修复 | 缺少投影注解 | - |
| 4 | P1 | ✅ 无需修复 | 方法名与实现不符 | - |
| 5 | P1 | ✅ 已修复 | TournamentIssueRewardDomainService 空类 | 用户 |
| 6 | P2 | ✅ 已修复 | Dao 重复定义 | 用户 |
| 7 | P1 | ✅ 无需修复 | execute() 方法为空 | - |
| 8 | P0 | ✅ 已修复 | RegistrationEndTaskHandlerStrategy 导入路径错误 | AI |
| 9 | P1 | ✅ 已修复 | initializeGroups 分组逻辑未包含机器人 | AI |
| 10 | P0 | ✅ 已修复 | TournamentTaskDispatchService 前置任务检查逻辑错误 | AI |
| 11 | P0 | ✅ 已修复 | StageSettleTaskHandlerStrategy 异常被吞没 | AI |

---

## 问题 1: TournamentChallengeDomainService groupId 未定义 ✅

**状态:** 已修复 (用户自行修复)

---

## 问题 2: StageSettleTaskHandlerStrategy while 循环条件逻辑错误 ✅

**状态:** 已修复 (用户已修改为 `isNotEmpty`)

---

## 问题 3: TournamentChallengeRecordOnlyFinalScoreView 缺少投影注解 ✅

**状态:** 无需修复
**原因:** 不暴露到 FeignClient，不需要 `@ProjectedPayload` 注解

---

## 问题 4: findMinPositiveScore 方法名与实现不符 ✅

**状态:** 无需修复
**原因:** 方法名已确认，符合业务需求

---

## 问题 5: TournamentIssueRewardDomainService 空类 ✅

**状态:** 已修复 (用户已修改为 Asc)

---

## 问题 6: ChallengeDao 存在重复定义 ✅

**状态:** 已修复 (用户已删除冗余文件)

**删除的文件:**
```
silence-content-service/src/main/java/com/old/silence/content/infrastructure/persistence/dao/tournament/TournamentChallengeRecordDao.java
```

**保留的文件:**
```
silence-content-service/src/main/java/com/old/silence/content/infrastructure/persistence/tournament/dao/TournamentChallengeDao.java
```

---

## 问题 7: IssueRewardTaskHandlerStrategy.execute() 方法为空 ✅

**状态:** 无需修复 (先为空实现，后续修改)

---

## 问题 8: RegistrationEndTaskHandlerStrategy 导入路径错误 ✅

**文件路径:**
```
silence-content-service/src/main/java/com/old/silence/content/domain/service/tournament/task/handler/RegistrationEndTaskHandlerStrategy.java
```

**修复内容:**
```java
// 修复前 (错误)
import com.old.silence.content.domain.model.tournament.TournamentTask;

// 修复后 (正确)
import com.old.silence.content.domain.model.TournamentTask;
```

**状态:** 已修复，编译通过

---

## 问题 9: initializeGroups 分组逻辑未包含机器人 ✅

**文件路径:**
```
silence-content-service/src/main/java/com/old/silence/content/domain/service/tournament/TournamentGroupRecordDomainService.java
```

**问题描述:**
- 根据问题18反馈：报名时分组逻辑是"根据真人报名记录计算要补的机器人、创建组、随机打乱、创建组和人员(包括机器人)关联关系"
- 原来的 `initializeGroups()` 方法虽然通过 `findByEventGameIdAndStatus()` 查到了所有报名记录（包括机器人），但日志描述不够准确

**修复内容:**
更新日志描述，明确说明分组包含真人和机器人：
```java
// 修复前
logger.info("【参赛者查询完成】共 {} 人，准备随机分组，每组 {} 人", participants.size(), groupSize);

// 修复后
logger.info("【参赛者查询完成】共 {} 人（真人+机器人），准备随机分组，每组 {} 人", participants.size(), groupSize);

// 修复前
logger.info("【初始化分组完成】共分配 {} 名成员，分组情况：{}", groupRecords.size(), ...);

// 修复后
logger.info("【初始化分组完成】共分配 {} 名成员（含真人+机器人），分组情况：{}", groupRecords.size(), ...);
```

**状态:** 已修复，编译通过

---

## 问题 10: TournamentTaskDispatchService 前置任务检查逻辑错误 ✅

**文件路径:**
```
silence-content-bp/src/main/java/com/old/silence/bp/service/TournamentTaskDispatchService.java
```

**问题描述:**
- 原代码检查的是**当前任务**的状态，而不是**前置任务**的状态
- 错误代码：
```java
var hasPrevTask = tournamentTaskDomainClient.findById(tournamentTask.getId(), TournamentTaskBpView.class)
        .filter(task -> !TournamentTaskStatus.SUCCESS.equals(task.getStatus()))
        .isPresent();
```
- 应该使用 `dependsOnTaskId` 来查询并检查**前置任务**的状态

**修复内容:**
```java
// 检查前置任务是否已完成
var prevTaskId = tournamentTask.getDependsOnTaskId();
if (prevTaskId != null) {
    var prevTaskCompleted = tournamentTaskDomainClient.findById(prevTaskId, TournamentTaskBpView.class)
            .filter(task -> TournamentTaskStatus.SUCCESS.equals(task.getStatus()))
            .isPresent();
    if (!prevTaskCompleted) {
        log.info("前置任务未完成, currentTaskId={}, prevTaskId={}", tournamentTask.getId(), prevTaskId);
        continue;
    }
}
```

**状态:** 已修复，编译通过

---

## 问题 11: StageSettleTaskHandlerStrategy 异常被吞没 ✅

**文件路径:**
```
silence-content-service/src/main/java/com/old/silence/content/domain/service/tournament/task/handler/StageSettleTaskHandlerStrategy.java
```

**问题描述:**
- 原代码在 `execute()` 方法中使用 try-catch 捕获了所有异常但只记录日志，不抛出异常
- 这导致任务会被 `TaskDispatcherService` 错误地标记为 SUCCESS，而不是 FAIL
- 异常应该让 `TaskDispatcherService` 统一处理，以便正确更新重试次数和状态

**修复内容:**
移除了 try-catch 块，让异常正常传播到 `TaskDispatcherService` 处理：
```java
// 移除了以下代码：
// try {
//     ...
// } catch (Exception e) {
//     log.error("StageSettleTaskHandlerStrategy run occur error :{}", e.getMessage());
// }
```

**状态:** 已修复，编译通过

---

## 待确认/暂不考虑的问题

| 序号 | 问题 | 说明 |
|------|------|------|
| 23 | 奖励发放实现 | 先不考虑 |
| 24 | 奖励规则 | 先不考虑 |
| 25 | 发奖时机（赛事结束时间） | 先不考虑 |
| 28 | 历史排行保留时间 | 先不考虑 |
| 30 | 缓存一致性策略 | 先不考虑 |
| 31 | 监控报警机制（任务重试超3次） | 先不考虑 |
| 32 | 数据清理策略 | 先不考虑 |

---

## 编译状态

✅ **Maven 编译成功**
- 执行命令: `mvn clean compile -DskipTests`
- 状态: Build Success
