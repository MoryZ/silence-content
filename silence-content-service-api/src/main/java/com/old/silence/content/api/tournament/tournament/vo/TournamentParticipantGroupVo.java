package com.old.silence.content.api.tournament.tournament.vo;


import java.math.BigInteger;

public class TournamentParticipantGroupVo {
    //分组id
    private BigInteger groupId;
    //段位名
    private String tierName;
    //段位图标
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

    public String getTierIcon() {
        return tierIcon;
    }

    public void setTierIcon(String tierIcon) {
        this.tierIcon = tierIcon;
    }
}
