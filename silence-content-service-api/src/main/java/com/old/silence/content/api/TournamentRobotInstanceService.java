package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentRobotInstanceCommand;
import com.old.silence.content.api.dto.TournamentRobotInstanceQuery;
import com.old.silence.content.api.vo.TournamentRobotInstanceView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

interface TournamentRobotInstanceService {
    @GetMapping(value = "/tournamentRobotInstances/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(TournamentRobotInstanceView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentRobotInstances", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentRobotInstanceQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentRobotInstanceView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentRobotInstances")
    BigInteger create(@RequestBody @Validated TournamentRobotInstanceCommand command);

    @PutJsonMapping(value = "/tournamentRobotInstances/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentRobotInstanceCommand command);

    @DeleteMapping("/tournamentRobotInstances/{id}")
    void deleteById(@PathVariable BigInteger id);
}
