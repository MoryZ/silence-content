package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
* PromptCommonFormat查询对象
*/
public class PromptCommonFormatQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String formatName;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String description;


    public String getFormatName() {
        return this.formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}