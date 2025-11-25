package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
/**
* LiveRoomOrganization命令对象
*/
public class LiveRoomOrganizationCommand {
    @NotNull
    private BigInteger liveRoomId;
    @NotBlank
    @Size(max = 100)
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