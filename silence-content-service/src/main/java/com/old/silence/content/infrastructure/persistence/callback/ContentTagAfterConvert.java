package com.old.silence.content.infrastructure.persistence.callback;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import com.old.silence.content.domain.model.ContentTag;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author MurrayZhang
 */
/*public class ContentTagAfterConvert implements AfterConvertCallback<ContentTag> {


    private final MinioClient minioClient;

    public ContentTagAfterConvert(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @NotNull
    @Override
    public ContentTag onAfterConvert(ContentTag contentTag) {
        if (StringUtils.isNotBlank(contentTag.getIconReference())) {
            String objectName = contentTag.getIconReference();
            GetPresignedObjectUrlArgs args = (GetPresignedObjectUrlArgs) ((GetPresignedObjectUrlArgs.Builder) ((GetPresignedObjectUrlArgs.Builder) GetPresignedObjectUrlArgs.builder()
                    .bucket("silence-content-dmz-stg")).object(objectName)).method(Method.GET).build();

            try {
                var presignedObjectUrl = this.minioClient.getPresignedObjectUrl(args);
                contentTag.setIconReference(presignedObjectUrl);
            } catch (InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException |
                     IOException | NoSuchAlgorithmException | XmlParserException | ServerException |
                     ErrorResponseException e) {
                throw new RuntimeException(e);
            }


        }
        return contentTag;
    }
}*/
