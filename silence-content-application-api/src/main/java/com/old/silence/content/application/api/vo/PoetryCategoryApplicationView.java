package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryCategory视图接口
 */
@ProjectedPayload
public interface PoetryCategoryApplicationView extends AuditableView {
    BigInteger getId();

    String getName();

    String getCode();

    String getIcon();

    Long getSortOrder();

    BigInteger getParentId();

}