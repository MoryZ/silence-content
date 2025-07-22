package com.old.silence.content.console.vo;

import java.math.BigDecimal;

/**
 * @author MurrayZhang
 * @Description
 */
public interface OrderFoodConsoleView {

    FoodConsoleView getFood();

    Integer getNumber();

    BigDecimal getPrice();
}
