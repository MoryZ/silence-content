package com.old.silence.content.infrastructure.persistence.dao.support;

import jakarta.persistence.Transient;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class NumberStatsVo {

    private BigInteger userId;
    @Transient
    private Long indicatorAccumulation;


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
