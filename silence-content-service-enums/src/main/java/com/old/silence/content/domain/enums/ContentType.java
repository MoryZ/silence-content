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
    VISUALIZATION_PAGE(5, "可视化"),
    QUESTIONNAIRE(6, "问卷"),
    PRODUCT_TERM(7, "产品条款"),
    LOW_CODE(8, "低代码"),
    POSTER(9, "海报"),
    COMMON_PAGE(10, "AC页面"),
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
