package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.TournamentGroupRecordMapper;
import com.old.silence.content.api.dto.TournamentGroupRecordCommand;
import com.old.silence.content.api.dto.TournamentGroupRecordQuery;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * TournamentGroupRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentGroupRecordResource implements TournamentGroupRecordService {

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;
    private final TournamentGroupRecordMapper tournamentGroupRecordMapper;

    public TournamentGroupRecordResource(TournamentGroupRecordRepository tournamentGroupRecordRepository,
                                         TournamentGroupRecordMapper tournamentGroupRecordMapper) {
        this.tournamentGroupRecordRepository = tournamentGroupRecordRepository;
        this.tournamentGroupRecordMapper = tournamentGroupRecordMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentGroupRecordRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentGroupRecordQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentGroupRecord.class);
        return tournamentGroupRecordRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentGroupRecordCommand command) {
        var record = tournamentGroupRecordMapper.convert(command);
        tournamentGroupRecordRepository.create(record);
        return record.getId();
    }

    @Override
    public void update(BigInteger id, TournamentGroupRecordCommand command) {
        var record = tournamentGroupRecordMapper.convert(command);
        record.setId(id);
        validateModifyingResult(tournamentGroupRecordRepository.update(record));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(tournamentGroupRecordRepository.deleteById(id));
    }
}
