package com.old.silence.bp.service;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.old.silence.content.api.TournamentParticipantClient;
import com.old.silence.content.api.TournamentRankClient;
import com.old.silence.content.api.TournamentScoreRecordClient;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankingDto;
import com.old.silence.content.api.tournament.tournament.vo.TournamentParticipationRecordSimpleView;
import com.old.silence.content.api.tournament.tournament.vo.TournamentScoreRecordSimpleView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class TournamentRankService {

    private static final Logger log = LoggerFactory.getLogger(TournamentRankService.class);


    private final TournamentScoreRecordClient tournamentScoreRecordClient;
    private final TournamentParticipantClient tournamentParticipantClient;
    private final TournamentRankClient tournamentRankClient;

    private final Integer PAGE_SIZE = 1000;
    private final Integer BATCH_ADD_SIZE = 1000;
    private final Integer TOP_K = 10;
    private final Integer SCALE = 2;
    public TournamentRankService(TournamentScoreRecordClient tournamentScoreRecordClient,
                                 TournamentParticipantClient tournamentParticipantClient,
                                 TournamentRankClient tournamentRankClient){
        this.tournamentScoreRecordClient = tournamentScoreRecordClient;
        this.tournamentParticipantClient = tournamentParticipantClient;
        this.tournamentRankClient = tournamentRankClient;
    }




    /**
     * 全局排名计算并批量保存（每 1000 条保存一次）
     */
    public void globalRank(BigInteger eventGameId) {
        log.info("开始计算赛事 [{}] 全局排名", eventGameId);

        // 1. 获取所有得分记录
        List<TournamentScoreRecordSimpleView> allScoreRecords = fetchAllScoreRecords(eventGameId);
        if (CollectionUtils.isEmpty(allScoreRecords)) {
            log.info("无得分记录，eventGameId={}", eventGameId);
            return;
        }

        // 2. 构建排名对象 + 提取 TopK 分数
        List<TournamentRankingDto> tournamentRankings = new ArrayList<>();
        BigDecimal robotMinScore = BigDecimal.valueOf(Long.MAX_VALUE);
        BigDecimal robotMaxScore = BigDecimal.ZERO;
        PriorityQueue<BigDecimal> minHeap = new PriorityQueue<>(TOP_K, Comparator.naturalOrder());

        for (TournamentScoreRecordSimpleView record : allScoreRecords) {
            BigDecimal score = record.getScore();
            if (score == null) continue;

            calculateTopK(score, TOP_K, minHeap);

            if (score.compareTo(robotMinScore) < 0) {
                robotMinScore = score;
            }

            tournamentRankings.add(buildRankingDto(record.getParticipantId(), record.getParticipantType(), score));
        }

        // 从堆中取出 TopK，倒序
        List<BigDecimal> topKList = minHeap.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        robotMaxScore = topKList.isEmpty() ? BigDecimal.ZERO : topKList.get(topKList.size() - 1);

        // 3. 获取机器人参赛记录并生成机器人排名
        List<TournamentParticipationRecordSimpleView> allRobotRecords = fetchAllRobotRecords(eventGameId);
        if (CollectionUtils.isNotEmpty(allRobotRecords)) {

            for (TournamentParticipationRecordSimpleView record : allRobotRecords) {
                BigDecimal robotScore = calculateRobotScore(robotMinScore, robotMaxScore);
                tournamentRankings.add(buildRankingDto(record.getParticipantId(), TournamentParticipantType.ROBOT, robotScore));
            }
        }

        // 4. 排序：按分数降序
        tournamentRankings.sort(Comparator.comparing(TournamentRankingDto::getScore).reversed());

        // 5. 分批保存：每 BATCH_ADD_SIZE 条保存一次
        saveRankingsInBatches(tournamentRankings);

        log.info("赛事 [{}] 全局排名计算完成，共处理 {} 条数据", eventGameId, tournamentRankings.size());
    }

    /**
     * 分批保存排名数据（每 BATCH_ADD_SIZE 条保存一次）
     */
    private void saveRankingsInBatches(List<TournamentRankingDto> rankings) {
        List<List<TournamentRankingDto>> batches = splitList(rankings, BATCH_ADD_SIZE);
        int batchIndex = 0;

        for (List<TournamentRankingDto> batch : batches) {
            try {
                tournamentRankClient.batchAddRankRecord(batch);
                log.info("批次 {} 保存成功，共 {} 条", ++batchIndex, batch.size());
            } catch (Exception e) {
                log.error("批次 {} 保存失败，数据：{}", batchIndex + 1, batch, e);
                // 可选：加入重试机制
            }
        }
    }

    /**
     * 从数据库分页获取所有得分记录
     */
    private List<TournamentScoreRecordSimpleView> fetchAllScoreRecords(BigInteger eventId) {
        List<TournamentScoreRecordSimpleView> allRecords = new ArrayList<>();
        BigInteger lastId = BigInteger.ZERO;
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id"));

        while (true) {
            var page = tournamentScoreRecordClient.queryGlobalScoreList(eventId, lastId, pageRequest, TournamentScoreRecordSimpleView.class);
            if (CollectionUtils.isEmpty(page)) break;

            allRecords.addAll(page);
            var lastRecord = page.get(page.size() - 1);
            lastId = lastRecord.getId();

            if (page.size() < PAGE_SIZE) break;
        }

        return allRecords;
    }

    /**
     * 从数据库分页获取所有机器人参赛记录
     */
    private List<TournamentParticipationRecordSimpleView> fetchAllRobotRecords(BigInteger eventId) {
        List<TournamentParticipationRecordSimpleView> allRecords = new ArrayList<>();
        BigInteger lastId = BigInteger.ZERO;
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id"));

        while (true) {
            var page = tournamentParticipantClient.queryRobotRecord(eventId, lastId, pageRequest, TournamentParticipationRecordSimpleView.class);
            if (CollectionUtils.isEmpty(page)) break;

            allRecords.addAll(page);
            var lastRecord = page.get(page.size() - 1);
            lastId = lastRecord.getId();

            if (page.size() < PAGE_SIZE) break;
        }

        return allRecords;
    }

    /**
     * 用于 TopK 的小顶堆更新
     */
    private void calculateTopK(BigDecimal score, int k, PriorityQueue<BigDecimal> minHeap) {
        if (score == null) return;

        if (minHeap.size() < k) {
            minHeap.offer(score);
        } else if (score.compareTo(minHeap.peek()) > 0) {
            minHeap.poll();
            minHeap.offer(score);
        }
    }

    /**
     * 生成随机分数：[minRate, maxRate] 范围内
     */
    private BigDecimal randomBetween(BigDecimal minRate, BigDecimal maxRate) {
        if (minRate == null || maxRate == null || minRate.compareTo(maxRate) > 0) {
            throw new IllegalArgumentException("minRate 不能大于 maxRate，或为 null");
        }

        BigDecimal range = maxRate.subtract(minRate);
        double randomDouble = RandomUtils.nextDouble(0,1);
        BigDecimal randomValue = minRate.add(range.multiply(BigDecimal.valueOf(randomDouble)));
        return randomValue.setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 构建排名 DTO
     */
    private TournamentRankingDto buildRankingDto(BigInteger participantId, TournamentParticipantType participantType, BigDecimal score) {
        TournamentRankingDto dto = new TournamentRankingDto();
        dto.setParticipantId(participantId);
        dto.setParticipantType(participantType);
        dto.setScore(score);
        return dto;
    }

    /**
     * 手动分块（无 Spring 时可用）
     */
    private <T> List<List<T>> splitList(List<T> list, int batchSize) {
        if (list == null || list.isEmpty() || batchSize <= 0) {
            return new ArrayList<>();
        }

        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            int end = Math.min(i + batchSize, list.size());
            batches.add(list.subList(i, end));
        }
        return batches;
    }

    /**
     * 机器人分数计算：基于 min/max 分数和系数范围
     */
    private BigDecimal calculateRobotScore(BigDecimal minLimit, BigDecimal maxLimit) {
        if (minLimit == null || maxLimit == null || minLimit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = randomBetween(minLimit, maxLimit);
        return result.compareTo(maxLimit) < 0 ? result : minLimit;
    }

}
