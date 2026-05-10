package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;

import java.math.BigInteger;

/**
 * 互动累计同步命令
 */
public class ContentInteractionAccumulationCommand {

    @NotNull
    private BigInteger resourceId;

    @NotNull
    private ResourceType resourceType;

    @NotNull
    private InteractionType interactionType;

    @NotNull
    private BigInteger accumulation;

    public BigInteger getResourceId() {
        return resourceId;
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

    public BigInteger getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(BigInteger accumulation) {
        this.accumulation = accumulation;
    }
}