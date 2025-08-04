package com.old.silence.content.infrastructure.elasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author moryzang
 */
public class ContentIndexAccessor {

    @Field(type = FieldType.Long)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
