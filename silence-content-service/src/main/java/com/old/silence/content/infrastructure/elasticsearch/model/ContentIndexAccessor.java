package com.old.silence.content.infrastructure.elasticsearch.model;

import org.springframework.data.annotation.Id;

/**
 * @author moryzang
 */
public class ContentIndexAccessor {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
