package com.old.silence.content.console.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;

/**
 * @author MurrayZhang
 */
public interface BookContentTagConsoleView {

    BigInteger getBookId();

    BigInteger getTagId();
}
