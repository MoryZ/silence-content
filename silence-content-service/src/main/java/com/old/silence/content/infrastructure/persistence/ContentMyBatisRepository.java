package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentDao;


@Repository
public class ContentMyBatisRepository implements ContentRepository {
    private final ContentDao contentDao;

    public ContentMyBatisRepository(ContentDao contentDao) {
        this.contentDao = contentDao;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentDao.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByIds(Collection<BigInteger> ids, Class<T> projectionType) {
        return contentDao.findAllById(ids, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(Content content) {
        return contentDao.insert(content);
    }

    @Override
    public int update(Content content) {
        return contentDao.update(content);
    }

    @Override
    public int updateStatus(BigInteger id, ContentStatus status) {
        return contentDao.updateStatus(status, id);
    }

    @Override
    public int deleteById(BigInteger id) {
        return contentDao.deleteById(id);
    }
}
