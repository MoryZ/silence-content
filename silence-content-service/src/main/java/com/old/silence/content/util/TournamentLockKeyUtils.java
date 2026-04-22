package com.old.silence.content.util;



import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigInteger;

public class TournamentLockKeyUtils {

    private static final String LOCK_TOURNAMENT_PARTICIPATION  =  "lock:tournamentParticipation:%s:%s:%s";

    private static final String LOCK_TOURNAMENT_PARTICIPATION_COUNT  =  "lock:tournamentParticipationCount:%s";

    private static final String LOCK_TOURNAMENT_CHALLENGE_COUNT  =  "lock:tournamentChallengeCount:%s:%s:%s:%s:%s:%s";


    public static String getTournamentParticipationLockKey(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType tournamentParticipantType){
        return String.format(LOCK_TOURNAMENT_PARTICIPATION, eventGameId, participantId, tournamentParticipantType.getValue().intValue());
    }

    public static String getTournamentParticipationCountLockKey(BigInteger eventGameId){
        return String.format(LOCK_TOURNAMENT_PARTICIPATION_COUNT, eventGameId);
    }
    public static String getTournamentChallengeCountLockKey(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType tournamentParticipantType, int cycleNumber, int segmentNumber, int stageNumber){
        return String.format(LOCK_TOURNAMENT_CHALLENGE_COUNT, eventGameId, participantId, tournamentParticipantType.getValue().intValue(), cycleNumber, segmentNumber, stageNumber);
    }
}
