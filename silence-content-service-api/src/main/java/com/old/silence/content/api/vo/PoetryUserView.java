package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;


/**
 * PoetryUser视图接口
 */
@ProjectedPayload
public interface PoetryUserView extends AuditableView {
    BigInteger getId();

    String getOpenid();

    String getNickname();

    String getAvatarUrl();

    Long getGradeLevel();

    Long getStudyGoalDaily();

}