package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue; 

/**
 * 匹配模式
 *
 * @author moryzang
 */
public enum TournamentMatchMode implements DescribedEnumValue<Byte> {

    NON_REALTIME(1, "非实时")

    ;

    private final Byte value;

    private final String description;

    TournamentMatchMode(int value, String description) {
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
