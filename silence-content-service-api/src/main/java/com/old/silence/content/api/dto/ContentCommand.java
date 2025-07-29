package com.old.silence.content.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;

/**
 * @author MurrayZhang
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = ContentArticleCommand.class, name = "1"),
        @JsonSubTypes.Type(value = ContentLiveCommand.class, name = "2"),
        @JsonSubTypes.Type(value = ContentVideoCommand.class, name = "3"),
        @JsonSubTypes.Type(value = ContentCommonCommand.class, names = {"4", "5", "6", "8", "9", "10"}),
        @JsonSubTypes.Type(value = ContentProductTermCommand.class, name = "7"),
})
public class ContentCommand {
    @NotBlank
    @Size(max = 100)
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
