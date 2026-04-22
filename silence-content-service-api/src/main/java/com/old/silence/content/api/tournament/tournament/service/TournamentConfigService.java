package com.old.silence.content.api.tournament.tournament.service;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigQuery;
import com.old.silence.content.api.tournament.tournament.dto.TournamentUserParticipationInfoCommand;
import com.old.silence.content.api.tournament.tournament.vo.TournamentConfigView;
import com.old.silence.content.api.tournament.tournament.vo.TournamentConfigVo;
import com.old.silence.content.api.tournament.tournament.vo.TournamentUserParticipationInfoVo;
import com.old.silence.web.bind.annotation.GetJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentConfigService {

    @GetJsonMapping("/tournament/detail")
    Response<TournamentConfigVo> getTournamentDetail(@RequestParam BigInteger eventGameId);

    @GetMapping("/tournaments")
    <T> Optional<T> findByEventGameId(@RequestParam BigInteger eventGameId, @ProjectedPayloadType(TournamentConfigView.class) Class<T> projectionType);

    @GetMapping(value = "/tournaments", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@SpringQueryMap TournamentConfigQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentConfigView.class) Class<T> projectionType);

    @PostMapping("/tournaments")
    BigInteger create(@RequestBody TournamentConfigDomainCommand tournamentConfigDomainCommand);

    @PutMapping("/tournaments/{id}")
    int update(@PathVariable BigInteger id, @RequestBody TournamentConfigDomainCommand tournamentConfigDomainCommand);

    @GetJsonMapping("/tournament/user")
    Response<TournamentUserParticipationInfoVo> queryTournamentUserParticipationInfo(@SpringQueryMap @Validated TournamentUserParticipationInfoCommand command);
}
