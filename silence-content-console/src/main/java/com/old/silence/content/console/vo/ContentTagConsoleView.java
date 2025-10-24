package com.old.silence.content.console.vo;


import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author moryzang
 */
public interface ContentTagConsoleView extends AuditableView {

    BigInteger getId();

    String getName();

    String getCode();

    ContentTagType getType();

    BigInteger getParentId();

    Long getSort();

    String getIconReference();

    Boolean enabled();
}
