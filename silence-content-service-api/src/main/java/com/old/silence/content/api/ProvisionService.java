package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.vo.ProvisionView;
import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;
import com.old.silence.web.data.ProjectedPayloadType;

import java.util.List;

/**
 * 条款服务接口
 */
interface ProvisionService {

    @GetMapping(value = "/provisions")
    <T> List<T> findByScenarioCodeAndChannelCode(@RequestParam ProvisionScenario scenarioCode, @RequestParam ProvisionChannel channelCode,
                                                 @ProjectedPayloadType(ProvisionView.class) Class<T> projectionType);
}