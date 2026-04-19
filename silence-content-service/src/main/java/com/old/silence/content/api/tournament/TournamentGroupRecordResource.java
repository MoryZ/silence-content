package com.old.silence.content.api.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.tournament.TournamentGroupRecordMapper;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordQuery;
import com.old.silence.content.api.tournament.tournament.service.TournamentGroupRecordService;
import com.old.silence.content.domain.service.tournament.TournamentGroupRecordDomainService;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1")
public class TournamentGroupRecordResource implements TournamentGroupRecordService {

    private final TournamentGroupRecordDomainService tournamentGroupDomainService;
    private final TournamentGroupRecordMapper tournamentGroupRecordMapper;


    public TournamentGroupRecordResource(TournamentGroupRecordDomainService tournamentGroupDomainService,
                                         TournamentGroupRecordMapper tournamentGroupRecordMapper) {
        this.tournamentGroupDomainService = tournamentGroupDomainService;
        this.tournamentGroupRecordMapper = tournamentGroupRecordMapper;
    }

    @Override
    public <T> Page<T> query(TournamentGroupRecordQuery query, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupDomainService.query(query, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentGroupRecordCommand command) {
        var tournamentGroupRecord = tournamentGroupRecordMapper.convert(command);
        return tournamentGroupDomainService.create(tournamentGroupRecord);
    }

    @Override
    public void regrouping(BigInteger eventGameId, Integer cycleNo) {
        tournamentGroupDomainService.performRegrouping(eventGameId, cycleNo);
    }
}
