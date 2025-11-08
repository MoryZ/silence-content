package com.old.silence.content.infrastructure.persistence.dao.support;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author moryzang
 */
public class BigDecimalStatsVo {

    private BigInteger userId;
    private BigDecimal accuracy;      // 正确率

    public BigDecimalStatsVo() {
    }

    public BigDecimalStatsVo(BigInteger userId, BigDecimal accuracy) {
        this.userId = userId;
        this.accuracy = accuracy;
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

}
