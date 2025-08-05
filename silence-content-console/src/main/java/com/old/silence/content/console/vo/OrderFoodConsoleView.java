package com.old.silence.content.console.vo;

import java.math.BigDecimal;

/**
 * @author moryzang
 */
public interface OrderFoodConsoleView {

    FoodConsoleView getFood();

    Integer getNumber();

    BigDecimal getPrice();
}
