package com.old.silence.content.api.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author MurrayZhang
 */
public class ContentCommand {
    @NotBlank
    @Size(max = 100)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
