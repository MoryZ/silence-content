package com.old.silence.content.domain.service.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordQuery;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.TierDefinition;
import com.old.silence.content.api.tournament.tournament.dto.support.TierMapping;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.model.TournamentGroup;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Service
public class TournamentGroupRecordDomainService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentGroupRecordDomainService.class);

    private final TournamentScoreRecordRepository scoreRecordRepository;

    private final TournamentGroupRepository groupRepository;

    private final TournamentGroupRecordRepository groupRecordRepository;

    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;

    private final TournamentParticipationRecordRepository participationRecordRepository;

    public TournamentGroupRecordDomainService(TournamentScoreRecordRepository scoreRecordRepository,
            TournamentGroupRepository groupRepository, TournamentGroupRecordRepository groupRecordRepository,
            TournamentConfigCacheDomainService tournamentConfigCacheDomainService,
            TournamentParticipationRecordRepository tournamentParticipationRecordRepository) {
        this.scoreRecordRepository = scoreRecordRepository;
        this.groupRepository = groupRepository;
        this.groupRecordRepository = groupRecordRepository;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.participationRecordRepository = tournamentParticipationRecordRepository;
    }

    /**
     * 初始化分组：将已报名的参赛者（包括真人和机器人）随机分组
     *
     * @param eventGameId 赛事玩法ID
     */
    @Transactional
    public void initializeGroups(BigInteger eventGameId) {
        logger.info("【初始化分组开始】eventGameId={}", eventGameId);

        // 1. 获取赛事配置
        TournamentConfigCacheDto config = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);
        if (config == null) {
            throw new IllegalArgumentException("未找到赛事配置，eventGameId=" + eventGameId);
        }

        Integer groupSize = config.getGroupSize();
        if (groupSize == null || groupSize <= 0) {
            throw new IllegalArgumentException("配置中 groupSize 无效，eventGameId=" + eventGameId);
        }

        // 2. 查询所有已报名的参赛者（状态为已报名，包括真人和机器人）
        List<TournamentParticipationRecord> participants = participationRecordRepository.findByEventGameIdAndStatus(
                eventGameId, TournamentParticipantStatus.REGISTERED);

        if (participants.isEmpty()) {
            logger.warn("【无参赛者】eventGameId={}，无需分组", eventGameId);
            return;
        }

        logger.info("【参赛者查询完成】共 {} 人（真人+机器人），准备随机分组，每组 {} 人", participants.size(), groupSize);

        // 3. 随机打乱（包含真人和机器人）
        List<TournamentParticipationRecord> shuffled = new ArrayList<>(participants);
        Collections.shuffle(shuffled);

        // 4. 创建分组（组数 = total / groupSize） 能够整除
        int totalParticipants = shuffled.size();
        if (totalParticipants % groupSize != 0) {
            throw new IllegalStateException(
                    "参赛人数必须能被 groupSize 整除，但当前：" + totalParticipants + " % " + groupSize + " != 0");
        }
        int groupCount = totalParticipants / groupSize;
        LocalDate groupDate = LocalDate.now();
        List<TournamentGroup> newGroups = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {
            TournamentGroup group = new TournamentGroup();
            group.setEventGameId(eventGameId);
            group.setStageType(TournamentStageType.CYCLE); // 初始分组
            group.setStageNumber(1); // 第一阶段
            group.setGroupDate(groupDate);
            group.setGroupNumber(i + 1); // 组号从1开始
            // tierName、tierOrder、tierIcon 可为空，或根据需要设置默认值
            newGroups.add(group);
            logger.info("【创建初始组】组号={}", i + 1);
        }

        // 5. 保存分组（确保 ID 被回填）
        groupRepository.saveAll(newGroups);
        logger.info("【分组创建完成】共创建 {} 个组", newGroups.size());

        // 6. 生成组成员记录
        List<TournamentGroupRecord> groupRecords = new ArrayList<>();
        Map<Integer, Integer> groupStats = new LinkedHashMap<>(); // 统计每组人数

        for (int i = 0; i < shuffled.size(); i++) {
            TournamentParticipationRecord participant = shuffled.get(i);
            int groupIndex = i / groupSize; // 分配到第几组
            TournamentGroup savedGroup = newGroups.get(groupIndex);

            TournamentGroupRecord record = new TournamentGroupRecord();
            record.setGroupId(savedGroup.getId());
            record.setParticipantId(participant.getParticipantId());
            record.setParticipantType(participant.getParticipantType());
            groupRecords.add(record);

            // 统计
            groupStats.merge(groupIndex + 1, 1, Integer::sum);
        }

        // 7. 保存组成员记录
        if (!groupRecords.isEmpty()) {
            groupRecordRepository.saveAll(groupRecords);
            logger.info("【初始化分组完成】共分配 {} 名成员（含真人+机器人），分组情况：{}", groupRecords.size(),
                    groupStats.entrySet().stream().map(e -> "第" + e.getKey() + "组(" + e.getValue() + "人)")
                            .collect(Collectors.joining(", ")));
        }

        logger.info("【初始化分组结束】eventGameId={}", eventGameId);
    }

    /**
     * 执行重分组逻辑
     * - 根据 eventGameId 自动获取配置、当前周期等信息
     * - 对上一个周期的得分记录进行重分组
     *
     * @param eventGameId 赛事玩法ID
     */
    @Transactional
    public void performRegrouping(BigInteger eventGameId, Integer cycleNumber) {
        logger.info("【重分组开始】eventGameId={}, cycleNumber={}", eventGameId, cycleNumber);

        // 1. 获取赛事配置和CycleStageConfig
        TournamentConfigCacheDto config = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);

        if (config == null) {
            throw new IllegalArgumentException("未找到赛事配置，eventGameId=" + eventGameId);
        }

        CycleStageConfig cycleStageConfig = config.converterAttributes(CycleStageConfig.class);
        if (cycleStageConfig == null) {
            throw new IllegalArgumentException("未配置 CycleStageConfig，无法进行重分组");
        }

        List<TierDefinition> tierDefinitions = cycleStageConfig.getCycleTierDefinitions();
        if (tierDefinitions == null || tierDefinitions.isEmpty()) {
            throw new IllegalArgumentException("CycleStageConfig 中未配置 cycleTierDefinitions");
        }
        logger.info("【段位配置加载】共加载 {} 个段位：{}", tierDefinitions.size(),
                tierDefinitions.stream().map(TierDefinition::getTierName).collect(Collectors.joining(", ")));
        // 5. 查询该周期的最终得分记录
        List<TournamentScoreRecord> scoreRecords = scoreRecordRepository.findByEventGameIdAndCycleNumber(eventGameId,
                cycleNumber);

        if (scoreRecords.isEmpty()) {
            logger.warn("【无得分记录】eventGameId={}, cycleNumber={}，跳过重分组", eventGameId, cycleNumber);
            return; // 无得分记录，跳过
        }

        logger.info("【得分记录查询完成】共 {} 条记录，准备按 groupId 分组", scoreRecords.size());
        // 6. 按 groupId 分组并排序
        Map<BigInteger, List<TournamentScoreRecord>> groupedByGroup = scoreRecords.stream().collect(
                Collectors.groupingBy(TournamentScoreRecord::getGroupId, LinkedHashMap::new,// 保证插入顺序
                        Collectors.toList()));

        // 7. 创建分组记录（每个 tier 一个新组）
        List<TournamentGroup> newGroups = new ArrayList<>();
        LocalDate groupDate = LocalDate.now();

        for (int i = 0; i < tierDefinitions.size(); i++) {
            TierDefinition tierDef = tierDefinitions.get(i);
            TournamentGroup newGroup = new TournamentGroup();
            newGroup.setEventGameId(eventGameId);
            newGroup.setStageType(TournamentStageType.CYCLE);
            newGroup.setStageNumber(cycleNumber + 1); // 新组属于新周期（下一阶段）
            newGroup.setGroupDate(groupDate);
            // TODO groupNumber待定
            newGroup.setGroupNumber(i + 1); // 简单编号，也可按规则生成
            newGroup.setTierName(tierDef.getTierName());
            newGroup.setTierOrder(tierDef.getsOrder());
            newGroup.setTierImageUrl(tierDef.getTierImageUrl());
            newGroups.add(newGroup);
            logger.info("【创建新段位组】tierName={}, rankRange=[{}-{}], tierOrder={}", tierDef.getTierName(),
                    tierDef.getTierMapping().getRankStart(), tierDef.getTierMapping().getRankEnd(),
                    tierDef.getsOrder());

        }

        // 8. 保存新组
        groupRepository.saveAll(newGroups);
        List<TournamentGroup> savedNewGroups = newGroups;
        Map<String, TournamentGroup> tierNameToGroup = savedNewGroups.stream().collect(
                Collectors.toMap(TournamentGroup::getTierName, g -> g));

        // 9. 生成分组成员记录
        List<TournamentGroupRecord> newGroupRecords = new ArrayList<>();
        Map<String, Integer> assignmentStats = new LinkedHashMap<>(); // 统计每个段位分配人数

        for (Map.Entry<BigInteger, List<TournamentScoreRecord>> entry : groupedByGroup.entrySet()) {
            BigInteger oldGroupId = entry.getKey();
            List<TournamentScoreRecord> groupRecords = entry.getValue();
            // 组内按得分降序排序
            groupRecords.sort((a, b) -> {
                int cmp = b.getScore().compareTo(a.getScore());
                if (cmp != 0) {
                    return cmp;
                }
                return a.getParticipantId().compareTo(b.getParticipantId());
            });

            logger.info("【处理旧组】groupId={}, 成员数={}, 最高分={}, 最低分={}", oldGroupId, groupRecords.size(),
                    groupRecords.get(0).getScore(), groupRecords.get(groupRecords.size() - 1).getScore());

            // 为每个选手分配新组
            for (int i = 0; i < groupRecords.size(); i++) {
                TournamentScoreRecord record = groupRecords.get(i);
                int rank = i + 1;

                // 查找匹配的 tier
                TierDefinition matchedTier = tierDefinitions.stream().filter(t -> {
                    TierMapping m = t.getTierMapping();
                    return m != null && rank >= m.getRankStart() && rank <= m.getRankEnd();
                }).findFirst().orElse(null);

                if (matchedTier == null) {
                    logger.warn("【无匹配段位】participantId={}, groupId={}, rank={} 超出所有段位范围，跳过",
                            record.getParticipantId(), oldGroupId, rank);
                    continue;
                }

                TournamentGroup targetGroup = tierNameToGroup.get(matchedTier.getTierName());
                if (targetGroup == null) {
                    logger.error("【目标组不存在】tierName={} 未找到对应新组，participantId={}", matchedTier.getTierName(),
                            record.getParticipantId());
                    continue;
                }

                TournamentGroupRecord gr = new TournamentGroupRecord();
                gr.setGroupId(targetGroup.getId());
                gr.setParticipantId(record.getParticipantId());
                gr.setParticipantType(record.getParticipantType());
                newGroupRecords.add(gr);

                // 统计
                assignmentStats.merge(matchedTier.getTierName(), 1, Integer::sum);
                logger.debug("【成员分配】participantId={} (score={}) 在组 {} 中排名第 {}，分配至段位：{}",
                        record.getParticipantId(), record.getScore(), oldGroupId, rank, matchedTier.getTierName());
            }
        }

        // 10. 保存组记录
        if (!newGroupRecords.isEmpty()) {
            groupRecordRepository.saveAll(newGroupRecords);
            logger.info("【重分组完成】共分配 {} 名成员，详细分布：{}", newGroupRecords.size(),
                    assignmentStats.entrySet().stream().map(e -> e.getKey() + "(" + e.getValue() + "人)")
                            .collect(Collectors.joining(", ")));
        }
        logger.info("【重分组结束】eventGameId={}, cycleNumber={}，新阶段：{}", eventGameId, cycleNumber, cycleNumber + 1);

    }


    public <T> Page<T> query(TournamentGroupRecordQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentGroupRecord.class);
        return groupRecordRepository.findByCriteria(criteria, pageable, projectionType);
    }

    public BigInteger create(TournamentGroupRecord tournamentGroupRecord) {
        groupRecordRepository.create(tournamentGroupRecord);
        return tournamentGroupRecord.getId();
    }
}
