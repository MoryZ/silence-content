package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;

/**
 * @author MurrayZhang
 * @description
 */
@ProjectedPayload
public interface ContentView {

    BigInteger getId();

    String getTitle();
}
