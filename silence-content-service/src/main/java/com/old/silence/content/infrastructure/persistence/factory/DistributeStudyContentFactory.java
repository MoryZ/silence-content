package com.old.silence.content.infrastructure.persistence.factory;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.DistributeStudyContentType;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.content.domain.repository.ContentAccessRepository;
import com.old.silence.content.infrastructure.persistence.DistributeStudyContentStrategy;

/**
 * @author moryzang
 */
@Repository
public class DistributeStudyContentFactory {

    private final Map<DistributeStudyContentType, DistributeStudyContentStrategy> distributeStudyContentTypeDistributeStudyContentStrategyMap;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public DistributeStudyContentFactory(ObjectProvider<DistributeStudyContentStrategy> repositoryObjectProvider) {
        distributeStudyContentTypeDistributeStudyContentStrategyMap = repositoryObjectProvider.stream().collect(Collectors.toMap(DistributeStudyContentStrategy::getDistributeStudyContentType, Function.identity()));
    }

    public DistributeStudyContentStrategy getDistributeStudyContentStrategy(DistributeStudyContentType distributeStudyContentType) {
        return distributeStudyContentTypeDistributeStudyContentStrategyMap.get(distributeStudyContentType);
    }
}
