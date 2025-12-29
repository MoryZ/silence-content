package com.old.silence.content.infrastructure.persistence.dao.takeout;

import com.old.silence.content.domain.model.takeout.Food;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface FoodDao extends JdbcRepository<Food, BigInteger> {
}
