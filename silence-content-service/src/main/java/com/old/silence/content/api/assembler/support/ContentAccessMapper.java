package com.old.silence.content.api.assembler.support;

import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * @author moryzang
 */
public interface ContentAccessMapper<S extends ContentCommand, T extends ContentAccessor> extends Converter<S, T> {

    default List<ContentContentTag> toContentContentTags(Collection<BigInteger> tagIds) {
        return CollectionUtils.transformToList(tagIds, tagId -> {
            var contentContentTag = new ContentContentTag();
            contentContentTag.setTagId(tagId);
            return contentContentTag;
        });
    }



}
