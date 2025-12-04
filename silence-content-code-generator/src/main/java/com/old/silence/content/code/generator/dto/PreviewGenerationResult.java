package com.old.silence.content.code.generator.dto;

import java.util.List;
import java.util.Map;

public class PreviewGenerationResult {
    private Map<String, List<String>> filesByModule;

    public PreviewGenerationResult() {}
    public PreviewGenerationResult(Map<String, List<String>> filesByModule) {
        this.filesByModule = filesByModule;
    }

    public Map<String, List<String>> getFilesByModule() { return filesByModule; }
    public void setFilesByModule(Map<String, List<String>> filesByModule) { this.filesByModule = filesByModule; }

    public static PreviewGenerationResult of(Map<String, List<String>> filesByModule) {
        return new PreviewGenerationResult(filesByModule);
    }
}
