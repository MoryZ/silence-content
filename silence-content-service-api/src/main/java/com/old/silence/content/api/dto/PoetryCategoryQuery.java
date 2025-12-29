package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * PoetryCategory查询对象
 */
public class PoetryCategoryQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String name;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String code;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger parentId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Boolean enabled;


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