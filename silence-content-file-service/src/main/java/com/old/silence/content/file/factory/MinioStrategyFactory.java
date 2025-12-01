package com.old.silence.content.file.factory;

import java.io.InputStream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "MINIO")
public class MinioStrategyFactory implements FileStorageStrategy {
    private final MinioTemplate minioTemplate;

    public MinioStrategyFactory(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.MINIO;
    }

    @Override
    public String upload(String filename, String content) {
        return minioTemplate.upload(filename, content);
    }

    @Override
    public String upload(String filename, InputStream inputStream) {
        return minioTemplate.upload(filename, inputStream);
    }

    @Override
    public InputStream downloadFile(String fileKey,  String filename) {
        return minioTemplate.download(fileKey, filename);
    }

    @Override
    public void deleteFile(String fileKey) {
        minioTemplate.delete(fileKey);
    }

    @Override
    public String getPreviewUrl(String fileKey) {
        return minioTemplate.getInternetUrl(fileKey);
    }

    @Override
    public boolean fileKeyExists(String fileKey) {
        return minioTemplate.hasKey(fileKey);
    }
}
