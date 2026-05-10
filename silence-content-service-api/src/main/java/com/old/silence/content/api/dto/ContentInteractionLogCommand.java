package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;

import java.math.BigInteger;

/**
 * ContentInteractionLog命令对象
 */
public class ContentInteractionLogCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger resourceId;
    @NotNull
    private ResourceType resourceType;
    @NotNull
    private InteractionType interactionType;
    private BigInteger fromUserId;
    private BigInteger parentId;
    private BigInteger rootId;

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

    public BigInteger getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(BigInteger fromUserId) {
        this.fromUserId = fromUserId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getRootId() {
        return rootId;
    }

    public void setRootId(BigInteger rootId) {
        this.rootId = rootId;
    }
}