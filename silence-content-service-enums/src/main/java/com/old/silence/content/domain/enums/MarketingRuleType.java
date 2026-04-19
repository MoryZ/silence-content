package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

public enum MarketingRuleType implements DescribedEnumValue<Byte> {

    ACCESS_RULE(1, "准入规则"),
    DRAW_RULE(2, "抽奖规则"),
    WINNING_RULE(3, "中奖规则"),
    TASK_FREQUENCY_RULE(4, "任务频次规则"),
    FREQUENCY_RULE(11, "频次规则"),

    GIVEAWAY_ACCESS_RULE(5,"买赠销售方案准入规则"),

    GIVEAWAY_MATCH_RULE(6,"买赠销售方案匹配规则"),

    TASK_ACCESS_RULE(7,"任务流准入规则"),
    ASSISTANCE_RULE(8,"助力规则"),
    BUY_PRODUCT_ENTRY_RULE(9,"购买产品准入规则"),
    BUY_PRODUCT_REWARD_RULE(10,"购买产品奖品规则"),
    EVENT_GAME_ENTRY_RULE(12,"玩法准入规则"),
    CLAIM_RULE(13,"领取规则"),
    REWARD_POOL_HIT_RULE(14,"奖池必中规则"),
    EVENT_GAME_REWARD_ITEM_REWARD_RULE(15,"玩法奖品规则"),
    ;

    private final Byte value;

    private final String description;

    MarketingRuleType(int value, String description) {
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
