package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * PoetryCategory视图接口
 */
@ProjectedPayload
public interface PoetryCategoryView extends AuditableView {
    BigInteger getId();

    String getName();

    String getCode();

    String getIcon();

    Long getSortOrder();

    BigInteger getParentId();

    Boolean getEnabled();

}