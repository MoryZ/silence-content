package com.old.silence.content.domain.enums;

import java.util.Optional;

import com.old.silence.core.enums.DescribedEnumValue;
import com.old.silence.core.enums.EnumValueFactory;

/**
 * @author moryzang
 */
public enum ContentBusinessStatus implements DescribedEnumValue<Byte> {
    DEFAULT(0, "默认值"),
    NOT_STARTED(2000, "未开始"),
    LIVING(2001, "直播中"),
    STOPPED(2002, "已结束"),
    PLAYBACK_ING(2003, "回访中"),
    INVALID_LIVE(2004, "无效直播"),

    ;

    private final Byte value;
    private final String description;

    ContentBusinessStatus(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    public static Optional<ContentBusinessStatus> convertToContentBusinessStatus(ContentType contentType) {
        return convertToContentBusinessStatus(contentType, null);
    }

    public static Optional<ContentBusinessStatus> convertToContentBusinessStatus(ContentType contentType, DescribedEnumValue<Byte> value) {
        if (value == null) {
            return Optional.of(DEFAULT);
        }
        int resultValue = contentType.getValue() * 1000 + (int) value.getValue();
        return EnumValueFactory.get(ContentBusinessStatus.class, resultValue);
    }


    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
