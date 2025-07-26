package com.old.silence.content.console.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.content.domain.enums.OrderStatus;

/**
 * @author MurrayZhang
 * @Description
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
