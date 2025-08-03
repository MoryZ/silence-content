package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author moryzang
 * @Description
 */
public class FoodQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
