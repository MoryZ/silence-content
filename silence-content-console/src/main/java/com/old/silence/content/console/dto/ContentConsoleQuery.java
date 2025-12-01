package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.ContentType;

import java.time.Instant;

/**
 * @author moryzang
 */
public class ContentConsoleQuery {

    private String title;

    private ContentType type;

    private String author;

    private Instant publishedAtStart;

    private Instant publishedAtEnd;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
