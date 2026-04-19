package com.old.silence.content.api.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.tournament.TournamentGroupMapper;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupQuery;
import com.old.silence.content.api.tournament.tournament.service.TournamentGroupService;
import com.old.silence.content.domain.service.tournament.TournamentGroupDomainService;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentGroupDao;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1")
public class TournamentGroupResource implements TournamentGroupService {

    private final TournamentGroupDomainService tournamentGroupDomainService;
    private final TournamentGroupMapper tournamentGroupMapper;

    private final TournamentGroupDao tournamentGroupDao;

    public TournamentGroupResource(TournamentGroupDomainService tournamentGroupDomainService,
                                   TournamentGroupMapper tournamentGroupMapper, TournamentGroupDao tournamentGroupDao) {
        this.tournamentGroupDomainService = tournamentGroupDomainService;
        this.tournamentGroupMapper = tournamentGroupMapper;
        this.tournamentGroupDao = tournamentGroupDao;
    }


    @Override
    public <T> Page<T> query(TournamentGroupQuery query, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupDomainService.query(query, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentGroupCommand command) {
        var tournamentGroup = tournamentGroupMapper.convert(command);
        return tournamentGroupDomainService.create(tournamentGroup);
    }

    public Integer update(@PathVariable BigInteger id, @RequestBody TournamentGroupCommand command) {
        var tournamentGroup = tournamentGroupMapper.convert(command);
        tournamentGroup.setId(id);
        return tournamentGroupDao.updateNonNull(tournamentGroup);
    }

    public Integer delete(@PathVariable BigInteger id) {
        return tournamentGroupDao.deleteById(id);
    }
}
