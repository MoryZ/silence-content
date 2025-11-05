package com.old.silence.content.application.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
 * PoetryGrade查询对象
 */
public class PoetryGradeApplicationQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String code;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
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