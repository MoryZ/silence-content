package com.old.silence.content.api.tournament.tournament.dto;


import java.math.BigInteger;

public class TournamentParticipantGroupCacheDto {

    private BigInteger groupId;

    private String tierName;

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
