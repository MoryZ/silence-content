package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.time.Instant;

/**
 * @author moryzang
 */
public class ContentQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String title;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private ContentType type;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String author;

    @RelationalQueryProperty(name = "publishedAt", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant publishedAtStart;

    @RelationalQueryProperty(name = "publishedAt", type = Part.Type.LESS_THAN_EQUAL)
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
