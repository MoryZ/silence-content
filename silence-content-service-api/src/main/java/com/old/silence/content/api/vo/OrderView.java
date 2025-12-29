package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface OrderView {

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

    List<OrderFoodView> getOrderFoods();
}
