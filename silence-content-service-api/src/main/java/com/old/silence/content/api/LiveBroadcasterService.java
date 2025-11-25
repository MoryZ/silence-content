package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.LiveBroadcasterCommand;
import com.old.silence.content.api.dto.LiveBroadcasterQuery;
import com.old.silence.content.api.vo.LiveBroadcasterView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveBroadcaster服务接口
*/
interface LiveBroadcasterService {

    @GetMapping(value = "/liveBroadcasters/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(LiveBroadcasterView.class) Class<T> projectionType);

    @GetMapping(value = "/liveBroadcasters", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap LiveBroadcasterQuery query, Pageable pageable,
                        @ProjectedPayloadType(LiveBroadcasterView.class) Class<T> projectionType);

    @PostJsonMapping("/liveBroadcasters")
    BigInteger create(@RequestBody @Validated LiveBroadcasterCommand command);

    @PutJsonMapping(value = "/liveBroadcasters/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated LiveBroadcasterCommand command);

    @DeleteMapping("/liveBroadcasters/{id}")
    void deleteById(@PathVariable BigInteger id);
}