package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 当前阶段
 *
 * @author moryzang
 */
public enum TournamentCurrentStage implements DescribedEnumValue<Byte> {

    /**
     * TODO 补充阶段
     */
    TODO(1, "当前阶段");

    private final Byte value;

    private final String description;

    TournamentCurrentStage(int value, String description) {
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
