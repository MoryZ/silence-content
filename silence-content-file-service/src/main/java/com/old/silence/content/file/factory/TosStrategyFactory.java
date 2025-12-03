package com.old.silence.content.file.factory;

import java.io.InputStream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.tos.TosTemplate;
import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "TOS")
public class TosStrategyFactory implements FileStorageStrategy {
    private final TosTemplate tosTemplate;

    public TosStrategyFactory(TosTemplate tosTemplate) {
        this.tosTemplate = tosTemplate;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.TOS;
    }

    @Override
    public String upload(String filename, String content) {
        return tosTemplate.upload(filename, content);
    }

    @Override
    public String upload(String filename, InputStream inputStream) {
        return tosTemplate.upload(filename, inputStream);
    }

    @Override
    public InputStream downloadFile(String fileKey,  String filename) {
        return tosTemplate.download(fileKey);
    }

    @Override
    public void deleteFile(String fileKey) {
        tosTemplate.delete(fileKey);
    }

    @Override
    public String getPreviewUrl(String fileKey) {
        return tosTemplate.getInternetUrl(fileKey);
    }

    @Override
    public boolean fileKeyExists(String fileKey) {
        return tosTemplate.hasKey(fileKey);
    }
}
