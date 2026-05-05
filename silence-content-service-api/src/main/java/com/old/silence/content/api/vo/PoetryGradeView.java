package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
 * PoetryGrade视图接口
 */
@ProjectedPayload
public interface PoetryGradeView extends AuditableView {
    BigInteger getId();

    String getCode();

    String getName();

    String getDescription();

    Boolean getEnabled();

}