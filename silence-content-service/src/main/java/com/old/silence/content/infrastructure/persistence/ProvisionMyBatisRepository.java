package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;
import com.old.silence.content.domain.repository.ProvisionRepository;
import com.old.silence.content.infrastructure.persistence.dao.ProvisionDao;

import java.util.List;

/**
 * Provision仓储实现
 */
@Repository
public class ProvisionMyBatisRepository implements ProvisionRepository {
    private final ProvisionDao provisionDao;

    public ProvisionMyBatisRepository(ProvisionDao provisionDao) {
        this.provisionDao = provisionDao;
    }

    @Override
    public <T> List<T> findByScenarioCodeAndChannelCode(ProvisionScenario scenarioCode, ProvisionChannel channelCode, Class<T> projectionType) {
        return provisionDao.findByScenarioCodeAndChannelCode(scenarioCode, channelCode, projectionType);
    }
}