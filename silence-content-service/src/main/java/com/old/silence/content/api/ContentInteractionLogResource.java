package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.ContentInteractionLogMapper;
import com.old.silence.content.api.dto.ContentInteractionLogCommand;
import com.old.silence.content.api.dto.ContentInteractionLogQuery;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.enums.ContentInteractionMode;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.model.ContentInteractionLog;
import com.old.silence.content.domain.model.ContentUserInteractionStatus;
import com.old.silence.content.domain.repository.ContentInteractionAccumulationRepository;
import com.old.silence.content.domain.repository.ContentInteractionLogRepository;
import com.old.silence.content.domain.repository.ContentUserInteractionStatusRepository;
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
public class ContentInteractionLogResource implements ContentInteractionLogService {
    private final ContentInteractionLogRepository contentInteractionLogRepository;
    private final ContentInteractionLogMapper contentInteractionLogMapper;
    private final ContentUserInteractionStatusRepository contentUserInteractionStatusRepository;
    private final ContentInteractionAccumulationRepository contentInteractionAccumulationRepository;

    public ContentInteractionLogResource(ContentInteractionLogRepository contentInteractionLogRepository,
                                         ContentInteractionLogMapper contentInteractionLogMapper,
                                         ContentUserInteractionStatusRepository contentUserInteractionStatusRepository,
                                         ContentInteractionAccumulationRepository contentInteractionAccumulationRepository) {
        this.contentInteractionLogRepository = contentInteractionLogRepository;
        this.contentInteractionLogMapper = contentInteractionLogMapper;
        this.contentUserInteractionStatusRepository = contentUserInteractionStatusRepository;
        this.contentInteractionAccumulationRepository = contentInteractionAccumulationRepository;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentInteractionLogRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(ContentInteractionLogQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, ContentInteractionLog.class);
        return contentInteractionLogRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<StatsVo> findFavoriteTop5() {
        var favoriteTop5 = contentInteractionLogRepository.findFavoriteTop5();
        return CollectionUtils.transformToList(favoriteTop5, numberStatsVo ->
                new StatsVo(numberStatsVo.getUserId(), numberStatsVo.getIndicatorAccumulation()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigInteger create(ContentInteractionLogCommand command) {
        var contentInteractionLog = contentInteractionLogMapper.convert(command);

        switch (command.getInteractionType().toMode()) {
            case STATEFUL -> handleStatefulInteraction(command);
            case STATELESS -> handleStatelessInteraction(command);
        }

        contentInteractionLogRepository.create(contentInteractionLog);
        return contentInteractionLog.getId();
    }

    @Override
    public void update(BigInteger id, ContentInteractionLogCommand command) {
        var poetryUserFavorite = contentInteractionLogMapper.convert(command);
        poetryUserFavorite.setId(id);
        validateModifyingResult(contentInteractionLogRepository.update(poetryUserFavorite));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(contentInteractionLogRepository.deleteById(id));
    }

    private void persistState(ContentInteractionLogCommand command, InteractionType interactionType) {
        var status = new ContentUserInteractionStatus();
        status.setUserId(command.getUserId());
        status.setResourceId(command.getResourceId());
        status.setResourceType(command.getResourceType());
        status.setInteractionType(interactionType);
        validateModifyingResult(contentUserInteractionStatusRepository.create(status));
    }

    private void handleStatefulInteraction(ContentInteractionLogCommand command) {
        var stateType = command.getInteractionType().normalizeStateType();
        if (command.getInteractionType().isCancelAction()) {
            validateModifyingResult(contentUserInteractionStatusRepository.deleteByUserIdAndResourceIdAndResourceTypeAndInteractionType(
                    command.getUserId(), command.getResourceId(), command.getResourceType(), stateType));
            contentInteractionAccumulationRepository.increaseAccumulationByResourceIdAndResourceTypeAndType(
                    BigInteger.ONE.negate(), command.getResourceId(), command.getResourceType(), stateType);
            return;
        }

        persistState(command, stateType);
        contentInteractionAccumulationRepository.upsertAccumulationByResourceIdAndResourceTypeAndType(
                command.getResourceId(), command.getResourceType(), stateType, BigInteger.ONE);
    }

    private void handleStatelessInteraction(ContentInteractionLogCommand command) {
        contentInteractionAccumulationRepository.upsertAccumulationByResourceIdAndResourceTypeAndType(
                command.getResourceId(), command.getResourceType(), command.getInteractionType(), BigInteger.ONE);
    }
}