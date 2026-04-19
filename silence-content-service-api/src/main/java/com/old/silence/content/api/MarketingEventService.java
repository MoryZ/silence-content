package com.old.silence.content.api;

import org.springframework.web.bind.annotation.PathVariable;
import com.old.silence.content.api.vo.MarketingEventDomainView;
import com.old.silence.web.bind.annotation.GetJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

interface MarketingEventService {

    @GetJsonMapping("/marketingEvents/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
            @ProjectedPayloadType(MarketingEventDomainView.class) Class<T> projectionType);

}
