package com.old.silence.content.file.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum StorageType implements DescribedEnumValue<Byte> {
    MINIO(1, "Minio 存储"),
    COS(1, "Cos 存储");

    private final Byte value;
    private final String description;

    StorageType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Byte getValue() {
        return value;
    }
}
