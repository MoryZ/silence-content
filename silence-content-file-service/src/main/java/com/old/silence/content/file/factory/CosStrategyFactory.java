package com.old.silence.content.file.factory;

import java.io.InputStream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.cos.CosTemplate;
import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
@Component
public class CosStrategyFactory implements FileStorageStrategy {
    private final CosTemplate cosTemplate;

    public CosStrategyFactory(CosTemplate cosTemplate) {
        this.cosTemplate = cosTemplate;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.COS;
    }

    @Override
    public String upload(String filename, String content) {
        return cosTemplate.upload(filename, content);
    }

    @Override
    public String upload(String filename, InputStream inputStream) {
        return cosTemplate.upload(filename, inputStream);
    }

    @Override
    public InputStream downloadFile(String fileKey,  String filename) {
        return cosTemplate.download(fileKey);
    }

    @Override
    public void deleteFile(String fileKey) {
        cosTemplate.delete(fileKey);
    }

    @Override
    public String getPreviewUrl(String fileKey) {
        return cosTemplate.getInternetUrl(fileKey);
    }

    @Override
    public boolean fileKeyExists(String fileKey) {
        return cosTemplate.hasKey(fileKey);
    }
}
