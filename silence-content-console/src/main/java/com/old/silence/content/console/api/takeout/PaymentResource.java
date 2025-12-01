package com.old.silence.content.console.api.takeout;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.OrderClient;
import com.old.silence.content.domain.enums.OrderStatus;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class PaymentResource {

    private final OrderClient orderClient;

    public PaymentResource(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @PostMapping("/payments/{id}")
    public void create(@PathVariable BigInteger id) {
        // 支付动作
        // 支付成功，更改订单状态

        orderClient.updateStatus(id, OrderStatus.PAID);
    }
}
