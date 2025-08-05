package com.old.silence.content.domain.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.model.ContentTag;


public interface ContentTagRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findByIds(Collection<BigInteger> ids, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(ContentTag contentTag);

    int update(ContentTag contentTag);

    int deleteById(BigInteger id);

    <T> List<T> findByTypeAndEnabled(ContentTagType type, Boolean enabled, Class<T> projectionType);

    <T> List<T> findByEnabledAndCodesIn(Boolean enabled, Collection<String> codes, Class<T> projectionType);
}
