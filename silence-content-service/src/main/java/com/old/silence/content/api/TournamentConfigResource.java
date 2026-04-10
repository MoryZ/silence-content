package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.TournamentConfigMapper;
import com.old.silence.content.api.dto.TournamentConfigCommand;
import com.old.silence.content.api.dto.TournamentConfigQuery;
import com.old.silence.content.domain.model.tournament.TournamentConfig;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * TournamentConfig资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentConfigResource implements TournamentConfigService {
    private final TournamentConfigRepository tournamentConfigRepository;
    private final TournamentConfigMapper tournamentConfigMapper;

    public TournamentConfigResource(TournamentConfigRepository tournamentConfigRepository,
                                    TournamentConfigMapper tournamentConfigMapper) {
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentConfigMapper = tournamentConfigMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentConfigRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentConfigQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentConfig.class);
        return tournamentConfigRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentConfigCommand command) {
        var tournamentConfig = tournamentConfigMapper.convert(command);
        tournamentConfigRepository.create(tournamentConfig);
        return tournamentConfig.getId();
    }

    @Override
    public void update(BigInteger id, TournamentConfigCommand command) {
        var tournamentConfig = tournamentConfigMapper.convert(command);
        tournamentConfig.setId(id);
        validateModifyingResult(tournamentConfigRepository.update(tournamentConfig));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(tournamentConfigRepository.deleteById(id));
    }
}
