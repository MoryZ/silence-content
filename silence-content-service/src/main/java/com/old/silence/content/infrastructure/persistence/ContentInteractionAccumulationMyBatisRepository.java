package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.model.ContentInteractionAccumulation;
import com.old.silence.content.domain.repository.ContentInteractionAccumulationRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentContentTagDao;
import com.old.silence.content.infrastructure.persistence.dao.ContentInteractionAccumulationDao;

import java.util.List;


@Repository
public class ContentInteractionAccumulationMyBatisRepository implements ContentInteractionAccumulationRepository {
    private final ContentInteractionAccumulationDao contentInteractionAccumulationDao;


    public ContentInteractionAccumulationMyBatisRepository(ContentInteractionAccumulationDao contentInteractionAccumulationDao) {
        this.contentInteractionAccumulationDao = contentInteractionAccumulationDao;
    }

    @Override
    public int bulkCreate(List<ContentInteractionAccumulation> contentInteractionAccumulations) {
        return contentInteractionAccumulationDao.insertAll(contentInteractionAccumulations);
    }
}
