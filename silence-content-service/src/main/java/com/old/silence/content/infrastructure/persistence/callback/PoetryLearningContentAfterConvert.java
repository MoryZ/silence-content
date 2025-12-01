package com.old.silence.content.infrastructure.persistence.callback;

import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.PoetryLearningContent;

/**
 * @author moryzang
 */
@Component
public class PoetryLearningContentAfterConvert implements AfterConvertCallback<PoetryLearningContent> {


    private final MinioTemplate minioTemplate;

    public PoetryLearningContentAfterConvert(MinioTemplate cosTemplate) {
        this.minioTemplate = cosTemplate;
    }

    @Override
    public PoetryLearningContent onAfterConvert(PoetryLearningContent poetryLearningContent) {
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
