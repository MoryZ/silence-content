---
name: openspec-propose-entitlement
description: Generate structured Proposals for entitlement/benefit system features. Enforces Non-Goals, RFC 2119 requirements, complete Given/When/Then scenarios, and full field schema. Use when proposing any change to medal, coupon, points, or other entitlement types.
license: MIT
compatibility: Requires openspec CLI. Works alongside openspec-propose.
metadata:
  author: moryzang
  version: "1.0"
---

Generate one or more Proposals for entitlement system features using the project's OpenSpec workflow.

This skill is a **domain overlay** on top of `openspec-propose`. It enforces the entitlement-specific proposal quality rules that the generic skill does not impose.

---

## When To Use

- 用户要新增/修改权益类型（勋章、红包、积分、优惠券等）
- 用户提供了业务规则、接口明细或字段要求，需要拆分为标准 Proposal
- 用户想知道需求是否拆分合理，或直接生成 Proposal 文档

---

## Proposal Split Principle

将权益类需求按以下维度分 Proposal，每个 Proposal 聚焦一个关注点：

| Proposal | 聚焦 | 典型内容 |
|---|---|---|
| A - 管理侧定义 | 权益类型定义、后台配置、数据隔离 | 字段 Schema、UI 过滤、唯一性约束 |
| B - 下发/持有逻辑 | 下发规则、幂等、状态传播、删除恢复 | 领取逻辑、重复下发拦截、删除后重置 |
| C - 查询/展示（可选） | 用户端查询、列表明细 | 仅在查询逻辑独立且复杂时拆出 |

判断是否需要 C：若查询接口只是简单 SELECT，合并入 B；若涉及分页/聚合/权限过滤，独立为 C。

---

## Mandatory Sections Per Proposal

每个 Proposal 必须包含以下小节，缺一不可：

### 1. Purpose（目的）
- 说明这个变更解决什么问题
- 必须包含非功能目标（幂等性、数据一致性、隔离性等）

### 2. Non-Goals（非目标）
- 明确列出本 Proposal **不覆盖**的范围
- 防止评审时范围蔓延

### 3. Requirements（RFC 2119）
- 每条需求必须使用 RFC 2119 关键字：
  - `MUST` / `MUST NOT`：强制行为
  - `SHOULD` / `SHOULD NOT`：推荐行为
  - `MAY`：可选行为
- 格式：`<短标签>: <主语> <RFC-关键字> <动词短语>`
- 示例：`Idempotency: 领取接口 MUST 对同一（权益ID + 对象ID）组合保证幂等`

### 4. Data Schema（仅 Proposal A 类）
- 列出所有字段：字段名、类型、是否 nullable、约束说明
- 审计字段（created_by / created_date / updated_by / updated_date / is_deleted）MUST 显式列出

### 5. Scenarios（Given/When/Then）
- 每条业务规则至少对应 1 个场景
- 场景中的具体数据必须来自原始需求（禁止用"某用户"、"某权益"等占位描述）
- 必须覆盖：正常路径 + 至少1个边界/异常路径
- 格式：
  ```
  #### Scenario: <场景名>
  - **Given** <前置状态，含具体数据>
  - **When** <触发动作>
  - **Then** <可观测结果>
  - **And** <附加断言（可选）>
  ```

---

## Domain-Specific Checklist

生成权益类 Proposal 前，逐条核对：

**隔离性**
- [ ] 新权益类型是否需要在 UI 列表/下拉中隐藏？
- [ ] 枚举值是否需要屏蔽（前端下拉不展示）？

**唯一性**
- [ ] 是否存在全局唯一或分类唯一约束？
- [ ] 唯一键冲突时的行为是否定义？

**下发规则**
- [ ] 是否"一生一次"？重复调用的返回行为是否定义？
- [ ] 是否存在多任务条件 OR 下发场景？如有，是否覆盖？

**状态传播**
- [ ] 编辑后，已持有用户数据是否同步更新？
- [ ] 删除后，已持有用户看到的状态是否定义？
- [ ] 删除后重新添加时，历史持有记录是否清零？

**持有对象**
- [ ] 持有者标识是什么（客户号/会员号/userId）？
- [ ] 是否支持多种会员 type？

**遗漏扫描**
- 生成前将原始需求逐条对比 Scenario 列表，未覆盖的需求用 `> ⚠️ 未覆盖：<规则编号>` 标注

---

## Steps

1. **解析原始需求**
   - 提取业务规则（编号列表）
   - 提取数据字段
   - 提取接口明细
   - 标记每条规则归属哪个 Proposal（A/B/C）

2. **执行 Domain Checklist**
   - 逐项检查上方清单，记录缺失项

3. **生成 Proposal 文件**
   - 按 `openspec-propose` 的文件结构落地
   - 每个 Proposal 完整包含 Purpose / Non-Goals / Requirements / Data Schema / Scenarios
   - Scenario 中出现的数据值必须来自原始需求原文

4. **输出覆盖率报告**
   - 列出"原始需求→Scenario 映射表"
   - 标注未覆盖的规则

---

## Guardrails

- 禁止在 Scenario 中写 TODO / 占位描述；如信息不足，向用户追问后再写
- Non-Goals 必须至少 2 条
- Data Schema 字段必须完整（不能写"……等字段"）
- 若需求存在矛盾（如"可删除"与"终身获得"），必须在 Requirements 前标注矛盾并请用户澄清
