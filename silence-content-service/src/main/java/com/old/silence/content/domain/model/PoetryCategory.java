package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;
import com.old.silence.data.commons.domain.AbstractAuditable;

import java.math.BigInteger;
@Entity
public class PoetryCategory extends AbstractAuditable<BigInteger> {
    private String name;
    private String code;
    private String icon;
    private Long sortOrder;
    private BigInteger parentId;


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
}