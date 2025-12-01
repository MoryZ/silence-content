package com.old.silence.content.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;

/**
 * @author moryzang
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = ContentArticleCommand.class, name = "1"),
        @JsonSubTypes.Type(value = ContentVideoCommand.class, name = "2"),
        @JsonSubTypes.Type(value = ContentCommonCommand.class, names = {"3", "4", "5", "6"}),
})
public class ContentCommand {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    private ContentType type;

    private ContentStatus status;

    private String contentCode;

    private Instant publishedAt;

    private String author;

    @NotNull
    private CoverImageReferenceMode coverImageReferenceMode;
    @NotBlank
    private String coverImageReference;

    @NotNull
    private ContentReferenceMode contentReferenceMode;
    @NotBlank
    private String contentReference;

    @NotBlank
    private String keywords;

    @NotNull
    private BigInteger parentId;

    @NotNull
    private BigInteger rootId;

    private Boolean stickyTop;

    private Instant stickyTopAt;

    private Map<String, Object> attributes;

    private Instant expiredAt;

    private List<BigInteger> tagIds;

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

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
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

    public CoverImageReferenceMode getCoverImageReferenceMode() {
        return coverImageReferenceMode;
    }

    public void setCoverImageReferenceMode(CoverImageReferenceMode coverImageReferenceMode) {
        this.coverImageReferenceMode = coverImageReferenceMode;
    }

    public String getCoverImageReference() {
        return coverImageReference;
    }

    public void setCoverImageReference(String coverImageReference) {
        this.coverImageReference = coverImageReference;
    }

    public ContentReferenceMode getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(ContentReferenceMode contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public String getContentReference() {
        return contentReference;
    }

    public void setContentReference(String contentReference) {
        this.contentReference = contentReference;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getRootId() {
        return rootId;
    }

    public void setRootId(BigInteger rootId) {
        this.rootId = rootId;
    }

    public Boolean getStickyTop() {
        return stickyTop;
    }

    public void setStickyTop(Boolean stickyTop) {
        this.stickyTop = stickyTop;
    }

    public Instant getStickyTopAt() {
        return stickyTopAt;
    }

    public void setStickyTopAt(Instant stickyTopAt) {
        this.stickyTopAt = stickyTopAt;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public List<BigInteger> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<BigInteger> tagIds) {
        this.tagIds = tagIds;
    }
}
