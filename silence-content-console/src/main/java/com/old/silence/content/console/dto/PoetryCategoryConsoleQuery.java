package com.old.silence.content.console.dto;

import java.math.BigInteger;


/**
 * PoetryCategory查询对象
 */
public class PoetryCategoryConsoleQuery {
    private String name;
    private String code;
    private BigInteger parentId;
    private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}