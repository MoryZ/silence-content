package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.repository.ContentContentTagRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentContentTagDao;

import java.math.BigInteger;
import java.util.List;


@Repository
public class ContentContentTagMyBatisRepository implements ContentContentTagRepository {
    private final ContentContentTagDao contentContentTagDao;


    public ContentContentTagMyBatisRepository(ContentContentTagDao contentContentTagDao) {
        this.contentContentTagDao = contentContentTagDao;
    }

    @Override
    public int bulkCreate(List<ContentContentTag> contentContentTags) {
        return contentContentTagDao.insertAll(contentContentTags);
    }

    @Override
    public int deleteByContentId(BigInteger contentId) {
        return contentContentTagDao.deleteByContentId(contentId);
    }
}
