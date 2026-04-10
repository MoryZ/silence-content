package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 机器人实例类型
 * @author moryzang
 */
public enum TournamentRobotInstanceType implements DescribedEnumValue<Byte> {

    /**
     * TODO 机器人类型
     */
    TODO(1, "TODO机器人类型"),

    ;

    private final Byte value;

    private final String description;

    TournamentRobotInstanceType(int value, String description) {
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
