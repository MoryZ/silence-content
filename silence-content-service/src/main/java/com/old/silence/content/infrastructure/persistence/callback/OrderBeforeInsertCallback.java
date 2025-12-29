package com.old.silence.content.infrastructure.persistence.callback;

import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.takeout.Order;
import com.old.silence.content.util.ContentCodeUtils;
import com.old.silence.data.jdbc.core.mapping.event.BeforeInsertCallback;

import java.time.Instant;

/**
 * @author moryzang
 */
@Component
public class OrderBeforeInsertCallback implements BeforeInsertCallback<Order> {

    @Override
    public Order onBeforeInsert(Order order) {
        order.setCode("WD" + ContentCodeUtils.generateCode());
        if (order.getTaken()) {
            order.setPickupNo("A" + ContentCodeUtils.generateCode());
        }
        order.setCreateTime(Instant.now());
        return order;
    }
}
