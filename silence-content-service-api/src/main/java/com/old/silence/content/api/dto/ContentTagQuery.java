package com.old.silence.content.api.dto;


import java.math.BigInteger;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author moryzang
 */
public class ContentTagQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private ContentTagType type;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String code;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger parentId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Boolean enabled;


    public ContentTagType getType() {
        return type;
    }

    public void setType(ContentTagType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
