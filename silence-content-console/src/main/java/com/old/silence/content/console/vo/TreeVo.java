package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public class TreeVo {
    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    private BigInteger id;

    private String title;

    private boolean isLeaf;

    private List<TreeVo> children;

    public TreeVo(BigInteger id, String title) {
        this.id = id;
        this.title = title;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }
}
