package com.old.silence.content.console.api.takeout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.OrderClient;
import com.old.silence.content.api.dto.OrderCommentCommand;
import com.old.silence.content.api.dto.OrderQuery;
import com.old.silence.content.console.api.assembler.OrderCommandMapper;
import com.old.silence.content.console.dto.OrderConsoleCommand;
import com.old.silence.content.console.dto.OrderConsoleOnlyCommentCommand;
import com.old.silence.content.console.vo.OrderConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.data.commons.domain.BigIdOnlyView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class OrderResource {

    private final OrderClient orderClient;
    private final OrderCommandMapper orderCommandMapper;

    public OrderResource(OrderClient orderClient,
                         OrderCommandMapper orderCommandMapper) {
        this.orderClient = orderClient;
        this.orderCommandMapper = orderCommandMapper;
    }

    @GetMapping(value = "/orders/{id}")
    public OrderConsoleView findById(@PathVariable BigInteger id) {
        return orderClient.findById(id, OrderConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/orders")
    public Page<OrderConsoleView> query(OrderQuery orderQuery, Pageable pageable) {
        return orderClient.query(orderQuery, pageable, OrderConsoleView.class);
    }

    @PostMapping("/orders")
    public BigIdOnlyView create(@RequestBody OrderConsoleCommand command) {
        var orderCommand = orderCommandMapper.convert(command);
        return new BigIdOnlyView(orderClient.create(orderCommand));
    }

    @PutMapping("/orders/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody OrderConsoleOnlyCommentCommand command) {
        orderClient.updateComment(id, new OrderCommentCommand(command.getComment()));
    }
}
