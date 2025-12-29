package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public class TreeVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger value;

    private String title;

    private BigInteger parentId;

    private List<TreeVo> children;

    public TreeVo(BigInteger value, String title, BigInteger parentId) {
        this.value = value;
        this.title = title;
        this.parentId = parentId;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }
}
