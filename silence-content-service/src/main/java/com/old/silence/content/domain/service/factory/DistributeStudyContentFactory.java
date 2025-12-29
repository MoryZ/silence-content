package com.old.silence.content.domain.service.factory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.DistributeStudyContentType;
import com.old.silence.content.infrastructure.persistence.DistributeStudyContentStrategy;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Component
public class DistributeStudyContentFactory {

    private final Map<DistributeStudyContentType, DistributeStudyContentStrategy> distributeStudyContentTypeDistributeStudyContentStrategyMap;

    public DistributeStudyContentFactory(ObjectProvider<DistributeStudyContentStrategy> repositoryObjectProvider) {
        distributeStudyContentTypeDistributeStudyContentStrategyMap = repositoryObjectProvider.stream()
                .collect(Collectors.toMap(DistributeStudyContentStrategy::getDistributeStudyContentType, Function.identity()));
    }

    public DistributeStudyContentStrategy getDistributeStudyContentStrategy(DistributeStudyContentType distributeStudyContentType) {
        return distributeStudyContentTypeDistributeStudyContentStrategyMap.get(distributeStudyContentType);
    }
}
