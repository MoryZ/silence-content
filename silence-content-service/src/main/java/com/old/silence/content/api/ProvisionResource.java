package com.old.silence.content.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;
import com.old.silence.content.domain.repository.ProvisionRepository;

import java.util.List;

/**
 * Provision资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class ProvisionResource implements ProvisionService {

    private final ProvisionRepository provisionRepository;

    public ProvisionResource(ProvisionRepository provisionRepository) {
        this.provisionRepository = provisionRepository;
    }

    @Override
    public <T> List<T> findByScenarioCodeAndChannelCode(ProvisionScenario scenarioCode, ProvisionChannel channelCode, Class<T> projectionType) {
        return provisionRepository.findByScenarioCodeAndChannelCode(scenarioCode, channelCode, projectionType);
    }
}