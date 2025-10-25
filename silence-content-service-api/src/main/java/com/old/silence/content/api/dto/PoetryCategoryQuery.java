package com.old.silence.content.api.dto;

import java.math.BigInteger;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * PoetryCategory查询对象
 */
public class PoetryCategoryQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String code;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String icon;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long sortOrder;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger parentId;
    @RelationalQueryProperty(name = "poetryGradePoetryCategories.gradeId", type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger gradeId;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigInteger getParentId() {
        return this.parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getGradeId() {
        return gradeId;
    }

    public void setGradeId(BigInteger gradeId) {
        this.gradeId = gradeId;
    }
}