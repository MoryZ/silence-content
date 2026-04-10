package com.old.silence.content.api.vo;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;

public class TournamentUserStatusView {

    private boolean registered;

    private TournamentParticipantStatus registrationStatus;

    private Long totalChallengeCount;

    private Integer currentRankNo;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public TournamentParticipantStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(TournamentParticipantStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Long getTotalChallengeCount() {
        return totalChallengeCount;
    }

    public void setTotalChallengeCount(Long totalChallengeCount) {
        this.totalChallengeCount = totalChallengeCount;
    }

    public Integer getCurrentRankNo() {
        return currentRankNo;
    }

    public void setCurrentRankNo(Integer currentRankNo) {
        this.currentRankNo = currentRankNo;
    }
}
