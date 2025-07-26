package com.old.silence.content.infrastructure.persistence.callback;

import com.old.silence.content.domain.model.Content;
import com.old.silence.content.util.ContentCodeUtils;
import com.old.silence.data.jdbc.core.mapping.event.BeforeInsertCallback;

/**
 * @author MurrayZhang
 * @Description
 */
public class ContentBeforeInsertCallback implements BeforeInsertCallback<Content> {

    @Override
    public Content onBeforeInsert(Content content) {
        content.setContentCode(ContentCodeUtils.generateCode(16));
        return content;
    }
}
