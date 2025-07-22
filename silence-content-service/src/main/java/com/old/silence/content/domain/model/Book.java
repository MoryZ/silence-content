package com.old.silence.content.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.data.commons.domain.AbstractAuditable;

@Entity
public class Book extends AbstractAuditable<BigInteger> {

    private String name;

    private BookStatus status;

    private Instant publishedAt;

    private String author;

    private BigDecimal price;

    @Column(updatable = false)
    private String isbn;

    private String coverImageReference;

    private String contentReference;

    @OneToMany(mappedBy = "book")
    private List<BookContentTag> bookContentTags;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCoverImageReference() {
        return coverImageReference;
    }

    public void setCoverImageReference(String coverImageReference) {
        this.coverImageReference = coverImageReference;
    }

    public String getContentReference() {
        return contentReference;
    }

    public void setContentReference(String contentReference) {
        this.contentReference = contentReference;
    }

    public List<BookContentTag> getBookContentTags() {
        return bookContentTags;
    }

    public void setBookContentTags(List<BookContentTag> bookContentTags) {
        this.bookContentTags = bookContentTags;
    }
}