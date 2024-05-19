package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;
/**
 * @author MurrayZhang
 */
public enum ContentType implements DescribedEnumValue<Byte>{
    ARTICLE(1, "文章"),
    LIVE(2, "直播"),
    VIDEO(3, "视频"),
    PDF(4, "PDF"),
    VISUAL_PAGE(5, "可视化"),
    QUESTIONNAIRE(6, "问卷"),
    POSTER(7, "海报"),
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
