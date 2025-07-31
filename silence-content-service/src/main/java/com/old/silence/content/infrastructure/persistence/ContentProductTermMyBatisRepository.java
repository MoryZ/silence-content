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
import com.old.silence.content.domain.model.ContentProductTerm;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.domain.repository.ContentProductTermRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentProductTermDao;


@Repository
public class ContentProductTermMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentProductTerm> 
        implements ContentProductTermRepository {
    private final ContentProductTermDao contentProductTermDao;

    public ContentProductTermMyBatisRepository(ContentProductTermDao contentProductTermDao,
                                               ContentRepository contentRepository) {
        super(contentRepository);
        this.contentProductTermDao = contentProductTermDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentProductTermDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentProductTermDao.findByCriteria(criteria, pageable, projectionType);
    }
 
    @Override
    protected int createSpecificContent(ContentProductTerm contentProductTerm) {
        return contentProductTermDao.insert(contentProductTerm);
    }

    @Override
    protected int updateSpecificContent(ContentProductTerm contentProductTerm) {
        return contentProductTermDao.update(contentProductTerm);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return contentProductTermDao.deleteById(id);
    }

    @Override
    public Collection<ContentType> getSupportedTypes() {
        return List.of(ContentType.PRODUCT_TERM);
    }

 
}
