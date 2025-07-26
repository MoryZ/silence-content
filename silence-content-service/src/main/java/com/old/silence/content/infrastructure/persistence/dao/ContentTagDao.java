package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.model.ContentTag;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author MurrayZhang
 * @Description
 */
public interface ContentTagDao extends JdbcRepository<ContentTag, BigInteger> {
    <T> List<T> findByTypeAndEnabled(ContentTagType type, Boolean enabled, Class<T> projectionType);
}
