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
import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.repository.ContentLiveRepository;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentLiveDao;


@Repository
public class ContentLiveMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentLive>
        implements ContentLiveRepository {
    private final ContentLiveDao contentLiveDao;

    public ContentLiveMyBatisRepository(ContentLiveDao contentLiveDao,
                                        ContentRepository contentRepository) {
        super(contentRepository);
        this.contentLiveDao = contentLiveDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentLiveDao.findById(id, projectionType);
    }


    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentLiveDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    protected int createSpecificContent(ContentLive contentLive) {
        return contentLiveDao.insert(contentLive);
    }

    @Override
    protected int updateSpecificContent(ContentLive contentLive) {
        return contentLiveDao.update(contentLive);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return contentLiveDao.deleteById(id);
    }

    @Override
    public Collection<ContentType> getSupportedTypes() {
        return List.of(ContentType.LIVE);
    }


}
