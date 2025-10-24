package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * PoetryUser命令对象
 */
public class PoetryUserConsoleCommand {
    @NotBlank
    @Size(max = 64)
    private String openid;
    private String nickname;
    private String avatarUrl;
    @NotNull
    private Long gradeLevel;
    private Long studyGoalDaily;

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getGradeLevel() {
        return this.gradeLevel;
    }

    public void setGradeLevel(Long gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Long getStudyGoalDaily() {
        return this.studyGoalDaily;
    }

    public void setStudyGoalDaily(Long studyGoalDaily) {
        this.studyGoalDaily = studyGoalDaily;
    }
}