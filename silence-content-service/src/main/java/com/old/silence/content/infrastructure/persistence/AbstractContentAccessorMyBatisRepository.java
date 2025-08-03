package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.content.domain.repository.ContentAccessRepository;
import com.old.silence.content.domain.repository.ContentRepository;

/**
 * @author moryzang
 */
abstract class AbstractContentAccessorMyBatisRepository<C extends ContentAccessor> implements ContentAccessRepository<C> {

    protected final ContentRepository contentRepository;

    AbstractContentAccessorMyBatisRepository(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public <T> List<T> findByIds(Collection<BigInteger> ids, Class<T> projectionType) {
        return contentRepository.findByIds(ids, projectionType);
    }

    @Override
    public int create(C contentAccessor) {
        var content = contentAccessor.getContent();
        contentRepository.create(content);
        contentAccessor.setId(content.getId());
        return createSpecificContent(contentAccessor);
    }

    @Override
    public int update(C contentAccessor) {
        var content = contentAccessor.getContent();
        contentRepository.update(content);
        return updateSpecificContent(contentAccessor);
    }

    @Override
    public int updateStatus(BigInteger id, ContentStatus status) {
        return contentRepository.updateStatus(id, status);
    }

    @Override
    public int deleteById(BigInteger id) {
        contentRepository.deleteById(id);
        return deleteSpecificContent(id);
    }

    protected abstract int createSpecificContent(C content);

    protected abstract int updateSpecificContent(C content);

    protected abstract int deleteSpecificContent(BigInteger id);

}
