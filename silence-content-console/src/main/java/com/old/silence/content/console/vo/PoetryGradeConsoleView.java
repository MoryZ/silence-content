package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
 * PoetryGrade视图接口
 */
public interface PoetryGradeConsoleView extends AuditableView {
    BigInteger getId();

    String getCode();

    String getName();

    String getDescription();

    Boolean getEnabled();

}