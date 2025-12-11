package com.old.silence.content.code.generator.vo;

/**
 * 代码生成结果
 *
 * @author moryzang
 */
public class GenerationResult {
    private boolean success;
    private String message;

    public static GenerationResult success(String message) {
        GenerationResult result = new GenerationResult();
        result.success = true;
        result.message = message;
        return result;
    }

    public static GenerationResult failure(String message) {
        GenerationResult result = new GenerationResult();
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
}
