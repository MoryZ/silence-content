package com.old.silence.content.infrastructure.persistence.callback;

import java.time.Instant;

import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.takeout.Order;
import com.old.silence.content.util.ContentCodeUtils;
import com.old.silence.data.jdbc.core.mapping.event.BeforeInsertCallback;

/**
 * @author moryzang
 */
@Component
public class OrderBeforeInsertCallback implements BeforeInsertCallback<Order> {

    @Override
    public Order onBeforeInsert(Order order) {
        order.setCode("WD" + ContentCodeUtils.generateCode(18));
        if (order.getTaken()) {
            order.setPickupNo("A" + ContentCodeUtils.generateCode(4));
        }
        order.setCreateTime(Instant.now());
        return order;
    }
}
