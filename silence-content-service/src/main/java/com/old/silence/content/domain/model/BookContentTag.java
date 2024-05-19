package com.old.silence.content.domain.model;

import com.old.silence.data.commons.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;

@Entity
public class BookContentTag extends AbstractAuditable<BigInteger> {
    private BigInteger bookId;

    private BigInteger tagId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}