package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;

import java.time.Instant;

/**
 * @author moryzang
 */
public class ContentConsoleCommand {
    private String title;

    private ContentType type;

    private ContentStatus status;

    private Instant publishedAt;

    private String author;

    private String coverImageReference;

    private String contentReference;

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

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
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
}
