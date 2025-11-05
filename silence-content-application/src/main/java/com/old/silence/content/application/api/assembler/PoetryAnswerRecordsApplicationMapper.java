package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;
import com.old.silence.content.application.api.dto.PoetryAnswerRecordsApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
* PoetryAnswerRecords映射器
*/
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryAnswerRecordsApplicationMapper extends Converter<PoetryAnswerRecordsApplicationCommand, PoetryAnswerRecordsCommand>{


}