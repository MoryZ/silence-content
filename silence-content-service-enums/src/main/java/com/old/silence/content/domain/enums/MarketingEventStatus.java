package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

public enum MarketingEventStatus implements DescribedEnumValue<Byte> {

    DRAFT(1, "草稿"), PROCESSING(2, "审核中"), APPROVED(3, "审核通过"), REJECT(4, "审核拒绝"), PUBLISHED(5, "上线"), UNPUBLISHED(6, "下线");

    private final Byte value;

    private final String description;

    MarketingEventStatus(int value, String description) {
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
