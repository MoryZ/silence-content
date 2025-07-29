package com.old.silence.content.domain.service.view;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentType;

/**
 * @author moryzang
 */
public interface ContentTypeAndIdVIew {
    ContentType getType();

    BigInteger getId();
}
