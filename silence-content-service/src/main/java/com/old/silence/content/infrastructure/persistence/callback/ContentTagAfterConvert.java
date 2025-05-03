package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.ContentTag;

/**
 * @author MurrayZhang
 */
@Component
public class ContentTagAfterConvert implements AfterConvertCallback<ContentTag> {


    private final MinioTemplate minioTemplate;

    public ContentTagAfterConvert(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @NotNull
    @Override
    public ContentTag onAfterConvert(ContentTag contentTag) {
        if (StringUtils.isNotBlank(contentTag.getIconReference())) {
            var fileKey = StringUtils.substringBefore( contentTag.getIconReference(), "-");
            var filename = StringUtils.substringAfter( contentTag.getIconReference(), "-");
            var presignedObjectUrl = minioTemplate.getInternetUrl(fileKey, filename);
            contentTag.setIconReference(presignedObjectUrl);

        }
        return contentTag;
    }
}
