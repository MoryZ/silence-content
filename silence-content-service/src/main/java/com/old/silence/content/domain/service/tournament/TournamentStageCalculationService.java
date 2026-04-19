package com.old.silence.content.domain.service.tournament;

import org.apache.commons.lang3.tuple.Triple;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.StageConfig;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.enums.MarketingEventStatus;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author moryzang
 */
public final class TournamentStageCalculationService {

    private TournamentStageCalculationService() {
    }

    /**
     * 计算当前周期号
     * 规则：
     * - 比赛未开始 → 0
     * - 比赛已结束 → totalCycles + 1
     *
     * @param config 赛事配置
     * @param currentTime 当前时间
     * @return 当前周期号（从1开始），0表示未开始，-1表示已结束
     */
    // TODO 待优化
    public static int calculateCurrentCycle(TournamentConfigCacheDto config, Instant currentTime) {
        if (config == null || currentTime == null) {
            return 0;
        }

        return calculateCurrentCycle(
                currentTime,
                config.getTournamentStartTime(),
                config.getTournamentEndTime(),
                1
        );

    }

    /**
     * 判断当前时间是否在比赛有效参与时间内（即：比赛中且在允许的场次时间段内）
     *
     * @param config 赛事配置
     * @param currentTime 当前时间
     * @return true 表示可以参与，false 表示不在有效时间内
     */
    public static boolean isTournamentActive(TournamentConfigCacheDto config, Instant currentTime) {
        if (config == null || currentTime == null) {
            return false;
        }

        // 1. 必须在比赛总时间范围内
        if (currentTime.isBefore(config.getTournamentStartTime()) || currentTime.isAfter(
                config.getTournamentEndTime())) {
            return false; // 不在比赛总时间内，直接返回 false
        }

        // 2. 获取 CycleStageConfig
        CycleStageConfig cycleStageConfig = config.converterAttributes(CycleStageConfig.class);
        if(cycleStageConfig == null){
            return false;
        }

        List<StageConfig> stageConfigs = cycleStageConfig.getStageConfigs();
        if (stageConfigs == null || stageConfigs.isEmpty()) {
            return false;
        }

        // 3. 获取当前日期（按系统时区）
        LocalDate today = currentTime.atZone(ZoneId.systemDefault()).toLocalDate();

        // 4. 遍历每个场次，判断当前时间是否落在其当日时间段内
        for (StageConfig stageConfig : stageConfigs) {
            LocalTime start = stageConfig.getStartTime();
            LocalTime end = stageConfig.getEndTime();

            if (start == null || end == null) {
                continue;
            }

            // 构造当天的开始和结束时间
            LocalDateTime stageStartDateTime = LocalDateTime.of(today, start);
            LocalDateTime stageEndDateTime = LocalDateTime.of(today, end);

            // 处理跨天情况：例如 22:00 - 02:00
            if (end.isBefore(start)) {
                stageEndDateTime = stageEndDateTime.plusDays(1);
            }

            // 转为 Instant（使用系统时区）
            Instant stageStartInstant = stageStartDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Instant stageEndInstant = stageEndDateTime.atZone(ZoneId.systemDefault()).toInstant();

            // 判断当前时间是否在该场次时间内
            if (!currentTime.isBefore(stageStartInstant) && !currentTime.isAfter(stageEndInstant)) {
                return true; // 同时满足：在比赛总时间内 + 在某一场次时间内
            }
        }
        return false;
    }

    public static void checkMarketingEvent(Instant now, Instant startTime, Instant endTime,
            MarketingEventStatus status) {
        if (MarketingEventStatus.PUBLISHED != status) {
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_MARKETING_EVENT_NOT_PUBLISHED);
        }

