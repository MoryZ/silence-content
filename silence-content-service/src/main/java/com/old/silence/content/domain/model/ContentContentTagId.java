package com.old.silence.content.domain.model;


import java.io.Serializable;
import java.math.BigInteger;

public class ContentContentTagId implements Serializable {
    private BigInteger contentId;
    private BigInteger tagId;

    public BigInteger getContentId() {
        return contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }

    public BigInteger getTagId() {
        return tagId;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
    }

}