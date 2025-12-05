package com.old.silence.content.infrastructure.persistence.dao.takeout;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.content.domain.model.takeout.Order;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface OrderDao extends JdbcRepository<Order, BigInteger> {

    int updateCommentById(String comment, BigInteger id);

    int updateStatusById(OrderStatus orderStatus, BigInteger id);
}
