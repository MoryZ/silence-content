package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentArticle;

/**
 * @author moryzang
 */
@Component
public class ContentArticleAfterConvertCallback implements AfterConvertCallback<ContentArticle> {

    private final MinioTemplate minioTemplate;

    public ContentArticleAfterConvertCallback(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @Override
    public ContentArticle onAfterConvert(ContentArticle contentArticle) {
        var content = contentArticle.getContent();
        if (ContentReferenceMode.OSS_URL.equals(content.getContentReferenceMode())) {
            content.setContentReference(minioTemplate.getInternetUrl(content.getContentReference()));
        }

        if (CoverImageReferenceMode.OSS.equals(content.getCoverImageReferenceMode())) {
            content.setCoverImageReference(minioTemplate.getInternetUrl(content.getCoverImageReference()));
        }

        return contentArticle;
    }
}
