package com.old.silence.content.infrastructure.elasticsearch.enums;

import com.old.silence.content.infrastructure.elasticsearch.model.ContentIndexAccessor;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;
import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum EsIndexType implements DescribedEnumValue<Byte> {
    CONTENT(1, "内容", ContentWideIndexDocument.class),
    ;

    private final Byte value;
    private final String description;
    private final Class<? extends ContentIndexAccessor> contentAccessorClass;

    EsIndexType(int value, String description, Class<? extends ContentIndexAccessor> contentAccessorClass) {
        this.value = (byte) value;
        this.description = description;
        this.contentAccessorClass = contentAccessorClass;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends ContentIndexAccessor> getContentAccessorClass() {
        return contentAccessorClass;
    }
}
