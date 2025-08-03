package com.old.silence.content.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigInteger;

@Embeddable
public class BookContentTagId implements Serializable {
    private BigInteger bookId;
    private BigInteger tagId;

    public BigInteger getBookId() {
        return bookId;
    }

    public void setBookId(BigInteger bookId) {
        this.bookId = bookId;
    }

    public BigInteger getTagId() {
        return tagId;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
    }

}