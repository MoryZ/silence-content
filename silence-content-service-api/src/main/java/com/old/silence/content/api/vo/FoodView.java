package com.old.silence.content.api.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface FoodView {

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
