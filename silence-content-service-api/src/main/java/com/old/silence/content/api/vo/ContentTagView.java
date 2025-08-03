package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentTagView extends AuditableView {

    BigInteger getId();

    String getName();

    String getCode();

    ContentTagType getType();

    BigInteger getParentId();

    Long getSort();

    String getIconReference();

    Boolean enabled();
}
