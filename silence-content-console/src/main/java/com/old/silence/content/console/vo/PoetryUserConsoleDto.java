package com.old.silence.content.console.vo;


import com.old.silence.content.domain.enums.GradeLevel;

import java.math.BigInteger;


/**
 * PoetryUser视图接口
 */
public record PoetryUserConsoleDto(BigInteger id, String openid, String nickname, String phone, String avatarUrl,
                                   GradeLevel gradeLevel, Long studyGoalDaily) {
}