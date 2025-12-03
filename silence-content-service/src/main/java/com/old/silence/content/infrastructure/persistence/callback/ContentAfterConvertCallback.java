package com.old.silence.content.infrastructure.persistence.callback;

import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class ContentAfterConvertCallback implements AfterConvertCallback<Content> {

    private final FileStorageFactory fileStorageFactory;

    public ContentAfterConvertCallback(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public Content onAfterConvert(Content content) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        if (ContentReferenceMode.OSS_URL.equals(content.getContentReferenceMode())) {
            content.setContentReference(storageTemplate.getPreviewUrl(content.getContentReference()));
        }

        if (CoverImageReferenceMode.OSS.equals(content.getCoverImageReferenceMode())) {
            content.setCoverImageReference(storageTemplate.getPreviewUrl(content.getCoverImageReference()));
        }
        return content;
    }
}
