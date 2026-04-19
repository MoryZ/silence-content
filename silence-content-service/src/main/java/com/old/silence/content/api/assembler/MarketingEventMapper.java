package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.MarketingEventDomainCommand;
import com.old.silence.content.api.dto.MarketingRuleDomainCommand;
import com.old.silence.content.domain.model.MarketingEvent;
import com.old.silence.content.domain.model.MarketingRule;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


/**
 * @author yangwenchang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface MarketingEventMapper extends Converter<MarketingEventDomainCommand, MarketingEvent> {
    MarketingRule toMarketingRule(MarketingRuleDomainCommand command);
}
