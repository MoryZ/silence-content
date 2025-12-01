package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.PoetryCategory;

/**
 * @author moryzang
 */
@Component
public class PoetryCategoryAfterConvert implements AfterConvertCallback<PoetryCategory> {


    private final MinioTemplate minioTemplate;

    public PoetryCategoryAfterConvert(MinioTemplate cosTemplate) {
        this.minioTemplate = cosTemplate;
    }

    @Override
    public PoetryCategory onAfterConvert(PoetryCategory poetryCategory) {
        if (StringUtils.isNotBlank(poetryCategory.getIcon())) {
            var presignedObjectUrl = minioTemplate.getInternetUrl(poetryCategory.getIcon());
            poetryCategory.setIcon(presignedObjectUrl);

        }
        return poetryCategory;
    }
}
