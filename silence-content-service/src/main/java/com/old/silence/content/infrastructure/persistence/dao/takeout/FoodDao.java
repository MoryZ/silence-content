package com.old.silence.content.infrastructure.persistence.dao.takeout;

import java.math.BigInteger;

import com.old.silence.content.domain.model.takeout.Food;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface FoodDao extends JdbcRepository<Food, BigInteger> {
}
