package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;

import java.math.BigInteger;

/**
 * UserInteractionLog命令对象
 */
public class UserInteractionLogCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger resourceId;
    @NotNull
    private ResourceType resourceType;
    @NotNull
    private InteractionType interactionType;

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(BigInteger resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(InteractionType interactionType) {
        this.interactionType = interactionType;
    }
}