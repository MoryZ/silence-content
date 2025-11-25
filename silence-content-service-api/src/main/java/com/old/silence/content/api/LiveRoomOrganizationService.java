package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.LiveRoomOrganizationCommand;
import com.old.silence.content.api.dto.LiveRoomOrganizationQuery;
import com.old.silence.content.api.vo.LiveRoomOrganizationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveRoomOrganization服务接口
*/
interface LiveRoomOrganizationService {

    @GetMapping(value = "/liveRoomOrganizations/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(LiveRoomOrganizationView.class) Class<T> projectionType);

    @GetMapping(value = "/liveRoomOrganizations", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap LiveRoomOrganizationQuery query, Pageable pageable,
                        @ProjectedPayloadType(LiveRoomOrganizationView.class) Class<T> projectionType);

    @PostJsonMapping("/liveRoomOrganizations")
    BigInteger create(@RequestBody @Validated LiveRoomOrganizationCommand command);

    @PutJsonMapping(value = "/liveRoomOrganizations/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated LiveRoomOrganizationCommand command);

    @DeleteMapping("/liveRoomOrganizations/{id}")
    void deleteById(@PathVariable BigInteger id);
}