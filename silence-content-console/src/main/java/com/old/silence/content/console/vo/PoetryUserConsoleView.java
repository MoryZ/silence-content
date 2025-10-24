package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.data.commons.domain.AuditableView;


/**
 * PoetryUser视图接口
 */
public interface PoetryUserConsoleView extends AuditableView {
    BigInteger getId();

    String getOpenid();

    String getNickname();

    String getAvatarUrl();

    Long getGradeLevel();

    Long getStudyGoalDaily();

}