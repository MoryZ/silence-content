package com.old.silence.content.api.tournament.tournament.service;


import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.old.silence.content.api.dto.TournamentRegisterCommand;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.tournament.tournament.vo.TournamentParticipationRecordView;
import com.old.silence.content.api.vo.TournamentRegisterVo;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.List;

public interface TournamentParticipantService {

    @PostJsonMapping("/tournament/register")
    Response<TournamentRegisterVo> tournamentRegister(@RequestBody @Valid TournamentRegisterCommand command);


    @GetMapping(value = "/tournament/participantRecord/queryRobotRecord", params = {"pageNo", "pageSize"})
    <T> List<T> queryRobotRecord(@RequestParam BigInteger eventGameId,
                                     @RequestParam BigInteger id, Pageable pageable,
                                     @ProjectedPayloadType(TournamentParticipationRecordView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentParticipationRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentParticipationRecordQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentParticipationRecordView.class) Class<T> projectionType);
}
