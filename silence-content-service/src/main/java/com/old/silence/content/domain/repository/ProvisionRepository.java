package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;

import java.util.List;

/**
 * Provision仓储接口
 */
public interface ProvisionRepository {

    <T> List<T> findByScenarioCodeAndChannelCode(ProvisionScenario scenarioCode, ProvisionChannel channelCode, Class<T> projectionType);

}