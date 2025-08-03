package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ContentReferenceMode implements DescribedEnumValue<Byte> {
    OSS_URL(1, "云存储链接"),
    EXTERNAL_LINK(2, "外部链接"),
    ;

    private final Byte value;
    private final String description;

    ContentReferenceMode(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
