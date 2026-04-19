package com.old.silence.content.api.tournament.tournament.dto;




import java.math.BigInteger;
import java.time.LocalDate;

public class TournamentParticipantGroupDto {

    private BigInteger id;

    private String tierName;

    private String tierIcon;

    private LocalDate groupDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public LocalDate getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.groupDate = groupDate;
    }
}
