package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.TournamentGroupMapper;
import com.old.silence.content.api.dto.TournamentGroupCommand;
import com.old.silence.content.api.dto.TournamentGroupQuery;
import com.old.silence.content.domain.model.tournament.TournamentGroup;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * TournamentGroup资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentGroupResource implements TournamentGroupService {

    private final TournamentGroupRepository tournamentGroupRepository;
    private final TournamentGroupMapper tournamentGroupMapper;

    public TournamentGroupResource(TournamentGroupRepository tournamentGroupRepository,
                                   TournamentGroupMapper tournamentGroupMapper) {
        this.tournamentGroupRepository = tournamentGroupRepository;
        this.tournamentGroupMapper = tournamentGroupMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentGroupRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentGroupQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentGroup.class);
        return tournamentGroupRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentGroupCommand command) {
        var tournamentGroup = tournamentGroupMapper.convert(command);
        tournamentGroupRepository.create(tournamentGroup);
        return tournamentGroup.getId();
    }

    @Override
    public void update(BigInteger id, TournamentGroupCommand command) {
        var tournamentGroup = tournamentGroupMapper.convert(command);
        tournamentGroup.setId(id);
        validateModifyingResult(tournamentGroupRepository.update(tournamentGroup));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(tournamentGroupRepository.deleteById(id));
    }
}
