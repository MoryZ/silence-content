package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ContentTagType implements DescribedEnumValue<Byte>{
    NORMAL(1, "分发栏目"),
    ORGANIZATION(2, "机构"),
    INFORMATION_DISCLOSURE(3, "公开信息披露"),
    VISUALIZATION_SCENARIO(4, "内容模板"),
    CONTENT_CATEGORY(5, "内容类型"),
    PRODUCT_TAG(6, "产品标签"),
    CONTENT_TAG(7, "内容标签"),
    DISTRIBUTION_CHANNEL(8, "分发渠道"),
    SYSTEM(9, "系统标签"),
    ;

    private final Byte value;
    private final String description;

    ContentTagType(int value, String description) {
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
