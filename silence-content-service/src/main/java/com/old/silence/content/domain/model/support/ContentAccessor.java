package com.old.silence.content.domain.model.support;

import org.springframework.data.domain.Persistable;
import com.old.silence.content.domain.model.Content;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentAccessor extends Persistable<BigInteger> {

    void setId(BigInteger id);

    Content getContent();
}
