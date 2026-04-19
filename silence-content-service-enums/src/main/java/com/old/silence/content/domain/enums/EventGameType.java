package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

public enum EventGameType implements DescribedEnumValue<Byte> {

    LOTTERY(1, "抽奖"),
    TASK(2, "任务流"),
    GIVEAWAY(3, "买赠"),
    AUTOMATIC_LOTTERY(4, "开奖"),
    GIVEAWAY_NEW(5, "买赠(新)"),
    SPECIAL_CONFIG_AM(6, "商品指定配置"),
    CLAIM(7, "权益领取"),
    SIMPLE_GIVEAWAY(8, "纯赠"),
    DIAMOND_SCRATCH(10, "钻石刮刮乐"),
    ACCOUNT_PRODUCT_CONFIG_INSURANCE(11, "账户产品配置-保险"),
    ACCOUNT_PRODUCT_CONFIG_MEDICAL(12, "账户产品配置-服务"),
    PK_TOURNAMENT(13, "PK赛事")
//    REAL_NAME_AUTOMATIC_LOTTERY(9, "抽奖-实名发奖"),
    ;

    private final Byte value;

    private final String description;

    EventGameType(int value, String description) {
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

    public static Boolean checkAccountProductConfigType(EventGameType eventGameType) {
        return ACCOUNT_PRODUCT_CONFIG_INSURANCE.equals(eventGameType)
                || ACCOUNT_PRODUCT_CONFIG_MEDICAL.equals(eventGameType);
    }
}
