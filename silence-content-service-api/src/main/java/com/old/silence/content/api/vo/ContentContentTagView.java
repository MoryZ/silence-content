package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentContentTagView {

    BigInteger getId();

    BigInteger getContentId();

    BigInteger getTagId();

    ContentTagView getContentTag();
}
