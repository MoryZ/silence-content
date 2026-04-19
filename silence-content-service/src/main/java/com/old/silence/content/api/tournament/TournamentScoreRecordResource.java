package com.old.silence.content.api.tournament;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.tournament.tournament.service.TournamentScoreRecordService;
import com.old.silence.content.domain.service.tournament.TournamentScoreRecordDomainService;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TournamentScoreRecordResource implements TournamentScoreRecordService {


    private TournamentScoreRecordDomainService tournamentScoreRecordDomainService;

    @Override
    public <T> List<T> queryGlobalScoreList(BigInteger eventGameId, BigInteger id, Pageable pageable, Class<T> projectionType) {
        return tournamentScoreRecordDomainService.queryGlobalScoreList(eventGameId,id,pageable,projectionType);
    }

    @Override
    public void calculateTournamentScoreByEventGameId(BigInteger eventGameId) {
        tournamentScoreRecordDomainService.calculateTournamentScoreByEventGameId(eventGameId);
    }
}
