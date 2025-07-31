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
import com.old.silence.content.domain.model.support.ContentCommon;
import com.old.silence.content.domain.repository.ContentCommonRepository;
import com.old.silence.content.domain.repository.ContentProductTermRepository;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentProductTermDao;


@Repository
public class ContentCommonMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentCommon>
        implements ContentCommonRepository {

    public ContentCommonMyBatisRepository(ContentRepository contentRepository) {
        super(contentRepository);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return 0;
    }

    @Override
    protected int updateSpecificContent(ContentCommon content) {
        return 0;
    }

    @Override
    protected int createSpecificContent(ContentCommon content) {
        return 0;
    }

    @Override
    public Collection<ContentType> getSupportedTypes() {
        return List.of(ContentType.PDF, ContentType.VISUALIZATION_PAGE, ContentType.QUESTIONNAIRE, ContentType.LOW_CODE,
                ContentType.POSTER, ContentType.COMMON_PAGE);
    }

 
}
