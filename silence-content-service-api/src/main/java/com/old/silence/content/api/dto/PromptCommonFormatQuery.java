package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.PromptFormatType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
 * PromptCommonFormat查询对象
 */
public class PromptCommonFormatQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String formatName;


    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String formatContent;
    private PromptFormatType formatType;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String description;


    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

    public PromptFormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(PromptFormatType formatType) {
        this.formatType = formatType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}