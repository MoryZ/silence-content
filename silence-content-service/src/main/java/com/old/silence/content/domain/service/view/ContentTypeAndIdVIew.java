package com.old.silence.content.domain.service.view;

import com.old.silence.content.domain.enums.ContentType;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentTypeAndIdVIew {
    ContentType getType();

    BigInteger getId();
}
