package com.old.silence.content.api.dto;

import java.math.BigInteger;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * UserInteractionLog查询对象
 */
public class UserInteractionLogQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger resourceId;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(BigInteger resourceId) {
        this.resourceId = resourceId;
    }

}