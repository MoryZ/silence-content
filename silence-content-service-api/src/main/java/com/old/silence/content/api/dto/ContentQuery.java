package com.old.silence.content.api.dto;

import com.old.silence.data.commons.annotation.RelationalQueryProperty;
import org.springframework.data.repository.query.parser.Part;

/**
 * @author moryzang
 */
public class ContentQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
