package com.old.silence.content.domain.model.support;


import java.math.BigInteger;

import com.old.silence.content.domain.model.Content;
import com.old.silence.data.commons.domain.ExplictNewPersistable;

/**
 * @author moryzang
 */
public class ContentCommon implements ExplictNewPersistable<BigInteger>, ContentAccessor {

    private static final long serialVersionUID = -8556205980180706065L;

    private BigInteger id;

    private Content content;

    private boolean newEntity = false;

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    @Override
    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
