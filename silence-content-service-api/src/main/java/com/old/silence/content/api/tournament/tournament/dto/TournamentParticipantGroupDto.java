package com.old.silence.content.api.tournament.tournament.dto;


import java.math.BigInteger;
import java.time.LocalDate;

public class TournamentParticipantGroupDto {

    private BigInteger id;

    private String tierName;

    private String tierImageUrl;

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

    public String getTierImageUrl() {
        return tierImageUrl;
    }

    public void setTierImageUrl(String tierImageUrl) {
        this.tierImageUrl = tierImageUrl;
    }

    public LocalDate getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.groupDate = groupDate;
    }
}
