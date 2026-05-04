package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.old.silence.content.domain.enums.GradeLevel;
import com.old.silence.content.domain.enums.UserGender;

/**
 * PoetryUser命令对象
 */
public class PoetryUserCommand {
    @NotBlank
    @Size(max = 64)
    private String openid;
    private String unionid;
    private String nickname;
    private String avatarUrl;
    private String phone;
    private UserGender gender;
    private String birthday;
    private String address;
    private GradeLevel gradeLevel;
    private Long studyGoalDaily;

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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