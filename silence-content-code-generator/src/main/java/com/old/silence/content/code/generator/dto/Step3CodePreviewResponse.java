package com.old.silence.content.code.generator.dto;

import java.util.List;
import java.util.Map;

/**
 * 步骤3：代码预览响应（包含导入分析）
 *
 * @author moryzang
 */
public class Step3CodePreviewResponse extends CodePreviewResponse {

    /**
     * 导入建议：文件路径 -> 建议导入的包列表
     */
    private Map<String, List<String>> importSuggestions;

    /**
     * 缺失导入警告：文件路径 -> 警告信息列表
     */
    private Map<String, List<String>> missingImportWarnings;

    /**
     * 排序建议
     */
    private List<String> sortingSuggestions;

    public Map<String, List<String>> getImportSuggestions() {
        return importSuggestions;
    }

    public void setImportSuggestions(Map<String, List<String>> importSuggestions) {
        this.importSuggestions = importSuggestions;
    }

    public Map<String, List<String>> getMissingImportWarnings() {
        return missingImportWarnings;
    }

    public void setMissingImportWarnings(Map<String, List<String>> missingImportWarnings) {
        this.missingImportWarnings = missingImportWarnings;
    }

    public List<String> getSortingSuggestions() {
        return sortingSuggestions;
    }

    public void setSortingSuggestions(List<String> sortingSuggestions) {
        this.sortingSuggestions = sortingSuggestions;
    }
}
