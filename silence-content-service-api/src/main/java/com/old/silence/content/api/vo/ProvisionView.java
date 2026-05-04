package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.ProvisionChannel;
import com.old.silence.content.domain.enums.ProvisionScenario;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ProvisionView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    ProvisionScenario getScenarioCode();

    ProvisionChannel getChannelCode();

    String getName();

    String getUrl();
}
