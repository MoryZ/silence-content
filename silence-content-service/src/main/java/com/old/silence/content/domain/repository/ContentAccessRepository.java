package com.old.silence.content.domain.repository;

import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.support.ContentAccessor;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * @author moryzang
 */
public interface ContentAccessRepository<C extends ContentAccessor> {

    <T> List<T> findByIds(Collection<BigInteger> ids, Class<T> projectionType);

    @Transactional
    int create(C contentAccessor);

    @Transactional
    int update(C contentAccessor);

    @Transactional
    int updateStatus(BigInteger id, ContentStatus status);

    @Transactional
    int deleteById(BigInteger id);

    Collection<ContentType> getSupportedTypes();
}
