package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事任务类型
 *
 * @author moryzang
 */
public enum TaskTypeEnum implements DescribedEnumValue<String> {

    STAGE_SETTLE("STAGE_SETTLE", "场次结算"),
    SEGMENT_SETTLE("SEGMENT_SETTLE", "片结算"),
    CYCLE_SETTLE("CYCLE_SETTLE", "周期结算"),
    ;

    private final String value;

    private final String description;

    TaskTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
