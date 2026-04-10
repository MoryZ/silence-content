---
name: openspec-config-generator
description: Generate or update openspec/config.yaml for any Java multi-module project by analyzing the real module/package structure and producing an 8-section context + rules set. Use when user asks to create a project governance config, replicate silence-content style config, or standardize OpenSpec rules.
license: MIT
compatibility: Requires openspec CLI and readable project source tree.
metadata:
  author: moryzang
  version: "1.0"
  generatedBy: "gpt-5.3-codex"
---

Generate a production-grade `openspec/config.yaml` adapted to the target repository.

The skill must output a valid YAML file and verify it with `openspec list`.

---

## Input Expectations

User provides one or more of:
- target project path (default current workspace)
- project type (BFF / DDD / CRUD / mixed)
- language and framework hints
- governance preferences (API contract, security, testing, design/spec rules)

If input is incomplete, infer from codebase first, then ask only missing critical items.

---

## Required Workflow

1. Discover project structure from real files
- Read root `pom.xml` and submodule `pom.xml`
- Identify Java package roots under `src/main/java`
- Detect layers from package names and class suffixes/annotations:
  - controller/resource: `@RestController`, `*Resource`, `*Controller`
  - service: `@Service`, `*Service`
  - domain: `domain.model`, `domain.service`, `domain.repository`
  - infrastructure: `infrastructure.persistence`, `infrastructure.*`
  - API contracts: `service-api` module (`*Service`, `*Client`, `dto`, `vo`)

2. Build config context with exactly 8 sections
- 1) 技术栈与基础环境
- 2) 项目包结构与分层职责
- 3) API 契约与安全规范
- 4) 编码风格与工具链
- 5) 依赖与版本绑定
- 6) 特别禁止项
- 7) 项目模块结构
- 8) 实际项目业务模块列表

3. Build `rules` block
- Must include at least:
  - `proposal`
  - `specs`
  - `design`
  - `tasks`
  - `api`
  - `security`
  - `coding`
  - `dependency`
  - `forbidden`

4. Adapt to project reality
- Do not copy BFF-specific constraints if project is not BFF
- Match annotation/package conventions actually used in code
- Keep constraints enforceable by developers
- Keep Chinese wording consistent across sections

5. Validate and fix
- Run:
  - `openspec list`
- If parse fails, fix YAML and rerun until valid

---

## Authoring Constraints

- Output must be valid YAML under `openspec/config.yaml`
- Use concise, auditable constraints (no contradictory rules)
- Never invent non-existent modules/classes
- For path rules:
  - baseline specs: `openspec/specs/[capability]/spec.md`
  - change specs: `openspec/changes/[change-name]/specs/[capability]/spec.md`
- Keep naming examples in kebab-case for capability/change names

---

## Recommended Command Sequence

```bash
# 1) discover modules
rg "<module>" pom.xml

# 2) inspect package patterns
find . -path "*/src/main/java/*" -name "*.java" | head

# 3) detect controllers/services/repositories quickly
rg "@RestController|class .*Resource|@Service|interface .*Repository|@FeignClient" -g"**/*.java"

# 4) write openspec/config.yaml
# 5) validate
openspec list
```

---

## Done Criteria

- `openspec/config.yaml` exists and is valid YAML
- `openspec list` executes successfully
- 8 context sections are present and complete
- rules set is complete and aligned with project architecture
- content is specific to the target repository, not generic boilerplate
