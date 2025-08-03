package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author moryzang
 * @Description
 */
public class CategoryQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
