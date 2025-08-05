package com.old.silence.content.api.vo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CategoryView {

    BigInteger getId();

    String getName();

    Integer getSort();

    List<FoodView> getFoods();
}
