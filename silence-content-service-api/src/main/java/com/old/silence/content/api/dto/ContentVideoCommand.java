package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author moryzang
 */
public class ContentVideoCommand extends ContentCommand {

    @NotBlank
    @Size(max = 256)
    private String description;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    private String verticalCoverImageReference;

    private String scriptFilename;

    private String scriptFileKey;

    private String videoName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getVerticalCoverImageReference() {
        return verticalCoverImageReference;
    }

    public void setVerticalCoverImageReference(String verticalCoverImageReference) {
        this.verticalCoverImageReference = verticalCoverImageReference;
    }

    public String getScriptFilename() {
        return scriptFilename;
    }

    public void setScriptFilename(String scriptFilename) {
        this.scriptFilename = scriptFilename;
    }

    public String getScriptFileKey() {
        return scriptFileKey;
    }

    public void setScriptFileKey(String scriptFileKey) {
        this.scriptFileKey = scriptFileKey;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
