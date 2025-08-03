package com.old.silence.content.infrastructure.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.repository.ContentContentTagRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentContentTagDao;


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
}
