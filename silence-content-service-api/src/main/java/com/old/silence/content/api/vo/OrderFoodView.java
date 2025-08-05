package com.old.silence.content.api.vo;

import java.math.BigDecimal;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface OrderFoodView {

    FoodView getFood();

    Integer getNumber();

    BigDecimal getPrice();
}
