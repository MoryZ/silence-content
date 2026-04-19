package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.RewardItemDomainCommand;
import com.old.silence.content.domain.model.RewardItem;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


/**
 * @author yangwenchang
 * @date 2023-02-01
 * @description
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface RewardItemMapper extends Converter<RewardItemDomainCommand, RewardItem> {
}
