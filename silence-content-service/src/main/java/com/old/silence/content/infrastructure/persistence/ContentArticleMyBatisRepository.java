package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.ContentArticle;
import com.old.silence.content.domain.repository.ContentArticleRepository;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentArticleDao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public class ContentArticleMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentArticle>
        implements ContentArticleRepository {
    private final ContentArticleDao contentArticleDao;

    public ContentArticleMyBatisRepository(ContentArticleDao contentArticleDao,
                                           ContentRepository contentRepository) {
        super(contentRepository);
        this.contentArticleDao = contentArticleDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentArticleDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentArticleDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    protected int createSpecificContent(ContentArticle contentArticle) {
        return contentArticleDao.insert(contentArticle);
    }

    @Override
    protected int updateSpecificContent(ContentArticle contentArticle) {
        return contentArticleDao.update(contentArticle);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return contentArticleDao.deleteById(id);
    }

    @Override
    public Collection<ContentType> getSupportedTypes() {
        return List.of(ContentType.ARTICLE);
    }


}
