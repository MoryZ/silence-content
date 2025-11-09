package com.old.silence.content.api.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author moryzang
 */
public class StatsVo {

    private BigInteger userId;
    private BigDecimal accuracy;
    private Long indicatorAccumulation;

    public StatsVo() {
    }

    public StatsVo(BigInteger userId, BigDecimal accuracy) {
        this.userId = userId;
        this.accuracy = accuracy;
    }

    public StatsVo(BigInteger userId, Long indicatorAccumulation) {
        this.userId = userId;
        this.indicatorAccumulation = indicatorAccumulation;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public Long getIndicatorAccumulation() {
        return indicatorAccumulation;
    }

    public void setIndicatorAccumulation(Long indicatorAccumulation) {
        this.indicatorAccumulation = indicatorAccumulation;
    }
}
