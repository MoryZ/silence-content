package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * @author moryzang
 */
public interface OrderBasicConsoleView {

    BigInteger getId();

    String getCode();

    String getPickupNo();

    String getUserId();

    BigDecimal getPrice();

    BigDecimal getPromotion();

    Integer getNumber();

    Boolean getPaid();

    Boolean getTaken();

    String getComment();

    OrderStatus getStatus();

    Instant getCreateTime();

    Instant getPayTime();

    Instant getTakenTime();
}
