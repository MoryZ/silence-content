package com.old.silence.content.domain.model;


import jakarta.persistence.Entity;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.commons.domain.AbstractLogicalDeletionAuditable;

import java.math.BigInteger;

/**
 * 分组记录
 *
 * @author EX-GUOWEI869
 */
@Entity
public class TournamentGroupRecord extends AbstractLogicalDeletionAuditable<BigInteger> {

    /**
     * 分组id
     */
    private BigInteger groupId;

    /**
     * 参赛单位ID
     */
    private BigInteger participantId;

    /**
     * 参赛单位类型
     */
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
