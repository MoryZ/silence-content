# Specs: tournament_score_record CRUD

## ADDED Requirements

### Requirement: Create tournament_score_record

系统 MUST 支持创建得分记录，`eventGameId`、`participantId`、`participantType`、`scoreType`、`score` 为必填字段。

#### Scenario: Record Stage Score

Given 参赛单位已报名，当前场次已结束。

When 调用创建接口，传入 `eventGameId`、`participantId`、`participantType=USER`、`scoreType=STAGE`、`stageNumber`、`score`。

Then 得分记录写入成功，返回新记录 id（BigInteger，序列化为字符串）。

---

### Requirement: Read tournament_score_record by id

系统 MUST 支持按 `id` 查询单条得分记录，不存在时返回 404。

#### Scenario: Get Score Record by ID

Given 目标 id 对应的得分记录存在且未被逻辑删除。

When 按 id 查询。

Then 返回完整得分明细，包含 `scoreType`、`score`、`stageNumber`/`segmentNumber`/`cycleNumber`、`groupId`。

---

### Requirement: Read tournament_score_record by participant

系统 SHOULD 支持按 `eventGameId + participantId` 分页查询；SHOULD 支持可选 `scoreType` 过滤。

#### Scenario: Query Score Records by Participant

Given 参赛单位在该玩法下有多条得分记录。

When 按 `eventGameId + participantId` 分页查询（page=0, size=20）。

Then 返回分页结果，每条包含 `scoreType`、`score`、层级编号字段。

#### Scenario: Filter by Score Type

Given 参赛单位有 STAGE、SEGMENT、CYCLE 多种类型得分。

When 查询时附加 `scoreType=CYCLE` 过滤条件。

Then 仅返回 `scoreType=CYCLE` 的记录，其他类型不出现。

---

### Requirement: Update tournament_score_record

系统 MUST 支持通过 `id` 更新 `score`、`cycleNumber`、`segmentNumber`、`stageNumber`、`groupId`。

#### Scenario: Update Score

Given 得分记录存在（id 已知）。

When 调用更新接口，传入新的 `score` 值。

Then 记录的 `score` 字段更新成功，`updatedBy`/`updatedDate` 同步更新，返回 204 NO_CONTENT。

---

### Requirement: Delete tournament_score_record

系统 MUST 使用逻辑删除，不得物理删除记录。

#### Scenario: Logical Delete Score Record

Given 得分记录存在。

When 调用删除接口。

Then 该记录 `is_deleted=1`；后续按 id 查询返回 404；按参赛单位分页查询该记录不再出现。
