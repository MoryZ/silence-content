package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛区模式
 *
 * @author EX-GUOWEI869
 */
public enum TournamentDivisionMode implements DescribedEnumValue<Byte> {

    WITHOUT_DIVISION (1, "无赛区"),
    WITH_DIVISION(2, "有赛区");

    private final Byte value;

    private final String description;

    TournamentDivisionMode(int value, String description) {
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
