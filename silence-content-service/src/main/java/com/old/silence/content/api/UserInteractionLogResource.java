package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.UserInteractionLogMapper;
import com.old.silence.content.api.dto.UserInteractionLogCommand;
import com.old.silence.content.api.dto.UserInteractionLogQuery;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.poetry.UserInteractionLog;
import com.old.silence.content.domain.repository.poetry.UserInteractionLogRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUserFavorite资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class UserInteractionLogResource implements UserInteractionLogService {
    private final UserInteractionLogRepository userInteractionLogRepository;
    private final UserInteractionLogMapper userInteractionLogMapper;

    public UserInteractionLogResource(UserInteractionLogRepository userInteractionLogRepository,
                                      UserInteractionLogMapper userInteractionLogMapper) {
        this.userInteractionLogRepository = userInteractionLogRepository;
        this.userInteractionLogMapper = userInteractionLogMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return userInteractionLogRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(UserInteractionLogQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, UserInteractionLog.class);
        return userInteractionLogRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<StatsVo> findFavoriteTop5() {
        var favoriteTop5 = userInteractionLogRepository.findFavoriteTop5();
        return CollectionUtils.transformToList(favoriteTop5, numberStatsVo ->
                new StatsVo(numberStatsVo.getUserId(), numberStatsVo.getIndicatorAccumulation()));
    }

    @Override
    public BigInteger create(UserInteractionLogCommand command) {
        var poetryUserFavorite = userInteractionLogMapper.convert(command);
        userInteractionLogRepository.create(poetryUserFavorite);
        return poetryUserFavorite.getId();
    }

    @Override
    public void update(BigInteger id, UserInteractionLogCommand command) {
        var poetryUserFavorite = userInteractionLogMapper.convert(command);
        poetryUserFavorite.setId(id);
        validateModifyingResult(userInteractionLogRepository.update(poetryUserFavorite));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(userInteractionLogRepository.deleteById(id));
    }
}