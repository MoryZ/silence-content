package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.model.ContentTag;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface ContentTagDao extends JdbcRepository<ContentTag, BigInteger> {
    <T> List<T> findByTypeAndEnabled(ContentTagType type, Boolean enabled, Class<T> projectionType);

    <T> List<T> findByEnabledAndCodeIn(Boolean enabled, Collection<String> codes, Class<T> projectionType);
}
