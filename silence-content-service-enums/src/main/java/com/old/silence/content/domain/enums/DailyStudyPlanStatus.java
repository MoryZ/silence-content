package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum DailyStudyPlanStatus implements DescribedEnumValue<Byte> {
    NOT_STARTED(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    COMPLETED(2, "已完成"),

    ;

    private final Byte value;
    private final String description;

    DailyStudyPlanStatus(int value, String description) {
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
