package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * @author MurrayZhang
 */
public class BookQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BookStatus status;

    @RelationalQueryProperty(name = "publishedAt", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant publishedAtStart;

    @RelationalQueryProperty(name = "publishedAt",type = Part.Type.LESS_THAN_EQUAL)
    private Instant publishedAtEnd;

    @RelationalQueryProperty(name = "bookContentTags.tagId", type = Part.Type.IN)
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
