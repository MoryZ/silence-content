package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.old.silence.validation.group.UpdateValidation;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class ContentVideoConsoleCommand extends ContentConsoleCommand {

    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;

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


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

}
