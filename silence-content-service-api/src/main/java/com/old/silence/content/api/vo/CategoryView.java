package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;
import java.util.List;

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
