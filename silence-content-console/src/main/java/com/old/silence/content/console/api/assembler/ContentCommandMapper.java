package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentCommonCommand;
import com.old.silence.content.api.dto.ContentVideoCommand;

import com.old.silence.content.console.dto.ContentArticleConsoleCommand;
import com.old.silence.content.console.dto.ContentCommonConsoleCommand;
import com.old.silence.content.console.dto.ContentConsoleCommand;
import com.old.silence.content.console.dto.ContentVideoConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentCommandMapper extends Converter<ContentConsoleCommand, ContentCommand> {


    @Override
    @Mapping(target = "status", defaultValue = "STAGED")
    @SubclassMapping(source = ContentArticleConsoleCommand.class, target = ContentArticleCommand.class)
    @SubclassMapping(source = ContentVideoConsoleCommand.class, target = ContentVideoCommand.class)
    @SubclassMapping(source = ContentCommonConsoleCommand.class, target = ContentCommonCommand.class)
    ContentCommand convert(ContentConsoleCommand source);
}
