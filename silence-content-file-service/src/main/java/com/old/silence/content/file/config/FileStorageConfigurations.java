package com.old.silence.content.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
@ConfigurationProperties(FileStorageConfigurations.PREFIX)
public class FileStorageConfigurations {
    public static final String PREFIX = "file.storage";

    private StorageType type;

    public StorageType getType() {
        return type;
    }

    public void setType(StorageType type) {
        this.type = type;
    }
}
