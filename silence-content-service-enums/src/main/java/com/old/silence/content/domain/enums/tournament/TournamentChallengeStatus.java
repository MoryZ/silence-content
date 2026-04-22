package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 挑战状态
 *
 * @author EX-GUOWEI869
 */
public enum TournamentChallengeStatus implements DescribedEnumValue<Byte> {


    COMPLETED(1, "挑战状态");

    private final Byte value;

    private final String description;

    TournamentChallengeStatus(int value, String description) {
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
