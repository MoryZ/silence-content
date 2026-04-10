# Specs: tournament_ranking CRUD

## ADDED Requirements

### Requirement: Create tournament_ranking

系统 MUST 支持创建排行榜记录，event_game_id、group_id、participant_id、participant_type、ranking_type 必填。

#### Scenario: Insert Ranking Row

Given 榜单计算已产出结果。

When 写入排名记录。

Then 记录创建成功。

### Requirement: Read tournament_ranking

系统 MUST 支持按 id 查询；SHOULD 支持按 group_id + ranking_type 查询榜单并按 rank_no 排序。

#### Scenario: Query Ranking List

Given 某组榜单已生成。

When 查询 group_id + ranking_type。

Then 返回按 rank_no 升序结果。

### Requirement: Update tournament_ranking

系统 MUST 支持更新 score、rank_no、avatar_url、nickname。

#### Scenario: Correct Rank

Given 某记录排名错误。

When 更新 rank_no。

Then 新排名生效。

### Requirement: Delete tournament_ranking

系统 MUST 使用逻辑删除。

#### Scenario: Logical Delete Ranking

Given 榜单记录存在。

When 删除记录。

Then is_deleted=1。
