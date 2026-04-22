package com.old.silence.content.domain.enums;


import org.apache.commons.lang3.StringUtils;
import com.old.silence.core.enums.EnumValue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author yangwenchang
 */
public enum TimeCalculationMethod implements EnumValue<Byte> {

    FIXED(1) {

        @Override
        public Instant calculate(String value) {
            return Instant.parse(value);
        }
    },
    BASED_ON_CURRENT_TIME(2) {

        @Override
        public Instant calculate(String value) {
            return Instant.now().plus(Long.parseLong(value), ChronoUnit.DAYS);
        }
    };

    private final Byte value;

    TimeCalculationMethod(int value) {
        this.value = (byte) value;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    public abstract Instant calculate(String value);

    public static Instant calculate(TimeCalculationMethod method, String value) {
        if (method == null || StringUtils.isBlank(value)) {
            return null;
        } else {
            return method.calculate(value);
        }
    }
}
