package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
* LiveRoomOrganization查询对象
*/
public class LiveRoomOrganizationQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger liveRoomId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String orgCode;


    public BigInteger getLiveRoomId() {
        return this.liveRoomId;
    }

    public void setLiveRoomId(BigInteger liveRoomId) {
        this.liveRoomId = liveRoomId;
    }
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}