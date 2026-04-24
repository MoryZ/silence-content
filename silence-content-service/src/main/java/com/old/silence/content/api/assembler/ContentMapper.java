package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */

@Mapper(uses = MapStructSpringConfig.class)
public interface ContentMapper extends Converter<ContentCommand, Content> {

    @Override
    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content convert(ContentCommand command);

    default List<ContentContentTag> toContentContentTags(List<BigInteger> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return tagIds.stream()
                .map(tagId -> {
                    ContentContentTag contentContentTag = new ContentContentTag();
                    contentContentTag.setTagId(tagId);
                    return contentContentTag;
                })
                .toList();
    }

}