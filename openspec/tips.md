# OpenSpec 命令速查

## Copilot 斜杠命令

| 命令 | 说明 | 场景 |
|---|---|---|
| `/opsx:propose` | 一步生成完整变更 | 需求清晰 |
| `/opsx:explore` | 探索调研，不产生文件 | 需求模糊、技术选型 |
| `/opsx:apply` | 按任务清单写代码 | 实现阶段 |
| `/opsx:archive` | 归档，合并规格 | 功能完成收尾 |
| `/opsx:new` | 创建变更骨架 | 手动逐步推进 |
| `/opsx:continue` | 生成下一个工件 | 逐步审查 |
| `/opsx:ff` | 快进生成所有工件 | 确认方向后加速 |
| `/opsx:verify` | 三维度验证实现 | 归档前质量检查 |
| `/opsx:sync` | 只同步规格不归档 | 并行变更需引用 |
| `/opsx:bulk-archive` | 批量归档 | 多功能统一收尾 |
| `/opsx:onboard` | 交互式教程 | 新手上手 |

## CLI 命令

| 命令 | 说明 | 场景 |
|---|---|---|
| `openspec list` | 查看活跃变更 | 日常管理 |
| `openspec status` | 查看工件完成度 | 了解当前进度 |
| `openspec view` | 交互式仪表盘 | 浏览变更和规格 |
| `openspec validate` | 验证格式 | 检查规格质量 |