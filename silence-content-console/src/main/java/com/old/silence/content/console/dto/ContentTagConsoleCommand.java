package com.old.silence.content.console.dto;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentTagType;

/**
 * @author moryzang
 */
public class ContentTagConsoleCommand {

    private String name;

    private String code;

    private ContentTagType type;

    private BigInteger parentId;

    private String iconReference;

    private Long sort;

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

    public ContentTagType getType() {
        return type;
    }

    public void setType(ContentTagType type) {
        this.type = type;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getIconReference() {
        return iconReference;
    }

    public void setIconReference(String iconReference) {
        this.iconReference = iconReference;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
