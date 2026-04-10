package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 匹配模式
 *
 * @author moryzang
 */
public enum TournamentRobotMode implements DescribedEnumValue<Byte> {

    ENABLED(1, "启用")

    ;

    private final Byte value;

    private final String description;

    TournamentRobotMode(int value, String description) {
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
