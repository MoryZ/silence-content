package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;

/**
 * PoetryUserStudyNote命令对象
 */
public class PoetryUserStudyNoteConsoleCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger contentId;
    @NotBlank
    @Size(max = 65535)
    private String noteContent;
    private String tags;
    private Boolean disclosure;

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

    public String getNoteContent() {
        return this.noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getDisclosure() {
        return disclosure;
    }

    public void setDisclosure(Boolean disclosure) {
        this.disclosure = disclosure;
    }
}