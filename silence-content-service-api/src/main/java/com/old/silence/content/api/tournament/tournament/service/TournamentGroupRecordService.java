package com.old.silence.content.api.tournament.tournament.service;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordQuery;
import com.old.silence.content.api.tournament.tournament.vo.TournamentGroupRecordView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;

/**
 * TournamentGroupRecord服务接口
 */
public interface TournamentGroupRecordService {


    @GetMapping(value = "/tournamentGroupRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentGroupRecordQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentGroupRecordView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentGroupRecords")
    BigInteger create(@RequestBody @Validated TournamentGroupRecordCommand command);

    @PutMapping("/tournamentGroupRecords/regrouping")
    void regrouping(@RequestParam BigInteger eventGameId, @RequestParam Integer cycleNo);
}
