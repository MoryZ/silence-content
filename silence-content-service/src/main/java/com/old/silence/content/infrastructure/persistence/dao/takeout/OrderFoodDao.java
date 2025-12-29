package com.old.silence.content.infrastructure.persistence.dao.takeout;

import com.old.silence.content.domain.model.takeout.OrderFood;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface OrderFoodDao extends JdbcRepository<OrderFood, BigInteger> {
}
