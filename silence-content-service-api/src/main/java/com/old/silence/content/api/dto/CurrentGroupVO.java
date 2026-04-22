package com.old.silence.content.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class CurrentGroupVO {
    /**
     * 小组id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger groupId;

    /**
     * 组名称
     */
    private String tierName;

    private Integer tierOrder;

    private String tierIcon;

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Integer getTierOrder() {
        return tierOrder;
    }

    public void setTierOrder(Integer tierOrder) {
        this.tierOrder = tierOrder;
    }

    public String getTierIcon() {
        return tierIcon;
    }

    public void setTierIcon(String tierIcon) {
        this.tierIcon = tierIcon;
    }
}
