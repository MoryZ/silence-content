package com.old.silence.content.api.tournament.tournament.dto;

/**
 * @author moryzang
 */
public class TournamentGroupInfoCacheDTO {
    private String tierName;

    private Integer tierOrder;

    private String tierIcon;

    public TournamentGroupInfoCacheDTO() {
    }

    public TournamentGroupInfoCacheDTO(String tierName, Integer tierOrder, String tierIcon) {
        this.tierName = tierName;
        this.tierOrder = tierOrder;
        this.tierIcon = tierIcon;
    }

    // getter and setter
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
