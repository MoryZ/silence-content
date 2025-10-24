package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

import com.old.silence.validation.group.UpdateValidation;

/**
 * @author moryzang
 */
public class ContentCommonCommand extends ContentCommand {

    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
