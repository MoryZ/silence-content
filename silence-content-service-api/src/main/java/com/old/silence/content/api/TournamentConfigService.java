package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentConfigCommand;
import com.old.silence.content.api.dto.TournamentConfigQuery;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentConfig服务接口
 */
interface TournamentConfigService {

    @GetMapping(value = "/tournamentConfigs/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(TournamentConfigView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentConfigs", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentConfigQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentConfigView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentConfigs")
    BigInteger create(@RequestBody @Validated TournamentConfigCommand command);

    @PutJsonMapping(value = "/tournamentConfigs/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentConfigCommand command);

    @DeleteMapping("/tournamentConfigs/{id}")
    void deleteById(@PathVariable BigInteger id);
}