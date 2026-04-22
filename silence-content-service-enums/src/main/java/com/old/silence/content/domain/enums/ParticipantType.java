package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author YANGWENCHANG983
 */
public enum ParticipantType implements DescribedEnumValue<Byte> {

    MEMBER(1, "会员"),
    PARTY(2, "客户"),

    TEAM(9, "团队"),
    ;

    private final Byte value;

    private final String description;

    ParticipantType(int value, String description) {
        this.value = (byte)value;
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

    public static ParticipantType getByValue(Byte value) {
        for (ParticipantType type : ParticipantType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
