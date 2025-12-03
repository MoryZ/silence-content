package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.PoetryCategory;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class PoetryCategoryAfterConvert implements AfterConvertCallback<PoetryCategory> {


    private final FileStorageFactory fileStorageFactory;

    public PoetryCategoryAfterConvert(FileStorageFactory cosTemplate) {
        this.fileStorageFactory = cosTemplate;
    }

    @Override
    public PoetryCategory onAfterConvert(PoetryCategory poetryCategory) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        if (StringUtils.isNotBlank(poetryCategory.getIcon())) {
            var presignedObjectUrl = storageTemplate.getPreviewUrl(poetryCategory.getIcon());
            poetryCategory.setIcon(presignedObjectUrl);

        }
        return poetryCategory;
    }
}
