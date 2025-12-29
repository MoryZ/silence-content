package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.ContentContentTag;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface ContentContentTagRepository {

    int bulkCreate(List<ContentContentTag> contentContentTags);

    int deleteByContentId(BigInteger contentId);
}
