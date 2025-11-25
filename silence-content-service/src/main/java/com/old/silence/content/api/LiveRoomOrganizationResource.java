package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.LiveRoomOrganizationMapper;
import com.old.silence.content.api.dto.LiveRoomOrganizationCommand;
import com.old.silence.content.api.dto.LiveRoomOrganizationQuery;
import com.old.silence.content.domain.model.LiveRoomOrganization;
import com.old.silence.content.domain.repository.LiveRoomOrganizationRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* LiveRoomOrganization资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveRoomOrganizationResource implements LiveRoomOrganizationService {
    private final LiveRoomOrganizationRepository liveRoomOrganizationRepository;
    private final LiveRoomOrganizationMapper liveRoomOrganizationMapper;

    public LiveRoomOrganizationResource(LiveRoomOrganizationRepository liveRoomOrganizationRepository,
                                LiveRoomOrganizationMapper liveRoomOrganizationMapper) {
        this.liveRoomOrganizationRepository = liveRoomOrganizationRepository;
        this.liveRoomOrganizationMapper = liveRoomOrganizationMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveRoomOrganizationRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(LiveRoomOrganizationQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, LiveRoomOrganization.class);
        return liveRoomOrganizationRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(LiveRoomOrganizationCommand command) {
        var liveRoomOrganization = liveRoomOrganizationMapper.convert(command);
        liveRoomOrganizationRepository.create(liveRoomOrganization);
                        return liveRoomOrganization.getId();
                        }

    @Override
    public void update(BigInteger id, LiveRoomOrganizationCommand command) {
        var liveRoomOrganization = liveRoomOrganizationMapper.convert(command);
        liveRoomOrganization.setId(id);
        validateModifyingResult(liveRoomOrganizationRepository.update(liveRoomOrganization));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(liveRoomOrganizationRepository.deleteById(id));
    }
}