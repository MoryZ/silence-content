---
name: java-code-review-spec
description: Use Java coding specification chapters to perform code review with structured findings and severity.
license: MIT
compatibility: Works with markdown specs and Java projects.
---

Review Java code against the Java specification chapters.

## Spec Source

Primary reference (GitHub):
- `https://github.com/<owner>/<repo>/tree/<branch>/specification/java/`

Optional local mirror (if available):
- `<workspace>/specification/java/`

Configuration note:
- Keep one canonical source in GitHub.
- If repository/branch changes, update only this section.

Chapter map:
1. 命名风格
2. 常量定义
3. 代码格式
4. 方法设计
5. 类设计
6. 基本类型与字符串
7. 日期时间
8. 集合处理
9. 并发处理
10. 控制语句
11. 注释规约
12. 异常处理
13. 日志规约
14. WEB规约
15. 工程规约
16. 单元测试
17. 其他规约

## When To Use

- 用户要求“按 Java 规范审查代码”
- Pull Request / diff 的 Java 代码质量评审
- 重构后做规范一致性检查

## Review Workflow

1. Identify scope
- Prefer changed Java files first.
- If no diff provided, ask for target modules or scan main Java source folders.

2. Load relevant rules
- Read only chapters related to touched code areas (method/class/web/exception/log/test etc.).
- Avoid full-document dump; extract actionable checks.

3. Execute checks
- Validate naming, method/class design, null/collection/concurrency safety, exception/logging style, and testability.
- For web/controller code, enforce WEB chapter rules.

4. Report findings by severity
- `CRITICAL`: may cause bug, data loss, security or major behavior risk.
- `WARNING`: maintainability/readability/design risk.
- `SUGGESTION`: optional improvements.

5. Provide fix guidance
- For each finding: include file location, violated rule keyword, risk, and concrete fix.

## Output Format

Use this structure:

### Findings
1. [SEVERITY] title
- Location: file + line
- Rule: chapter + short rule keyword
- Risk: what can go wrong
- Fix: specific change suggestion

### Residual Risks
- Items not fully verifiable (e.g., runtime behavior, missing tests).

### Summary
- Count by severity and overall readiness statement.

## Guardrails

- Findings first, summary second.
- No vague comments without actionable fixes.
- Do not request broad rewrites when local fix is enough.
- If evidence is insufficient, mark as uncertainty instead of over-asserting.
