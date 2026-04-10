---
name: mysql-code-review-spec
description: Use MySQL specification chapters to review schema, indexes, SQL statements, and ORM mapping quality.
license: MIT
compatibility: Works with SQL migration files, DDL/DML, and ORM mapping artifacts.
---

Review database-related code against the MySQL specification chapters.

## Spec Source

Primary reference (GitHub):
- `https://github.com/<owner>/<repo>/tree/<branch>/specification/mysql/`

Optional local mirror (if available):
- `<workspace>/specification/mysql/`

Configuration note:
- Keep one canonical source in GitHub.
- If repository/branch changes, update only this section.

Chapter map:
1. 建表规约
2. 索引规约
3. SQL 语句
4. ORM 映射

## When To Use

- 用户要求“按 MySQL 规范审查”
- 审查 DDL / 索引 / SQL 语句 / Mapper 改动
- 发布前数据库变更质量检查

## Review Workflow

1. Identify scope
- Prefer changed SQL files, migration scripts, MyBatis/JPA mapping files.
- Track related Java DAO/Repository usage when needed.

2. Load relevant rules
- Table/column changes -> 建表规约
- Index add/drop/change -> 索引规约
- Query rewrite/performance -> SQL 语句
- Mapper/entity mismatch -> ORM 映射

3. Execute checks
- Check naming/types/default/nullability for schema design.
- Check index necessity, selectivity, redundancy, and leftmost-prefix usage.
- Check SQL for full table scan risk, ambiguous conditions, pagination/order stability, and dangerous updates/deletes.
- Check ORM field/type mapping consistency and potential N+1 / mismatch issues.

4. Report findings by severity
- `CRITICAL`: data correctness/performance regression or unsafe write risk.
- `WARNING`: maintainability/perf risk that should be addressed.
- `SUGGESTION`: optimization opportunities.

5. Provide fix guidance
- For each finding include: affected object (table/index/sql/mapper), violated rule keyword, risk, and concrete fix.

## Output Format

Use this structure:

### Findings
1. [SEVERITY] title
- Location: file + line
- Object: table/index/sql/mapper
- Rule: chapter + short rule keyword
- Risk: what can go wrong
- Fix: specific change suggestion

### Residual Risks
- Items requiring execution plan or production data verification.

### Summary
- Count by severity and release-readiness statement.

## Guardrails

- Findings first, summary second.
- Prefer measurable/performance-oriented evidence.
- Mark assumptions explicitly when EXPLAIN plan or data distribution is unavailable.
- Avoid one-size-fits-all index advice without query context.