        if (now.isBefore(startTime)) {
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_MARKETING_EVENT_NOT_READY);
        }

        if (now.isAfter(endTime)) {
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_MARKETING_EVENT_END);
        }
    }

    public static void checkTournamentRegisterTime(Instant registrationTime, Instant registrationStartTime,
            Instant registrationEndTime) {
        if (registrationTime.isBefore(registrationStartTime)) {
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_NOT_READY);
        }

        if (registrationTime.isAfter(registrationEndTime)) {
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_END);
        }
    }

    /**
     * 解析总场次数 本次先写死
     * @param tournamentStartTime 开始时间
     * @param tournamentEndTime 结束时间
     * @return 返回总场次数
     */
    public static long resolveTotalStages(Instant tournamentStartTime, Instant tournamentEndTime) {
        var daysBetween = ChronoUnit.DAYS.between(tournamentStartTime, tournamentEndTime);
        return daysBetween + 1;
    }

    /**
     * 构造任务触发时间
     * @param startTime  开始时间
     * @param stageEndTime 场次结束时间时分秒
     * @param buffer 缓存时间
     * @return job执行时间
     */
    public static Instant buildTriggerTime(Instant startTime, LocalTime stageEndTime, Duration buffer) {
        var localDate = startTime.atZone(ZoneId.systemDefault()).toLocalDate();

        return localDate.atTime(stageEndTime)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .plus(buffer);
    }

    /**
     * 判断传入时间所属周期，片，场次， 先写死
     * @param checkTime 待校验时间
     * @return 周期，片，场次
     */
    public static Triple<Integer, Integer, Integer> calculateCycleSegmentStage(Instant checkTime, Instant tournamentStartTime, Instant tournamentEndTime
                                                                               ) {
        var cycleNo = (int) ChronoUnit.DAYS.between(tournamentStartTime, checkTime) + 1;
        var segmentNo = 1;
        var stageNo = 1;
        return Triple.of(cycleNo, segmentNo, stageNo);
    }

    /**
     * 判断当前时间是否在场次结束后
     * @param stageEndTime 场次结束时间
     */
    public static boolean isUntilStageEndTime(LocalTime stageEndTime) {
        return Instant.now().atZone(ZoneId.systemDefault()).toLocalTime().isAfter(stageEndTime);

    }



    /**
     * 计算当前时间所在的周期编号（从 1 开始）
     * 如果当前时间日期超过endTime的日期，则返回 -1
     *
     * @param startTime     周期起始时间（Instant）
     * @param endTime       有效时间范围结束时间（Instant）
     * @param perCycleDays  每个周期的天数
     * @param currentTime 当前时间（Instant）
     * @return 周期编号（从 1 开始），如果当前时间早于 startTime，返回 0,
     * 如果当前时间的日期大于endTime的日期返回-1
     */
    public static int calculateCurrentCycle(Instant currentTime, Instant startTime, Instant endTime, Integer perCycleDays) {
        // 如果当前时间小于起始时间，返回 0
        if (!isTournamentTimeStart(currentTime, startTime)) {
            return 0;
        }

        // 转为本地日期（按系统时区）
        ZoneId zone = ZoneId.systemDefault();
        LocalDate startDate = startTime.atZone(zone).toLocalDate();
        LocalDate endDate = endTime.atZone(zone).toLocalDate();
        LocalDate currentDate = currentTime.atZone(zone).toLocalDate();
        //当前日期大于结束日期
        if(currentDate.isAfter(endDate)){
            return -1;
        }

        // 计算从 startTime 到 currentTime 的天数差
        long daysBetween = ChronoUnit.DAYS.between(startDate, currentDate);

        // 周期编号 = 天数差 / n + 1（从 1 开始）
        return (int)(daysBetween / perCycleDays + 1);
    }

    public static boolean isTournamentTimeStart(Instant currentTime, Instant tournamentStartTime){
        return !currentTime.isBefore(tournamentStartTime);
    }

    public static boolean isAllCycleEnd(Instant currentTime, Instant tournamentEndTime){
        ZoneId zone = ZoneId.systemDefault();
        LocalDate endDate = tournamentEndTime.atZone(zone).toLocalDate();
        LocalDate currentDate = currentTime.atZone(zone).toLocalDate();
        return currentDate.isAfter(endDate);
    }

}
