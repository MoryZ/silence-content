package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * UserInteractionLog视图接口
 */
@ProjectedPayload
public interface UserInteractionLogView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getResourceId();

    ResourceType getResourceType();

    InteractionType getInteractionType();

}