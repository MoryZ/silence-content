package com.old.silence.content.console.dto;


import java.math.BigInteger;

/**
* LiveRoomOrganization查询对象
*/
public class LiveRoomOrganizationConsoleQuery {
    private BigInteger liveRoomId;
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