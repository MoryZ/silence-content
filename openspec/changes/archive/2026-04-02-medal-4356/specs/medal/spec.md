# Specs: 勋章权益

## MODIFIED

### Requirement: 勋章配置与通用权益配置列表隔离

系统 MUST 将"勋章"与通用权益配置列表隔离管理，勋章数据 MUST NOT 出现在通用"权益配置"列表页。

#### Scenario: Administrative UI Filtering

Given 数据库中存在 3 条"红包"权益和 2 条"勋章"权益。

When 管理员打开"权益配置"列表页。

Then 系统只显示 3 条"红包"权益。

### Requirement: 同分类下勋章名称唯一

系统 MUST 保证同一分类下勋章名称唯一，通过数据库唯一索引 `(category, name)` 强约束保证一致性。

#### Scenario: Duplicate Name Creation Rejected

Given 分类"坚持挑战"下已存在名称为"7天打卡"的勋章。

When 管理员尝试在同一分类下创建同名勋章"7天打卡"。

Then 系统返回 400 并提示"该分类下勋章名称已存在"，且不写入新记录。

### Requirement: 勋章定义包含审计字段

勋章定义数据 MUST 包含审计字段 `created_by`、`updated_by`、`created_date`、`updated_date`。

#### Scenario: Medal Schema Audit Fields Present

Given 管理员完成一条新勋章定义的创建。

When 系统写入勋章定义数据。

Then 勋章定义记录包含 `created_by`、`updated_by`、`created_date`、`updated_date` 字段。

## ADDED Requirements

### Requirement: 新增 user_medal_record 模型

系统 MUST 新增 `user_medal_record` 表，字段如下：

| 字段 | 类型 | 约束 | 说明 |
|---|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| user_type | VARCHAR(32) | NOT NULL | 持有者类型（CUSTOMER / MEMBER） |
| user_id | VARCHAR(64) | NOT NULL | 持有者标识 |
| medal_id | BIGINT | NOT NULL | 勋章 ID |
| status | VARCHAR(16) | NOT NULL | HELD / REVOKED |
| created_by | VARCHAR(64) | NOT NULL | 创建人 |
| updated_by | VARCHAR(64) | NOT NULL | 最后修改人 |
| created_at | DATETIME | NOT NULL | 首次下发时间 |
| updated_at | DATETIME | NOT NULL | 最后状态变更时间 |

UNIQUE INDEX: `(user_type, user_id, medal_id)`

#### Scenario: Medal Holding Schema Supports Idempotency

Given 数据库已创建 `user_medal_record` 表。

When 系统尝试为同一 `(user_type, user_id, medal_id)` 写入重复记录。

Then 数据库抛出唯一约束异常，系统拦截后返回"用户已领取"错误码。

### Requirement: 勋章下发幂等

对同一 `(user_type, user_id, medal_id)`，系统 MUST 仅允许一次成功下发。

#### Scenario: Duplicate Distribution Prevention

Given 用户 U001 已持有勋章 M100。

When 系统再次尝试为 U001 下发 M100。

Then 系统返回错误码并提示"用户已领取"。

### Requirement: 勋章编辑后持有者可见内容同步

勋章定义发生编辑后，系统 MUST 使已持有用户看到的勋章内容同步更新。

#### Scenario: Content Sync After Edit

Given 用户 U003 持有勋章 M300（status = HELD），当前描述为"连续打卡 7 天"。

When 管理员将 M300 描述修改为"连续打卡 14 天"并保存。

Then U003 在 C 端看到的勋章描述变为"连续打卡 14 天"，且 status 保持 HELD。

### Requirement: 删除后状态回收与重新达成

系统删除勋章后，原持有者状态 MUST 变为 `REVOKED`。

对于重建后的同名勋章（新 `medal_id`），历史 `REVOKED` 记录 MUST NOT 阻止用户再次满足条件后重新获得。

#### Scenario: Re-earning After Deletion

Given 用户 U002 曾持有勋章 M200（status = HELD），管理员将 M200 删除，U002 的 status 变为 REVOKED。

When 管理员重新创建同名勋章（新 `medal_id`），系统接收到外部任务完成事件，U002 满足条件。

Then 系统为 U002 下发新勋章 ID 对应记录，status 更新为 HELD，历史 REVOKED 记录不阻止本次下发。

### Requirement: 多外部触发源下的同勋章一次性发放

多个外部任务可配置到同一勋章；无论哪个外部任务触发，下发系统对同一 `(user_type, user_id, medal_id)` MUST 仅成功一次。

#### Scenario: Cross-trigger Idempotency

Given 勋章 M500 已被外部任务 T1 与 T2 同时配置为触发目标，用户 U010 已因 T1 成功获得 M500。

When 外部任务 T2 再次触发对 U010 的 M500 下发。

Then 系统拒绝重复下发，并保持 U010 对 M500 的单一持有记录。

## Test Validation

### Scenario -> Test Mapping

| Scenario | Test Class | Test Method | Status |
|---|---|---|---|
| Duplicate Distribution Prevention | `MedalDistributionIntegrationTest` | `shouldPreventDuplicateDistribution` | PASS |
| Re-earning After Deletion | `MedalDistributionIntegrationTest` | `shouldAllowReEarnAfterDeletionWithNewMedalId` | PASS |
| Content Sync After Edit | `MedalDistributionIntegrationTest` | `shouldSyncLatestMedalContentAfterEdit` | PASS |
| 删除后状态回收（Deletion Recovery） | `MedalDistributionIntegrationTest` | `shouldRevokeRecordsWhenMedalDeleted` | PASS |

### Validation Result

- 执行命令：`mvn clean test`
- 结果：`BUILD SUCCESS`
