package com.old.silence.content.api.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentTagType;

/**
 * @author MurrayZhang
 */
public class ContentTagCommand {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 64)
    private String code;

    @NotNull
    private ContentTagType type;

    @NotNull
    private BigInteger parentId;

    private String iconReference;

    @NotNull
    @Min(0)
    @Max(9999)
    private Long sort;

    @NotNull
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
