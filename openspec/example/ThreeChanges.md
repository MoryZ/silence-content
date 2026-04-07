## ADDED Requirements

### Requirement: Theme Switching
系统 MUST 提供深色/浅色主题切换功能。
系统 SHOULD 支持跟随操作系统主题设置。

#### Scenario: Manual Theme Switch
Given 用户当前使用浅色主题
When 用户点击主题切换开关
Then 界面切换为深色主题
And 选择结果持久化到 localStorage

## MODIFIED Requirements

### Requirement: Page Background (MODIFIED)
- 原：系统 MUST 使用固定白色背景（#FFFFFF）
- 新：系统 MUST 根据当前主题设置显示对应的背景色

## REMOVED Requirements

### Requirement: Fixed Color Scheme (REMOVED)
- 原：系统 MUST 使用预设的固定配色方案
- 原因：被新的主题系统取代
