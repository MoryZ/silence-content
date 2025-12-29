package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.ContentBook;
import com.old.silence.content.domain.repository.ContentBookRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentBookDao;

import java.math.BigInteger;
import java.util.Optional;


@Repository
public class ContentBookMyBatisRepository implements ContentBookRepository {

    private final ContentBookDao contentBookDao;

    public ContentBookMyBatisRepository(ContentBookDao contentBookDao) {
        this.contentBookDao = contentBookDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentBookDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentBookDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(ContentBook contentBook) {
        return contentBookDao.insert(contentBook);
    }

    @Override
    public int update(ContentBook contentBook) {
        return contentBookDao.update(contentBook);
    }

    @Override
    public int deleteById(BigInteger id) {
        return contentBookDao.deleteById(id);
    }
}
