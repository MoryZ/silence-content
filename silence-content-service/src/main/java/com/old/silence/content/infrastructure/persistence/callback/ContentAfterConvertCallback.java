package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.content.domain.model.Content;

/**
 * @author moryzang
 */
@Component
public class ContentAfterConvertCallback implements AfterConvertCallback<Content> {

    private final MinioTemplate minioTemplate;

    public ContentAfterConvertCallback(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @Override
    public Content onAfterConvert(Content content) {
        if (ContentReferenceMode.OSS_URL.equals(content.getContentReferenceMode())) {
            content.setContentReference(minioTemplate.getInternetUrl(content.getContentReference()));
        }

        if (CoverImageReferenceMode.OSS.equals(content.getCoverImageReferenceMode())) {
            content.setCoverImageReference(minioTemplate.getInternetUrl(content.getCoverImageReference()));
        }
        return content;
    }
}
