package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ContentStatus implements DescribedEnumValue<Byte> {
    STAGED(1, "草稿"),
    REVIEWING(2, "审核中"),
    APPROVED(3, "审核通过"),
    REJECTED(4, "审核拒绝"),
    PUBLISHED(5, "已上线"),
    UNPUBLISHED(6, "已下线"),
    DELETED(7, "删除"),
    ;

    private final Byte value;
    private final String description;

    ContentStatus(int value, String description) {
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
