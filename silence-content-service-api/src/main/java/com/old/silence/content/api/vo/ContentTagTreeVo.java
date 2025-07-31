package com.old.silence.content.api.vo;

import com.old.silence.content.domain.enums.ContentTagType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public class ContentTagTreeVo {
    private BigInteger id;
    private String name;
    private String code;
    private ContentTagType type;
    private BigInteger parentId;
    private Long sort;
    private List<ContentTagTreeVo> children;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public List<ContentTagTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<ContentTagTreeVo> children) {
        this.children = children;
    }
}
