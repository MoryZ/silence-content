package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum CoverImageReferenceMode implements DescribedEnumValue<Byte> {

    OSS(1, "OSS链接"),
    EXTERNAL_LINK(2, "外部链接"),
    ;

    private final Byte value;
    private final String description;

    CoverImageReferenceMode(int value, String description) {
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
