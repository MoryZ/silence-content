package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.GradeLevel;
import com.old.silence.data.commons.domain.AuditableView;


/**
 * PoetryUser视图接口
 */
@ProjectedPayload
public interface PoetryUserApplicationView extends AuditableView {
    BigInteger getId();

    String getOpenid();

    String getNickname();

    String getAvatarUrl();

    String getPhone();

    GradeLevel getGradeLevel();

    Long getStudyGoalDaily();

}