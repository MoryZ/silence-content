package com.old.silence.content.console.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;


/**
 * PoetryGrade视图接口
 */
@ProjectedPayload
public interface PoetryGradeConsoleView extends AuditableView {
    BigInteger getId();

    String getCode();

    String getName();

    String getDescription();

}