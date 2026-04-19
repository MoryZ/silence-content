package com.old.silence.content.api.tournament.tournament.dto;



import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigInteger;

/**
 * TournamentGroupRecord命令对象
 */
public class TournamentGroupRecordCommand {

    private BigInteger groupId;
    private BigInteger participantId;
    private TournamentParticipantType participantType;

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }
}
