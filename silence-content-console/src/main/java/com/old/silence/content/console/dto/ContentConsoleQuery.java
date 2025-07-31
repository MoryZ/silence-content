package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.ContentType;

/**
 * @author moryzang
 * @Description
 */
public class ContentConsoleQuery {

    private String title;

    private ContentType type;

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
