package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
* @author 老默
*/
public enum BookType implements DescribedEnumValue<Byte> {
    SINGLE_BOOK(1, "单册书"),
    THE_MAIN_VOLUME_OF_MULTIPLE_BOOKS(2, "多册书的主册"),
    THE_CHILD_VOLUME_OF_MULTIPLE_BOOKS(3, "多册书的子册"),
    ;

    private final Byte value;
    private final String description;

    BookType(int value, String description) {
        this.value = (byte)value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}