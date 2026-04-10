# Tasks: tournament_score_record CRUD

## Checklist

- [ ] 新增 `TournamentScoreRecordService` 接口（silence-content-service-api）
- [ ] 新增 `TournamentScoreRecordClient` FeignClient（silence-content-service-api）
- [ ] 新增 `TournamentScoreRecordCommand` 创建/更新 DTO（silence-content-service-api）
- [ ] 新增 `TournamentScoreRecordQuery` 查询 DTO（silence-content-service-api）
- [ ] 新增 `TournamentScoreRecordView` 投影接口（silence-content-service-api）
- [ ] 新增 `TournamentScoreRecordDao` MyBatis DAO（silence-content-service）
- [ ] 新增 `TournamentScoreRecordRepository` 仓储接口（silence-content-service）
- [ ] 新增 `TournamentScoreRecordMyBatisRepository` 仓储实现（silence-content-service）
- [ ] 新增 `TournamentScoreRecordResource` REST 控制器（silence-content-service）
- [ ] 新增 Liquibase 变更集：创建 `tournament_score_record` 表 + `idx_event_participant` 索引
- [ ] 补充创建得分记录集成测试
- [ ] 补充按参赛单位分页查询测试
