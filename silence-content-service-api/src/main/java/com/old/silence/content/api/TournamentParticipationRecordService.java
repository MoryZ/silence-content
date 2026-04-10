package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentParticipationRecordCommand;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.vo.TournamentParticipationRecordView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentParticipationRecord服务接口
 */
interface TournamentParticipationRecordService {

    @GetMapping(value = "/tournamentParticipationRecords/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(TournamentParticipationRecordView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentParticipationRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentParticipationRecordQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentParticipationRecordView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentParticipationRecords")
    BigInteger create(@RequestBody @Validated TournamentParticipationRecordCommand command);

    @PutJsonMapping(value = "/tournamentParticipationRecords/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentParticipationRecordCommand command);

    @DeleteMapping("/tournamentParticipationRecords/{id}")
    void deleteById(@PathVariable BigInteger id);
}
