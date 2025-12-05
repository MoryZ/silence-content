package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.takeout.OrderFood;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface OrderFoodDao extends JdbcRepository<OrderFood, BigInteger> {
}
