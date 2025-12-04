package com.old.silence.content.code.generator.dto;

/**
 * 批量生成结果
 *
 * @author moryzang
 */
public class BatchGenerationResult {

    private boolean success;
    private String message;
    private int totalTables;
    private int successCount;
    private int failureCount;

    public static BatchGenerationResult success(int total, int success, int failure) {
        BatchGenerationResult result = new BatchGenerationResult();
        result.success = true;
        result.message = String.format("批量生成完成：总计 %d 张表，成功 %d 张，失败 %d 张", 
                total, success, failure);
        result.totalTables = total;
        result.successCount = success;
        result.failureCount = failure;
        return result;
    }

    public static BatchGenerationResult failure(String message) {
        BatchGenerationResult result = new BatchGenerationResult();
        result.success = false;
        result.message = message;
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalTables() {
        return totalTables;
    }

    public void setTotalTables(int totalTables) {
        this.totalTables = totalTables;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
}
