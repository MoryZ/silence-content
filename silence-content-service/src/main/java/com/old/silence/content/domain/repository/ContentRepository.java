package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.Content;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface ContentRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findByIds(Collection<BigInteger> id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(Content content);

    int update(Content content);

    int updateStatus(BigInteger id, ContentStatus status);

    int updateStickyTop(BigInteger id, boolean stickyTopStatus);

    int deleteById(BigInteger id);

}
