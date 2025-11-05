package com.old.silence.content.application.api.dto;

import java.math.BigInteger;

/**
 * PoetryCategory查询对象
 */
public class PoetryCategoryApplicationQuery {
    private String name;
    private String code;
    private String icon;
    private Long sortOrder;
    private BigInteger parentId;
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