package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum BookStatus implements DescribedEnumValue<Byte> {
    STAGED(1, "草稿"),
    REVIEWING(2, "审核中"),
    APPROVED(3, "审核通过"),
    REJECTED(4, "审核通过"),
    PUBLISH(5, "已上线"),
    UNPUBLISH(6, "已下线"),
    ;

    private final Byte value;
    private final String description;

    BookStatus(int value, String description) {
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
