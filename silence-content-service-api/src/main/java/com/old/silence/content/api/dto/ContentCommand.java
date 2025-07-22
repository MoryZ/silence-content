package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.old.silence.content.domain.enums.ContentStatus;

/**
 * @author MurrayZhang
 */
public class ContentCommand {
    @NotBlank
    @Size(max = 100)
    private String title;

    private ContentStatus status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }
}
