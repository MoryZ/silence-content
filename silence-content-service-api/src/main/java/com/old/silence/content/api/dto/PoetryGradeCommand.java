package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
/**
* PoetryGrade命令对象
*/
public class PoetryGradeCommand {
    @NotBlank
    @Size(max = 20)
    private String code;
    @NotBlank
    @Size(max = 20)
    private String name;
    private String description;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}