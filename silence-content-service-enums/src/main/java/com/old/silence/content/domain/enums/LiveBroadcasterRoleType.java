package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum LiveBroadcasterRoleType implements DescribedEnumValue<Byte> {
    ADMIN(1, "管理员"),
    OPERATORS(2, "运营人员"),
    ANCHOR(3, "主播"),
    ;

    private final Byte value;
    private final String description;

    LiveBroadcasterRoleType(int value, String description) {
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
