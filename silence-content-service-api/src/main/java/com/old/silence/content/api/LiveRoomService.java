package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.LiveRoomCommand;
import com.old.silence.content.api.dto.LiveRoomQuery;
import com.old.silence.content.api.vo.LiveRoomView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveRoom服务接口
*/
interface LiveRoomService {

    @GetMapping(value = "/liveRooms/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(LiveRoomView.class) Class<T> projectionType);

    @GetMapping(value = "/liveRooms", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap LiveRoomQuery query, Pageable pageable,
                        @ProjectedPayloadType(LiveRoomView.class) Class<T> projectionType);

    @PostJsonMapping("/liveRooms")
    BigInteger create(@RequestBody @Validated LiveRoomCommand command);

    @PutJsonMapping(value = "/liveRooms/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated LiveRoomCommand command);

    @DeleteMapping("/liveRooms/{id}")
    void deleteById(@PathVariable BigInteger id);
}