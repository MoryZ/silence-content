package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.LiveBroadcasterMapper;
import com.old.silence.content.api.dto.LiveBroadcasterCommand;
import com.old.silence.content.api.dto.LiveBroadcasterQuery;
import com.old.silence.content.domain.model.LiveBroadcaster;
import com.old.silence.content.domain.repository.LiveBroadcasterRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* LiveBroadcaster资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveBroadcasterResource implements LiveBroadcasterService {
    private final LiveBroadcasterRepository liveBroadcasterRepository;
    private final LiveBroadcasterMapper liveBroadcasterMapper;

    public LiveBroadcasterResource(LiveBroadcasterRepository liveBroadcasterRepository,
                                LiveBroadcasterMapper liveBroadcasterMapper) {
        this.liveBroadcasterRepository = liveBroadcasterRepository;
        this.liveBroadcasterMapper = liveBroadcasterMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveBroadcasterRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(LiveBroadcasterQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, LiveBroadcaster.class);
        return liveBroadcasterRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(LiveBroadcasterCommand command) {
        var liveBroadcaster = liveBroadcasterMapper.convert(command);
        liveBroadcasterRepository.create(liveBroadcaster);
        return liveBroadcaster.getId();
    }

    @Override
    public void update(BigInteger id, LiveBroadcasterCommand command) {
        var liveBroadcaster = liveBroadcasterMapper.convert(command);
        liveBroadcaster.setId(id);
        validateModifyingResult(liveBroadcasterRepository.update(liveBroadcaster));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(liveBroadcasterRepository.deleteById(id));
    }
}