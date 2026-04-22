package com.old.silence.content.domain.service.tournament;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.content.domain.model.TournamentRanking;
import com.old.silence.content.domain.model.TournamentRobotInstance;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentRankingRepository;
import com.old.silence.content.domain.repository.tournament.TournamentRobotInstanceRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author EX-ZHANGMENGWEI001
 */
@Service
public class TournamentRankingDomainService {

    @Value("${ehis.content.tournament.nickname:bobo}")
    private String defaultNickname;

    @Value("${ehis.content.tournament.avtarUrl:https://stg.iobs.pingan.com.cn/download/pah-ows-dmz-stg/fedb857d-4ac1-41c8-a0fa-987c88efc22c}")
    private String defaultAvtarUrl;

    private static final int TOP_K = 50;
    private final TournamentRankingRepository tournamentRankingRepository;
    private final TournamentRobotInstanceRepository tournamentRobotInstanceRepository;

    public TournamentRankingDomainService(TournamentRankingRepository tournamentRankingRepository,
                                          TournamentRobotInstanceRepository tournamentRobotInstanceRepository) {
        this.tournamentRankingRepository = tournamentRankingRepository;
        this.tournamentRobotInstanceRepository = tournamentRobotInstanceRepository;
    }

    public void buildGroupRanking(BigInteger groupId, List<TournamentScoreRecord> tournamentScoreRecords) {
        var tournamentRankings = buildTopNRankings(groupId, tournamentScoreRecords, TOP_K);
        tournamentRankingRepository.bulkCreate(tournamentRankings);
    }

    private List<TournamentRanking> buildTopNRankings(BigInteger groupId, List<TournamentScoreRecord> tournamentScoreRecords, int TOP_K) {
        List<TournamentRanking> tournamentRankings = new ArrayList<>();
        tournamentScoreRecords.sort(Comparator.comparing(TournamentScoreRecord::getScore));

        if (CollectionUtils.size(tournamentScoreRecords) > TOP_K) {
            // 截取TOP_N
            tournamentScoreRecords = tournamentScoreRecords.subList(0, TOP_K);
        }

        var robotParticipantIds = tournamentScoreRecords.stream().filter(tournamentScoreRecord -> TournamentParticipantType.ROBOT.equals(tournamentScoreRecord.getParticipantType()))
                .map(TournamentScoreRecord::getParticipantId).collect(Collectors.toList());

        var tournamentRobotInstances = tournamentRobotInstanceRepository.findByIds(robotParticipantIds);
        var bigIntegerTournamentRobotInstanceMap = CollectionUtils.transformToMap(tournamentRobotInstances, TournamentRobotInstance::getId);
        for (int i = 0; i < tournamentScoreRecords.size(); i++) {
            var tournamentScoreRecord = tournamentScoreRecords.get(i);
            var tournamentRanking = convertToTournamentRanking(groupId, tournamentScoreRecord, i + 1);

            // 如果是机器人，还要取昵称和头像
            if (TournamentParticipantType.ROBOT.equals(tournamentScoreRecord.getParticipantType())) {
                tournamentRanking.setNickname(
                        Optional.of(bigIntegerTournamentRobotInstanceMap.get(tournamentScoreRecord.getParticipantId())).map(TournamentRobotInstance::getNickname).orElse(defaultNickname));
                tournamentRanking.setAvatarUrl(
                        Optional.of(bigIntegerTournamentRobotInstanceMap.get(tournamentScoreRecord.getParticipantId())).map(TournamentRobotInstance::getNickname).orElse(defaultAvtarUrl));
            }
            tournamentRankings.add(tournamentRanking);
        }
        return tournamentRankings;
    }

    private TournamentRanking convertToTournamentRanking(BigInteger groupId, TournamentScoreRecord tournamentScoreRecord, int rankNo) {
        var tournamentRanking = new TournamentRanking();
        tournamentRanking.setEventGameId(tournamentScoreRecord.getEventGameId());
        tournamentRanking.setParticipantType(tournamentScoreRecord.getParticipantType());
        tournamentRanking.setParticipantId(tournamentScoreRecord.getParticipantId());
        tournamentRanking.setScore(tournamentScoreRecord.getScore());
        tournamentRanking.setScore(tournamentScoreRecord.getScore());
        tournamentRanking.setGroupId(groupId);

        tournamentRanking.setRankingType(TournamentRankingType.GROUP);
        tournamentRanking.setRankNo(rankNo);
        return tournamentRanking;
    }


}
