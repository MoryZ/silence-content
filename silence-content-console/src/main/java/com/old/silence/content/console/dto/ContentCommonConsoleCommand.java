package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.validation.group.UpdateValidation;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class ContentCommonConsoleCommand extends ContentConsoleCommand{

    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
