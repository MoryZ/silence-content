package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author MurrayZhang
 * @Description
 */
public class OrderQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
