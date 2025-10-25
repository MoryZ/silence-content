package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface BigIdOnlyView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();
}
