package com.old.silence.content.domain.model.support;

import java.math.BigInteger;

import org.springframework.data.domain.Persistable;
import com.old.silence.content.domain.model.Content;

/**
 * @author moryzang
 */
public interface ContentAccessor extends Persistable<BigInteger> {

    void setId(BigInteger id);

    Content getContent();
}
