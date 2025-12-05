package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.content.domain.model.takeout.Order;
import com.old.silence.content.domain.repository.takeout.OrderRepository;
import com.old.silence.content.infrastructure.persistence.dao.takeout.OrderDao;
import com.old.silence.content.infrastructure.persistence.dao.takeout.OrderFoodDao;


@Repository
public class OrderMyBatisRepository implements OrderRepository {

    private final OrderDao orderDao;
    private final OrderFoodDao orderFoodDao;

    public OrderMyBatisRepository(OrderDao orderDao,
                                  OrderFoodDao orderFoodDao) {
        this.orderDao = orderDao;
        this.orderFoodDao = orderFoodDao;
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return orderDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return orderDao.findById(id, projectionType);
    }

    @Override
    public int create(Order order) {

        var rowsAffected = orderDao.insert(order);

        order.getOrderFoods().forEach(orderFood ->
                orderFood.setOrderId(order.getId())
        );
        orderFoodDao.insertAll(order.getOrderFoods());
        return rowsAffected;
    }

    @Override
    public int updateCommentById(BigInteger id, String comment) {
        return orderDao.updateCommentById(comment, id);
    }

    @Override
    public int updateStatusById(BigInteger id, OrderStatus orderStatus) {
        return orderDao.updateStatusById(orderStatus, id);
    }
}
