package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
* PoetryUserStudyNote查询对象
*/
public class PoetryUserStudyNoteQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger contentId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String tags;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    public BigInteger getContentId() {
        return this.contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }
    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}