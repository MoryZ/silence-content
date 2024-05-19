package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.model.ContentTag;
import com.old.silence.content.domain.repository.ContentTagRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentTagDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Repository
public class ContentTagMyBatisRepository implements ContentTagRepository {
    private final ContentTagDao contentTagDao;

    public ContentTagMyBatisRepository(ContentTagDao contentTagDao) {
        this.contentTagDao = contentTagDao;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return Optional.empty();
    }

    @Override
    public <T> List<T> findByTypeAndEnabled(ContentTagType type, Boolean enabled, Class<T> projectionType) {
        return contentTagDao.findByTypeAndEnabled(type, enabled, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentTagDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(ContentTag contentTag) {
        return contentTagDao.insert(contentTag);
    }

    @Override
    public int update(ContentTag contentTag) {
        return contentTagDao.update(contentTag);
    }

    @Override
    public int deleteById(BigInteger id) {
        return contentTagDao.deleteById(id);
    }
}
