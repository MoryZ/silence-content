package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.GradeLevel;

/**
 * PoetryUser查询对象
 */
public class PoetryUserConsoleQuery {
    private String openid;
    private String nickname;
    private String avatarUrl;
    private GradeLevel gradeLevel;
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

    public GradeLevel getGradeLevel() {
        return this.gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Long getStudyGoalDaily() {
        return this.studyGoalDaily;
    }

    public void setStudyGoalDaily(Long studyGoalDaily) {
        this.studyGoalDaily = studyGoalDaily;
    }

}