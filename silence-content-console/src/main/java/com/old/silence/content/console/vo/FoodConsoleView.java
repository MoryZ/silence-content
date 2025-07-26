package com.old.silence.content.console.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * @author MurrayZhang
 * @Description
 */
public interface FoodConsoleView {

    BigInteger getId();

    BigInteger getCategoryId();

    String getName();

    BigDecimal getPrice();

    String getImageUrl();

    Integer getStatus();

    Instant getCreateTime();

    Instant getUpdateTime();

    Instant getDeleteTime();
}
