package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.content.domain.model.Order;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author MurrayZhang
 * @Description
 */
public interface OrderDao extends JdbcRepository<Order, BigInteger> {

    int updateCommentById(String comment, BigInteger id);

    int updateStatusById(OrderStatus orderStatus, BigInteger id);
}
