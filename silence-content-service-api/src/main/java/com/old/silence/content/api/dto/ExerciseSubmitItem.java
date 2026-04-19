package com.old.silence.content.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author ZHAOGUANRUI108
 */
public class ExerciseSubmitItem {
    @NotBlank(message = "项目类型不能为空")
    private String type;

    @NotNull(message = "完成数量不能为空")
    private Integer result;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
