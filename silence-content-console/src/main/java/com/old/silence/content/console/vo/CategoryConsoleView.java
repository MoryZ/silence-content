package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface CategoryConsoleView {

    @JsonProperty("categoryId")
    BigInteger getId();

    String getName();

    Long getSort();

    List<FoodConsoleView> getFoods();
}
