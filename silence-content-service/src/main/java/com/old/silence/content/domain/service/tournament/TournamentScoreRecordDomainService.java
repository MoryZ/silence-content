package com.old.silence.content.domain.service.tournament;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EX-ZHANGMENGWEI001
 */
@Service
public class TournamentScoreRecordDomainService {

    private final TournamentScoreRecordRepository tournamentScoreRecordRepository;
    private final TournamentParticipationRecordRepository tournamentParticipationRecordRepository;

    public TournamentScoreRecordDomainService(TournamentScoreRecordRepository tournamentScoreRecordRepository,
                                              TournamentParticipationRecordRepository tournamentParticipationRecordRepository) {
        this.tournamentScoreRecordRepository = tournamentScoreRecordRepository;
        this.tournamentParticipationRecordRepository = tournamentParticipationRecordRepository;
    }

    public int bulkCreate(List<TournamentScoreRecord> tournamentScoreRecords) {
        return tournamentScoreRecordRepository.bulkCreate(tournamentScoreRecords);
    }

    @Transactional
    public void calculateTournamentScoreByEventGameId(BigInteger eventGameId) {

        var tournamentParticipationRecords = tournamentParticipationRecordRepository.findByEventGameIdAndParticipantTypeAndStatus(eventGameId,
                        TournamentParticipantType.PARTY, TournamentParticipantStatus.REGISTERED);

        List<TournamentScoreRecord> allFinalTournamentScoreRecords = new ArrayList<>();
        // 1. 1个人1个人的算
        for (TournamentParticipationRecord tournamentParticipationRecord : tournamentParticipationRecords) {
            var aggregateTournamentScoreRecords = tournamentScoreRecordRepository.findByEventGameIdAndParticipantId(eventGameId, tournamentParticipationRecord.getParticipantId());

            var finalTournamentScoreRecord = buildAggregateTournamentScoreRecord(eventGameId, tournamentParticipationRecord.getParticipantId(), tournamentParticipationRecord.getParticipantType(), aggregateTournamentScoreRecords);
            allFinalTournamentScoreRecords.add(finalTournamentScoreRecord);
        }
        bulkCreate(allFinalTournamentScoreRecords);
    }


    private static TournamentScoreRecord buildAggregateTournamentScoreRecord(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType,
                                                                             List<TournamentScoreRecord> aggregateTournamentScoreRecords ) {

        BigDecimal finalScore = aggregateTournamentScoreRecords == null ? null : aggregateTournamentScoreRecords.stream()
                                                                                 .map(TournamentScoreRecord::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        var tournamentScoreRecord = new TournamentScoreRecord();
        tournamentScoreRecord.setEventGameId(eventGameId);
        tournamentScoreRecord.setParticipantId(participantId);
        tournamentScoreRecord.setParticipantType(participantType);

        tournamentScoreRecord.setScore(finalScore);
        tournamentScoreRecord.setScoreType(TournamentScoreType.TOURNAMENT);

        return tournamentScoreRecord;
    }

    public <T> List<T> queryGlobalScoreList(BigInteger eventGameId, BigInteger id, Pageable pageable, Class<T> projectionType) {
        TournamentScoreType scoreType = TournamentScoreType.TOURNAMENT;
        return tournamentScoreRecordRepository.findByEventGameIdAndScoreTypeAndIdGreaterThan(eventGameId,scoreType,id,pageable,projectionType);
    }
}
