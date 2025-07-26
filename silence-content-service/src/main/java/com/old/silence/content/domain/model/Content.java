package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.data.commons.domain.AbstractAuditable;

/**
 * @author MurrayZhang
 * @Description
 */

@Entity
public class Content extends AbstractAuditable<BigInteger> {
    private static final long serialVersionUID = 3767967983979698045L;

    private String title;

    private ContentType type;

    private ContentStatus status;

    private Instant publishedAt;

    private String author;

    private String contentCode;

    private String coverImageReference;

    private CoverImageReferenceMode coverImageReferenceMode;

    private ContentReferenceMode contentReferenceMode;

    private String contentReference;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentReference() {
        return contentReference;
    }

    public void setContentReference(String contentReference) {
        this.contentReference = contentReference;
    }

    public ContentReferenceMode getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(ContentReferenceMode contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public String getCoverImageReference() {
        return coverImageReference;
    }

    public void setCoverImageReference(String coverImageReference) {
        this.coverImageReference = coverImageReference;
    }

    public CoverImageReferenceMode getCoverImageReferenceMode() {
        return coverImageReferenceMode;
    }

    public void setCoverImageReferenceMode(CoverImageReferenceMode coverImageReferenceMode) {
        this.coverImageReferenceMode = coverImageReferenceMode;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

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
}
