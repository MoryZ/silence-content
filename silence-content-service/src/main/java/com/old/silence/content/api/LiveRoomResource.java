package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.LiveRoomMapper;
import com.old.silence.content.api.dto.LiveRoomCommand;
import com.old.silence.content.api.dto.LiveRoomQuery;
import com.old.silence.content.domain.model.LiveRoom;
import com.old.silence.content.domain.repository.LiveRoomRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* LiveRoom资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveRoomResource implements LiveRoomService {
    private final LiveRoomRepository liveRoomRepository;
    private final LiveRoomMapper liveRoomMapper;

    public LiveRoomResource(LiveRoomRepository liveRoomRepository,
                                LiveRoomMapper liveRoomMapper) {
        this.liveRoomRepository = liveRoomRepository;
        this.liveRoomMapper = liveRoomMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveRoomRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(LiveRoomQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, LiveRoom.class);
        return liveRoomRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(LiveRoomCommand command) {
        var liveRoom = liveRoomMapper.convert(command);
        liveRoomRepository.create(liveRoom);
                        return liveRoom.getId();
                        }

    @Override
    public void update(BigInteger id, LiveRoomCommand command) {
        var liveRoom = liveRoomMapper.convert(command);
        liveRoom.setId(id);
        validateModifyingResult(liveRoomRepository.update(liveRoom));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(liveRoomRepository.deleteById(id));
    }
}