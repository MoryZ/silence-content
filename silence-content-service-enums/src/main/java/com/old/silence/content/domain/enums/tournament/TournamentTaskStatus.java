package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事任务状态
 *
 * @author EX-ZHANGMENGWEI001
 */
public enum TournamentTaskStatus implements DescribedEnumValue<Byte> {

    PENDING(1, "待执行"),
    RUNNING(2, "执行中"),
    FAILED(3, "失败"),
    SUCCESS(4, "成功"),
    TERMINAL_FAILED(5, "终态失败"),
    ;

    private final Byte value;

    private final String description;

    TournamentTaskStatus(int value, String description) {
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
