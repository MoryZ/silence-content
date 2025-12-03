package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.ContentTag;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class ContentTagAfterConvert implements AfterConvertCallback<ContentTag> {


    private final FileStorageFactory fileStorageFactory;

    public ContentTagAfterConvert(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public ContentTag onAfterConvert(ContentTag contentTag) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();

        if (StringUtils.isNotBlank(contentTag.getIconReference())) {
            var presignedObjectUrl = storageTemplate.getPreviewUrl(contentTag.getIconReference());
            contentTag.setIconReference(presignedObjectUrl);
        }
        return contentTag;
    }
}
