package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum AppliedToScenarioType implements DescribedEnumValue<Byte> {
    ARTICLE(1, "文章"),
    EVENT(2, "活动"),
    QUESTIONNAIRE(3, "问卷"),
    VIDEO(4, "视频"),
    ;

    private final Byte value;
    private final String description;

    AppliedToScenarioType(int value, String description) {
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
