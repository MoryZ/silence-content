package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.GradeLevel;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
 * PoetryUser查询对象
 */
public class PoetryUserQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String openid;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String nickname;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String avatarUrl;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private GradeLevel gradeLevel;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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