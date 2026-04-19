package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author yangwenchang
 * @date 2023-02-01
 * @description
 */
public enum RewardItemType implements DescribedEnumValue<Byte> {

    MATERIAL(1, "实物"), LOTTERY_CHANCE(2, "抽奖机会"),
    GIVEAWAYS(3, "赠险"),
    GIVEAWAYS_MEDICAL(4, "医健服务"),
    GROWTH_POINT(5, "成长值(健康分)"),
    EQUITIES_COUPON(6, "权益卡券(权益中心)"),
    BADGE(7, "徽章"),
    WL_POINT(8, "万里通积分"),
    //VIRTUAL_COUPON(5, "卡券"),
    //PIECE(6, "碎片"),
    //SERVICE_LINK(7, "服务链接"),
    //SERVICE_LINK(8, "有价险"),
    THANKS_REWARD(9, "谢谢惠顾"),
    COUPON_MATERIAL(11,"实物券(权益中心)"),
    COUPON_INSURANCE(12,"赠险券(权益中心)"),
    COUPON(13,"优惠券(权益中心)"),
    POINT(14,"虚拟积分"),
    PRIVILEGE(15,"特权"),
    DEDUCTIBLE_EXCESS(16,"免赔额(权益中心)"),
    AGENT_HELPER_EQUITIES(17,"好助手权益"),

    GIVEAWAYS_NEW(10, "赠品(产品中心)"),

    TICKETS_MEDICAL(18,"医健票券")

    ;

    private final Byte value;

    private final String description;

    RewardItemType(int value, String description) {
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
