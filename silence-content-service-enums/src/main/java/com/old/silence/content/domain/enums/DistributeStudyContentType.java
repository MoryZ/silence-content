package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum DistributeStudyContentType implements DescribedEnumValue<Byte>{
    SEQUENTIAL(1, "顺序分配"),
    RANDOM(2, "随机分配"),
    INTERLEAVED(3, "交错分配（适合记忆）"),
    BATCHED(4, "分批分配"),

    ;

    private final Byte value;
    private final String description;

    DistributeStudyContentType(int value, String description) {
        this.value = (byte)value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
