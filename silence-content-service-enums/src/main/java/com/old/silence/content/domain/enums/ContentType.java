package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;
/**
 * @author moryzang
 */
public enum ContentType implements DescribedEnumValue<Byte>{
    ARTICLE(1, "文章"),
    VIDEO(2, "视频"),
    PDF(3, "PDF"),
    VISUALIZATION_PAGE(4, "可视化"),
    QUESTIONNAIRE(5, "问卷"),
    POSTER(6, "海报"),
    ;

    private final Byte value;
    private final String description;

    ContentType(int value, String description) {
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
