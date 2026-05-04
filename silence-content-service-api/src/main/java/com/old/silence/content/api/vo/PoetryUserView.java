package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.GradeLevel;
import com.old.silence.content.domain.enums.UserGender;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.LocalTime;


/**
 * PoetryUser视图接口
 */
@ProjectedPayload
public interface PoetryUserView extends AuditableView {
    BigInteger getId();

    String getOpenid();

    String getNickname();

    String getAvatarUrl();

    String getPhone();

    UserGender getGender();

    String getBirthday();

    String getAddress();

    GradeLevel getGradeLevel();

    Long getStudyGoalDaily();

}