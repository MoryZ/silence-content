package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isNotBlank(poetryLearningContent.getImageUrl())) {
            var fileKey = StringUtils.substringBefore( poetryLearningContent.getImageUrl(), "-");
            var filename = StringUtils.substringAfter( poetryLearningContent.getImageUrl(), "-");
            var presignedObjectUrl = minioTemplate.getInternetUrl(fileKey, filename);
            poetryLearningContent.setImageUrl(presignedObjectUrl);
        }

        if (StringUtils.isNotBlank(poetryLearningContent.getAudioUrl())) {
            var fileKey = StringUtils.substringBefore( poetryLearningContent.getAudioUrl(), "-");
            var filename = StringUtils.substringAfter( poetryLearningContent.getAudioUrl(), "-");
            var presignedObjectUrl = minioTemplate.getInternetUrl(fileKey, filename);
            poetryLearningContent.setAudioUrl(presignedObjectUrl);
        }
        return poetryLearningContent;
    }
}
