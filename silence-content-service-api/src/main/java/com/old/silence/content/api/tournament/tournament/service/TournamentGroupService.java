package com.old.silence.content.api.tournament.tournament.service;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupQuery;
import com.old.silence.content.api.tournament.tournament.vo.TournamentGroupView;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;

/**
 * TournamentGroup服务接口
 */
public interface TournamentGroupService {

    @GetMapping(value = "/tournamentGroups", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentGroupQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentGroupView.class) Class<T> projectionType);

    @PostMapping("/tournamentGroups")
    BigInteger create(@RequestBody @Validated TournamentGroupCommand command);

    @PutJsonMapping(value = "/tournamentGroups/{id}")
    Integer update(@PathVariable BigInteger id, @RequestBody TournamentGroupCommand tournamentParticipantRecord);

    @DeleteMapping(value = "/tournamentGroups/{id}")
    Integer delete(@PathVariable BigInteger id);

}
