package com.old.silence.content.domain.repository.takeout;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.content.domain.model.takeout.Order;

import java.math.BigInteger;
import java.util.Optional;


public interface OrderRepository {

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    int create(Order order);

    int updateCommentById(BigInteger id, String comment);

    int updateStatusById(BigInteger id, OrderStatus orderStatus);
}
