package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.old.silence.content.api.dto.OrderCommand;
import com.old.silence.content.api.dto.OrderCommentCommand;
import com.old.silence.content.api.dto.OrderQuery;
import com.old.silence.content.api.vo.OrderView;
import com.old.silence.content.domain.enums.OrderStatus;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author MurrayZhang
 * @Description
 */
interface OrderService {


    @GetMapping(value = "/orders", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated OrderQuery query, Pageable pageable,
                      @ProjectedPayloadType(OrderView.class) Class<T> projectionType);

    @GetMapping(value = "/orders/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(OrderView.class) Class<T> projectionType);

    @PostMapping("/orders")
    BigInteger create(@RequestBody @Validated OrderCommand command);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/orders/{id}")
    void updateComment(@PathVariable BigInteger id, @RequestBody OrderCommentCommand orderCommentCommand);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/orders/{id}/pay")
    void updateStatus(@PathVariable BigInteger id, @RequestBody OrderStatus orderStatus);
}
