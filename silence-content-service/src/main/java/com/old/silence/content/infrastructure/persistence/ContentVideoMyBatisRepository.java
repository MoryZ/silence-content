package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.ContentVideo;
import com.old.silence.content.domain.repository.ContentVideoRepository;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentVideoDao;


@Repository
public class ContentVideoMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentVideo> 
        implements ContentVideoRepository {
    private final ContentVideoDao contentVideoDao;

    public ContentVideoMyBatisRepository(ContentVideoDao contentVideoDao,
                                         ContentRepository contentRepository) {
        super(contentRepository);
        this.contentVideoDao = contentVideoDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentVideoDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentVideoDao.findByCriteria(criteria, pageable, projectionType);
    }
 
    @Override
    protected int createSpecificContent(ContentVideo contentVideo) {
        return contentVideoDao.insert(contentVideo);
    }

    @Override
    protected int updateSpecificContent(ContentVideo contentVideo) {
        return contentVideoDao.update(contentVideo);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return contentVideoDao.deleteById(id);
    }

    @Override
    public Collection<ContentType> getSupportedTypes() {
        return List.of(ContentType.VIDEO);
    }

 
}
