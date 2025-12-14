## IntelliJ IDEA 隐藏 package-info.java 配置指南

### 方法1：通过文件类型设置（推荐）

1. **打开设置**
   - Mac: `IntelliJ IDEA` → `Preferences` (或 `Cmd+,`)
   - Windows/Linux: `File` → `Settings` (或 `Ctrl+Alt+S`)

2. **导航到文件类型设置**
   - 搜索 "File Types" 或
   - `Editor` → `File Types`

3. **配置忽略规则**
   - 找到 "Ignored Files and Folders" 部分
   - 点击 `+` 按钮
   - 输入: `package-info.java`
   - 点击 OK

### 方法2：快速搜索打开

1. `Cmd+Shift+A` (Mac) / `Ctrl+Shift+A` (Windows/Linux)
2. 搜索 "Ignored Files and Folders"
3. 按照方法1的步骤3继续

### 方法3：修改 IDE 配置文件（高级用户）

编辑 `~/.config/JetBrains/IntelliJIdea*/options/filetypes.xml`：

```xml
<option name="IGNORE_FILES_PATTERN" value="*;*.class;*.o;*.lib;*.exe;*.dll;*.so;*.dylib;*.suo;*.sln;*.sw?;package-info.java" />
```

### 验证配置

配置完成后，在 IntelliJ 中：
- 文件树中不会显示 `package-info.java`
- 但文件仍在磁盘上（Git 可以跟踪）
- JavaDoc 和 Package Info 视图中仍能看到文档

### 撤销配置

如果需要重新显示 `package-info.java`：
- 在 "Ignored Files and Folders" 中删除 `package-info.java` 项
- 重启 IDE
