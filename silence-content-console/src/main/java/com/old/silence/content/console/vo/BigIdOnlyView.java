package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author MurrayZhang
 * @Description
 */
public interface BigIdOnlyView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();
}
