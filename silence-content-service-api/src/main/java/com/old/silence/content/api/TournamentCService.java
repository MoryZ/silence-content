package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.TournamentChallengeCompleteCommand;
import com.old.silence.content.api.dto.TournamentChallengeStartCommand;
import com.old.silence.content.api.dto.TournamentUserRegisterCommand;
import com.old.silence.content.api.vo.TournamentCListItemView;
import com.old.silence.content.api.vo.TournamentCRankingView;
import com.old.silence.content.api.vo.TournamentUserStatusView;

import java.math.BigInteger;
import java.util.Optional;

interface TournamentCService {

    @GetMapping(value = "/c/tournaments", params = {"pageNo", "pageSize"})
    Page<TournamentCListItemView> listTournaments(Pageable pageable);

    @PostMapping("/c/tournaments/{eventGameId}/registrations")
    BigInteger register(@PathVariable BigInteger eventGameId,
                        @RequestBody @Validated TournamentUserRegisterCommand command);

    @GetMapping("/c/tournaments/{eventGameId}/users/{participantId}/status")
    TournamentUserStatusView getUserStatus(@PathVariable BigInteger eventGameId,
                                           @PathVariable String participantId);

    @PostMapping("/c/tournaments/{eventGameId}/challenges/start")
    BigInteger startChallenge(@PathVariable BigInteger eventGameId,
                              @RequestBody @Validated TournamentChallengeStartCommand command);

    @PostMapping("/c/tournaments/{eventGameId}/challenges/{challengeRecordId}/complete")
    void completeChallenge(@PathVariable BigInteger eventGameId,
                           @PathVariable BigInteger challengeRecordId,
                           @RequestBody @Validated TournamentChallengeCompleteCommand command);

    @GetMapping(value = "/c/tournaments/{eventGameId}/rankings", params = {"pageNo", "pageSize"})
    Page<TournamentCRankingView> getRankings(@PathVariable BigInteger eventGameId,
                                             @RequestParam(required = false) com.old.silence.content.domain.enums.tournament.TournamentRankingType rankingType,
                                             Pageable pageable);

    @GetMapping("/c/tournaments/{eventGameId}/rankings/me")
    Optional<TournamentCRankingView> getCurrentUserRanking(@PathVariable BigInteger eventGameId,
                                                           @RequestParam String participantId,
                                                           @RequestParam(required = false) com.old.silence.content.domain.enums.tournament.TournamentRankingType rankingType);
}
