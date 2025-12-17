package com.old.silence.content.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import com.old.silence.core.time.DateTimeUtils;
import com.old.silence.flake.SilenceFlakeException;

/**
 * @author moryzang
 */
public class FlakeIdUtils {

    static final Instant EPOCH_INSTANT = DateTimeUtils.toInstant(LocalDate.of(2017, 1, 1), ZoneId.of("Asia/Shanghai"));

    static final long EPOCH = EPOCH_INSTANT.toEpochMilli();

    static final int ELAPSED_MILLIS_BITS = 36;

    static final long MAX_ELAPSED_TIME = ~(-1L << ELAPSED_MILLIS_BITS);

    static final int WORKER_ID_BITS = 13;

    public static final int MAX_WORKER_ID = ~(-1 << WORKER_ID_BITS);

    static final int DATA_CENTER_BITS = 4;

    public static final int MAX_DATA_CENTER_ID = ~(-1 << DATA_CENTER_BITS);

    static final int CLOCK_DRIFT_BITS = 2;

    static final int MAX_ALLOWED_CLOCK_DRIFT_TIMES = ~(-1 << CLOCK_DRIFT_BITS);

    static final int SEQUENCE_BITS = 8;

    static final int MAX_SEQUENCE = ~(-1 << SEQUENCE_BITS);

    static final int SILENCE_FLAKE_TIME_UNIT_SHIFT_BITS = 5;

    static final int WORKER_LEFT_SHIFT_BITS = DATA_CENTER_BITS;

    static final int CLOCK_DRIFT_LEFT_SHIFT_BITS = WORKER_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    static final int SEQUENCE_LEFT_SHIFT_BITS = CLOCK_DRIFT_LEFT_SHIFT_BITS + CLOCK_DRIFT_BITS;

    static final int TIMESTAMP_LEFT_SHIFT_BITS = SEQUENCE_LEFT_SHIFT_BITS + SEQUENCE_BITS;

    static final int MAX_ALLOWED_CLOCK_DRIFT_TIME_UNIT = 8;

    private final int workerId;

    private final int dataCenterId;

    private final Clock clock;

    private long sequence = 0L;

    private long clockDriftTimes = 0L;

    /**
     * 当前序列号生成周期内的时钟回拨次数
     */
    private long elapsedTime = 1L;

    private long clockBackSegment = 0L;

    private long nextMilliSecond;

    public FlakeIdUtils(int workerId, int dataCenterId, Clock clock) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new SilenceFlakeException(String.format("当前的workerId[%d]超出范围[%d,%d]", workerId, 0, MAX_WORKER_ID));
        }

        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new SilenceFlakeException(String.format("当前的dataCenterId[%d]超出范围[%d,%d]", dataCenterId, 0, MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.clock = Objects.requireNonNull(clock);
    }


    /**
     * 生成全局ID
     *
     * @return 全局ID
     */
    public synchronized long nextId() {

        var now = clock.millis();
        var currentElapsedTime = now - EPOCH >> SILENCE_FLAKE_TIME_UNIT_SHIFT_BITS;

        if (currentElapsedTime < 0) {
            throw new SilenceFlakeException("当前系统时间不能早于" + EPOCH_INSTANT);
        }
        if (currentElapsedTime > MAX_ELAPSED_TIME) {
            throw new SilenceFlakeException("序列号生成周期超出最大值");
        }

        if (currentElapsedTime < elapsedTime) {
            if (elapsedTime - currentElapsedTime >= MAX_ALLOWED_CLOCK_DRIFT_TIME_UNIT) {
                throw new SilenceFlakeException("时钟回拨超出最大忍耐值");
            }
            if (currentElapsedTime > clockBackSegment) {
                clockBackSegment = elapsedTime;
            }

            // 时钟回拨保护
            this.clockDriftTimes = this.clockDriftTimes + 1 & MAX_ALLOWED_CLOCK_DRIFT_TIMES;
            // 超出最大时钟回拨保护次数
            if (this.clockDriftTimes == 0L) {
                throw new SilenceFlakeException("时钟回拨超出最大次数");
            }
        } else if (currentElapsedTime > clockBackSegment + MAX_ALLOWED_CLOCK_DRIFT_TIME_UNIT) {
            clockBackSegment = 0L;
            this.clockDriftTimes = 0L;
        }
        if (currentElapsedTime == elapsedTime) {
            this.sequence = this.sequence + 1L & MAX_SEQUENCE;
            if (this.sequence == 0L) {
                // 当前序列号生成周期内序列号溢出
                currentElapsedTime = nextTimeSegment();
            }
        } else {
            // 开始下一生产周期
            // 序列号复位
            this.sequence = 0L;
            // 计算下周期开始的时间戳，用于在序列号生产周期序列号溢出后等待下一周期判断
            setNextMilliSecond(now);
        }

        this.elapsedTime = currentElapsedTime;
        return currentElapsedTime << TIMESTAMP_LEFT_SHIFT_BITS | sequence << SEQUENCE_LEFT_SHIFT_BITS
                | clockDriftTimes << CLOCK_DRIFT_LEFT_SHIFT_BITS | workerId << WORKER_LEFT_SHIFT_BITS | dataCenterId;


    }

    // 设置下一周期的起始毫秒数
    private void setNextMilliSecond(long now) {
        this.nextMilliSecond = (now >> SILENCE_FLAKE_TIME_UNIT_SHIFT_BITS) + 1 << SILENCE_FLAKE_TIME_UNIT_SHIFT_BITS;
    }

    /**
     *  如果一个序列号生成周期内，sequence溢出，则需要等待下一seq生成周期，并返回下一周期时间戳segment
     *
     * @return 下一周期时间戳segment
     */
    private long nextTimeSegment() {
        var timestamp = 0L;
        while (timestamp < this.nextMilliSecond) {
            timestamp = clock.millis();
        }
        setNextMilliSecond(timestamp);
        return timestamp - EPOCH >> SILENCE_FLAKE_TIME_UNIT_SHIFT_BITS;
    }
}
