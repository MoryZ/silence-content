package com.old.silence.content.file.factory;


import java.util.EnumMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import com.old.silence.content.file.config.FileStorageConfigurations;
import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
@Component
public class FileStorageFactory {

    private final StorageType currentStorageType;

    private final EnumMap<StorageType, FileStorageStrategy> storageStrategyEnumMap =
            new EnumMap<>(StorageType.class);


    public FileStorageFactory(FileStorageConfigurations fileStorageConfigurations,
                              ObjectProvider<FileStorageStrategy> objectProvider) {
        this.currentStorageType = fileStorageConfigurations.getType();
        var map = objectProvider.stream()
                .collect(Collectors.toMap(FileStorageStrategy::getStorageType, Function.identity()));
        storageStrategyEnumMap.putAll(map);
    }

    public FileStorageStrategy getStorageTemplate() {
        FileStorageStrategy strategy = storageStrategyEnumMap.get(currentStorageType);
        if (strategy == null) {
            throw new IllegalStateException("No storage strategy found for storageType: " + currentStorageType);
        }
        return strategy;
    }

    /**
     * 根据类型获取指定的存储策略
     */
    public FileStorageStrategy getStorageTemplate(StorageType storageType) {
        FileStorageStrategy strategy = storageStrategyEnumMap.get(storageType);
        if (strategy == null) {
            throw new IllegalStateException(
                    "No storage strategy found for storageType: " + storageType);
        }
        return strategy;
    }


}
