package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 *  机器人状态
 * @author moryzang
 */
public enum TournamentRobotStatus implements DescribedEnumValue<Byte> {

    IDLE(1, "未使用"),
    ACTIVE(2, "使用中"),
    INACTIVE(3, "无效"),

    ;

    private final Byte value;

    private final String description;

    TournamentRobotStatus(int value, String description) {
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
