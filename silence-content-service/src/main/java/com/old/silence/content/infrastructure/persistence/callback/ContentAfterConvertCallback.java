package com.old.silence.content.infrastructure.persistence.callback;

import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import com.old.silence.content.domain.model.Content;

/**
 * @author moryzang
 */
public class ContentAfterConvertCallback implements AfterConvertCallback<Content> {


    @Override
    public Content onAfterConvert(Content content) {
        return content;
    }
}
