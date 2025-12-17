package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * UserInteractionLog视图接口
 */
public interface UserInteractionLogConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getResourceId();

    ResourceType getResourceType();

    InteractionType getInteractionType();

}