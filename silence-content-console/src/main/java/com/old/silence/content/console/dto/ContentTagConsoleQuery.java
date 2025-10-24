package com.old.silence.content.console.dto;


import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentTagType;

/**
 * @author moryzang
 */
public class ContentTagConsoleQuery {

    private ContentTagType type;

    private String name;

    private String code;

    private BigInteger parentId;

    private Boolean enabled;


    public ContentTagType getType() {
        return type;
    }

    public void setType(ContentTagType type) {
        this.type = type;
    }

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
