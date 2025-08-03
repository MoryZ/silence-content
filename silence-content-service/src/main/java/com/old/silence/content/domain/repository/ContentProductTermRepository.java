package com.old.silence.content.domain.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.model.ContentProductTerm;

/**
 * @author moryzang
 */
public interface ContentProductTermRepository extends ContentAccessRepository<ContentProductTerm> {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);
}
