package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentChallengeRecordCommand;
import com.old.silence.content.api.dto.TournamentChallengeRecordQuery;
import com.old.silence.content.api.vo.TournamentChallengeRecordView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

interface TournamentChallengeRecordService {
    @GetMapping(value = "/tournamentChallengeRecords/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(TournamentChallengeRecordView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentChallengeRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentChallengeRecordQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentChallengeRecordView.class) Class<T> projectionType);

    @PostJsonMapping("/tournamentChallengeRecords")
    BigInteger create(@RequestBody @Validated TournamentChallengeRecordCommand command);

    @PutJsonMapping(value = "/tournamentChallengeRecords/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentChallengeRecordCommand command);

    @DeleteMapping("/tournamentChallengeRecords/{id}")
    void deleteById(@PathVariable BigInteger id);
}
