package com.old.silence.content.console.dto;


import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import com.old.silence.content.domain.enums.BookStatus;

/**
 * @author moryzang
 */
public class BookConsoleQuery {

    private String name;

    private BookStatus status;

    private Instant publishedAtStart;

    private Instant publishedAtEnd;

    private List<BigInteger> tagIds;

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

    public Instant getPublishedAtStart() {
        return publishedAtStart;
    }

    public void setPublishedAtStart(Instant publishedAtStart) {
        this.publishedAtStart = publishedAtStart;
    }

    public Instant getPublishedAtEnd() {
        return publishedAtEnd;
    }

    public void setPublishedAtEnd(Instant publishedAtEnd) {
        this.publishedAtEnd = publishedAtEnd;
    }

    public List<BigInteger> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<BigInteger> tagIds) {
        this.tagIds = tagIds;
    }
}
