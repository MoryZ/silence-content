package com.old.silence.content.infrastructure.persistence.callback;

import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.PoetryLearningContent;
import com.old.silence.content.file.factory.FileStorageFactory;
import com.old.silence.content.file.factory.FileStorageStrategy;

/**
 * @author moryzang
 */
@Component
public class PoetryLearningContentAfterConvert implements AfterConvertCallback<PoetryLearningContent> {


    private final FileStorageFactory fileStorageFactory;

    public PoetryLearningContentAfterConvert(FileStorageFactory cosTemplate) {
        this.fileStorageFactory = cosTemplate;
    }

    @Override
    public PoetryLearningContent onAfterConvert(PoetryLearningContent poetryLearningContent) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
      /*  if (StringUtils.isNotBlank(poetryLearningContent.getImageUrl())) {
            var presignedObjectUrl = minioTemplate.getInternetUrl(poetryLearningContent.getImageUrl());
            poetryLearningContent.setImageUrl(presignedObjectUrl);
        }

        if (StringUtils.isNotBlank(poetryLearningContent.getAudioUrl())) {
            var presignedObjectUrl = minioTemplate.getInternetUrl(poetryLearningContent.getAudioUrl());
            poetryLearningContent.setAudioUrl(presignedObjectUrl);
        }*/
        return poetryLearningContent;
    }
}
