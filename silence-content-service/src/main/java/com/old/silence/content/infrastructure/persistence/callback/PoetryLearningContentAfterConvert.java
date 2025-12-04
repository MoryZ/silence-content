package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.PoetryLearningContent;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class PoetryLearningContentAfterConvert implements AfterConvertCallback<PoetryLearningContent> {


    private final FileStorageFactory fileStorageFactory;

    public PoetryLearningContentAfterConvert(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public PoetryLearningContent onAfterConvert(PoetryLearningContent poetryLearningContent) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        if (StringUtils.isNotBlank(poetryLearningContent.getImageUrl())) {
            var presignedObjectUrl = storageTemplate.getPreviewUrl(poetryLearningContent.getImageUrl());
            poetryLearningContent.setImageUrl(presignedObjectUrl);
        }

        if (StringUtils.isNotBlank(poetryLearningContent.getAudioUrl())) {
            var presignedObjectUrl = storageTemplate.getPreviewUrl(poetryLearningContent.getAudioUrl());
            poetryLearningContent.setAudioUrl(presignedObjectUrl);
        }
        return poetryLearningContent;
    }
}
