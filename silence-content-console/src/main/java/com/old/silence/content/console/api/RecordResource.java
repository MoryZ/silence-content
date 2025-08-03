package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.OrderClient;
import com.old.silence.content.api.dto.OrderQuery;
import com.old.silence.content.console.vo.OrderBasicConsoleView;

/**
 * @author moryzang
 * @Description
 */
@RestController
@RequestMapping("/api/v1")
public class RecordResource {

    private final OrderClient orderClient;

    public RecordResource(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping(value = "/records")
    public Page<OrderBasicConsoleView> query(OrderQuery orderQuery, Pageable pageable) {
        return orderClient.query(orderQuery, pageable, OrderBasicConsoleView.class);
    }
}
