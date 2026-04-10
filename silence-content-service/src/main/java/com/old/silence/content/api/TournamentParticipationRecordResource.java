package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.TournamentParticipationRecordMapper;
import com.old.silence.content.api.dto.TournamentParticipationRecordCommand;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * TournamentParticipationRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentParticipationRecordResource implements TournamentParticipationRecordService {

    private final TournamentParticipationRecordRepository repository;
    private final TournamentParticipationRecordMapper mapper;

    public TournamentParticipationRecordResource(TournamentParticipationRecordRepository repository,
                                                 TournamentParticipationRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return repository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentParticipationRecordQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentParticipationRecord.class);
        return repository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentParticipationRecordCommand command) {
        var record = mapper.convert(command);
        repository.create(record);
        return record.getId();
    }

    @Override
    public void update(BigInteger id, TournamentParticipationRecordCommand command) {
        var record = mapper.convert(command);
        record.setId(id);
        validateModifyingResult(repository.update(record));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(repository.deleteById(id));
    }
}
