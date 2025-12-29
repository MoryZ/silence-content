package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigDecimal;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface OrderFoodView {

    FoodView getFood();

    Integer getNumber();

    BigDecimal getPrice();
}
