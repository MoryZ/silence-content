package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ResourceType implements DescribedEnumValue<Byte>{
    CONTENT(1, "内容"),
    MATERIAL(2, "物料"),
    TAG(3, "标签"),
    COMMENT(4, "评论"),
    CLAIM_CASE(5, "理赔案例"),

    ;

    private final Byte value;
    private final String description;

    ResourceType(int value, String description) {
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
