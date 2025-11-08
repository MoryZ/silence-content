package com.old.silence.content.infrastructure.persistence.dao.support;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class NumberStatsVo {

    private BigInteger userId;
    private Long indicatorAccumulation;

    public NumberStatsVo() {
    }

    public NumberStatsVo(BigInteger userId, Long indicatorAccumulation) {
        this.userId = userId;
        this.indicatorAccumulation = indicatorAccumulation;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Long getIndicatorAccumulation() {
        return indicatorAccumulation;
    }

    public void setIndicatorAccumulation(Long indicatorAccumulation) {
        this.indicatorAccumulation = indicatorAccumulation;
    }

}
