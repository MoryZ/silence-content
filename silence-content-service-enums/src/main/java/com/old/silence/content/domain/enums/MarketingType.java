package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author EX-LENGJINGJING001
 */
public enum MarketingType implements DescribedEnumValue<Byte> {
    DIAMOND_SCRATCH(1, "钻石刮刮乐"),
    NESTED_INSTITUTE(2, "院中院"),
    MGM(3, "MGM投保"),
    SURPRISE_BOX(4, "惊喜盒子"),
    TOURNAMENT(5,"Ai运动PK赛")
    ;

    private final Byte value;

    private final String description;

    MarketingType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
