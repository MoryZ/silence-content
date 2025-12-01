package com.old.silence.content.console.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.api.ContentClient;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@Service
public class ContentConsoleService {

    private final ContentClient contentClient;
    private final MinioTemplate minioTemplate;

    public ContentConsoleService(ContentClient contentClient,
                                 MinioTemplate minioTemplate) {
        this.contentClient = contentClient;
        this.minioTemplate = minioTemplate;
    }

    public BigInteger create(ContentCommand command) {
        beforeClient(command);
        return contentClient.create(command);
    }

    public void update(BigInteger id, ContentCommand command) {
        beforeClient(command);
        contentClient.update(id, command);
    }


    private void beforeClient(ContentCommand command) {
        if (StringUtils.isNotBlank(command.getCoverImageReference()) && CoverImageReferenceMode.OSS.equals(command.getCoverImageReferenceMode())) {
            command.setCoverImageReference(
                    StringUtils.substringBefore(
                            StringUtils.substringAfterLast(command.getCoverImageReference(), "/"),
                    "?"));
        }

        if (StringUtils.isNotBlank(command.getContentReference()) && ContentReferenceMode.OSS_URL.equals(command.getContentReferenceMode())) {
            var filename = command.getTitle() + ".json";
            var fileKey = minioTemplate.upload(filename, command.getContentReference());
            command.setContentReference(fileKey);
        }
    }

}
