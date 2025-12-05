package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.OrderMapper;
import com.old.silence.content.api.dto.OrderCommand;
import com.old.silence.content.api.dto.OrderCommentCommand;
import com.old.silence.content.api.dto.OrderQuery;
import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.content.domain.model.takeout.Order;
import com.old.silence.content.domain.repository.takeout.OrderRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class OrderResource implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResource(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public <T> Page<T> query(OrderQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Order.class);
        return orderRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return orderRepository.findById(id, projectionType);
    }

    @Override
    public BigInteger create(OrderCommand command) {
        var order = orderMapper.convert(command);
        orderRepository.create(order);
        return order.getId(); //NOSONAR
    }

    @Override
    public void updateComment(BigInteger id, OrderCommentCommand command) {
        validateModifyingResult(orderRepository.updateCommentById(id, command.getComment()));
    }

    @Override
    public void updateStatus(BigInteger id, OrderStatus orderStatus) {
        validateModifyingResult(orderRepository.updateStatusById(id, orderStatus));
    }
}
