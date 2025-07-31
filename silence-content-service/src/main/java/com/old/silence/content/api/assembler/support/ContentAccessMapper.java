package com.old.silence.content.api.assembler.support;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.enums.ContentBusinessStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.model.ContentContentTagId;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
public interface ContentAccessMapper<S extends ContentCommand, T extends ContentAccessor> extends Converter<S, T> {

    default List<ContentContentTag> toContentContentTags(Collection<BigInteger> tagIds) {
        return CollectionUtils.transformToList(tagIds, tagId -> {
            var id = new ContentContentTagId();
            id.setTagId(tagId);
            var contentContentTag = new ContentContentTag();
            contentContentTag.setId(id);
            return contentContentTag;
        });
    }

    default ContentBusinessStatus toContentBusinessStatus(ContentType contentType) {
        return ContentBusinessStatus.convertToContentBusinessStatus(contentType).orElse(null);
    }


}
