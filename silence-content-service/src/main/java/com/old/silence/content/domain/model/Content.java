package com.old.silence.content.domain.model;

import com.fasterxml.jackson.core.io.ContentReference;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.data.commons.domain.AbstractAuditable;

import javax.persistence.Entity;
import java.math.BigInteger;
import java.time.Instant;

/**
 * @author MurrayZhang
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

    private ContentReference contentReference;

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

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
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

    public ContentReferenceMode getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(ContentReferenceMode contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public ContentReference getContentReference() {
        return contentReference;
    }

    public void setContentReference(ContentReference contentReference) {
        this.contentReference = contentReference;
    }
}
