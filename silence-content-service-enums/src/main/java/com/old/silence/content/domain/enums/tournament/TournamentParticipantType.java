package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 参赛单位类型
 *
 * @author EX-GUOWEI869
 */
public enum TournamentParticipantType implements DescribedEnumValue<Byte> {

    PARTY(1, "会员"),
    ROBOT(2, "机器人"),
    TEAM(3, "团队");

    private final Byte value;

    private final String description;

    TournamentParticipantType(int value, String description) {
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
