package com.old.silence.content.console.vo;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author MurrayZhang
 * @Description
 */
public interface CategoryConsoleView {

    @JsonProperty("categoryId")
    BigInteger getId();

    String getName();

    Long getSort();

    List<FoodConsoleView> getFoods();
}
