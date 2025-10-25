package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.GradeLevel;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
 * PoetryUser视图接口
 */
public interface PoetryUserConsoleView extends AuditableView {
    BigInteger getId();

    String getOpenid();

    String getNickname();

    String getAvatarUrl();

    String getPhone();

    GradeLevel getGradeLevel();

    Long getStudyGoalDaily();

}