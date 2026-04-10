package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentRankingCommand;
import com.old.silence.content.api.dto.TournamentRankingQuery;
import com.old.silence.content.api.vo.TournamentRankingView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

interface TournamentRankingService {
    @GetMapping(value = "/tournamentRankings/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(TournamentRankingView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentRankings", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentRankingQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentRankingView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentRankings")
    BigInteger create(@RequestBody @Validated TournamentRankingCommand command);

    @PutJsonMapping(value = "/tournamentRankings/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentRankingCommand command);

    @DeleteMapping("/tournamentRankings/{id}")
    void deleteById(@PathVariable BigInteger id);
}
