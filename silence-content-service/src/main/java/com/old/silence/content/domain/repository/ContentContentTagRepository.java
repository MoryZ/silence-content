package com.old.silence.content.domain.repository;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.domain.model.ContentContentTag;

/**
 * @author moryzang
 */
public interface ContentContentTagRepository {

    int bulkCreate(List<ContentContentTag> contentContentTags);

    int deleteByContentId(BigInteger contentId);
}
