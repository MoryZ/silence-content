package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;
import com.old.silence.content.domain.model.Provision;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Provision数据访问接口
 */
public interface ProvisionDao extends JdbcRepository<Provision, BigInteger> {

    <T> List<T> findByScenarioCodeAndChannelCode(ProvisionScenario scenarioCode, ProvisionChannel channelCode, Class<T> projectionType);
}