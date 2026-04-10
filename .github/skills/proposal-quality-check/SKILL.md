---
name: proposal-quality-check
description: Verify that one or more Proposals are complete, correct, and implementation-ready. Checks Non-Goals, RFC 2119 requirement coverage, Given/When/Then scenario completeness, and traceability back to the original requirements. Use before archiving or handing off to implementation.
license: MIT
compatibility: Works with any OpenSpec Proposal markdown file.
metadata:
  author: moryzang
  version: "1.0"
---

Review one or more Proposal files against a structured quality checklist and produce a remediation report.

---

## When To Use

- 用户写完 Proposal 想知道"写得怎么样"
- 在进入 design / specs 阶段前做质量门禁
- 评审他人写的 Proposal

---

## Input

Either:
- 一个具体的 Proposal 文件路径
- 一个 openspec change 名称（自动扫描其下 proposal.md）
- 粘贴进来的 Proposal 文本

如未提供，询问：`你想检查哪个 Proposal？请提供文件路径或 change 名称。`

---

## Validation Dimensions

### Dimension 1 — Structure（结构完整性）

| 小节 | 是否必须 | 检查内容 |
|---|---|---|
| Purpose | MUST | 包含功能目标 + 非功能目标 |
| Non-Goals | MUST | 至少 2 条，且与 Purpose 形成对称 |
| Requirements | MUST | 每条需求使用 RFC 2119 关键字 |
| Data Schema | MUST（涉及数据变更时） | 字段完整，含类型/nullable/约束 |
| Scenarios | MUST | 至少 1 个正常路径 + 1 个异常路径 |

严重级别：缺失必须小节 → `CRITICAL`

---

### Dimension 2 — Requirements Quality（需求质量）

逐条检查：

- **RFC 2119 强度**：是否使用了正确关键字？是否存在"应该"/"可以"等模糊表达替代？
- **可测试性**：每条需求是否有对应 Scenario？无对应的需求 → `WARNING`
- **矛盾检测**：需求之间是否存在逻辑矛盾（如"终身持有"vs"删除后可重置"）？→ `CRITICAL`
- **遗漏扫描**：对照原始需求列表，是否存在需求被完全未提及？

严重级别参考：矛盾 → `CRITICAL`；无 Scenario 支撑的需求 → `WARNING`；措辞模糊 → `SUGGESTION`

---

### Dimension 3 — Scenario Quality（场景质量）

逐个 Scenario 检查：

- **格式**：是否包含 Given / When / Then（可含 And）？
- **具体性**：Given 中是否含具体数据（ID、数量、时间等）？是否有占位描述（"某用户"/"某权益"）？
- **可断言性**：Then 的结果是否可被测试工具直接断言？
- **覆盖率**：原始需求中每条规则是否有对应 Scenario？

打分：
- 有占位描述 → `WARNING`
- 无具体数据 → `WARNING`
- Then 不可断言（如"系统处理正确"）→ `WARNING`
- 需求规则无对应 Scenario → `WARNING`（如原始需求完全未出现）→ `CRITICAL`

---

### Dimension 4 — Traceability（需求追踪）

生成"原始需求 → Proposal 内容"映射表：

| 原始需求编号 | 原文摘要 | 覆盖状态 | 覆盖位置 |
|---|---|---|---|
| 规则 1 | 勋章不在列表页展示 | ✅ 已覆盖 | Requirements: Visibility |
| 规则 5 | 删除后历史持有清零 | ⚠️ 场景未完成 | Scenario 占位 |
| 规则 7 | 多任务 OR 下发 | ❌ 未覆盖 | — |

如原始需求未提供：提示用户提供原始需求文本以完成追踪。

---

## Output Format

### 检查报告：`<Proposal 文件名>`

#### 1. 结构完整性
- ✅ / ⚠️ / ❌ 每个必须小节的状态

#### 2. Findings

列出所有问题，按严重级别排序：

```
[CRITICAL] <问题标题>
- 位置：<小节名 + 行号>
- 问题：<具体描述>
- 修复建议：<直接可操作的改法>

[WARNING] ...
[SUGGESTION] ...
```

#### 3. 需求追踪表
（见 Dimension 4 格式）

#### 4. 总结

```
CRITICAL: N 条 — 必须修复后才能进入 design/specs 阶段
WARNING:  N 条 — 建议修复
SUGGESTION: N 条 — 可选优化
整体状态：❌ 未就绪 / ⚠️ 有条件就绪 / ✅ 就绪
```

---

## Guardrails

- 有 CRITICAL 问题时，结论必须为"未就绪"，不得标注为就绪
- 每条 Finding 必须包含修复建议，不允许只描述问题
- 若无法判断（如缺少原始需求），标注"需补充原始需求后再判断"，不猜测
- 多个 Proposal 时逐个报告，最后输出跨 Proposal 的一致性检查（如两个 Proposal 是否存在重叠覆盖）
