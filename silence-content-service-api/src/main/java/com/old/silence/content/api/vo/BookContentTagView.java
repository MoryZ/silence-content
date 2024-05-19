package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;

/**
 * @author MurrayZhang
 */
@ProjectedPayload
public interface BookContentTagView {

    BigInteger getBookId();

    BigInteger getTagId();
}
