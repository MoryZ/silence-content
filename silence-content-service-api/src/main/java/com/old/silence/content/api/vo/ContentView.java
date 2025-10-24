package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentView {

    BigInteger getId();

    String getTitle();
}
