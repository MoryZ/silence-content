package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.support.ContentCommon;
import com.old.silence.content.domain.repository.ContentCommonRepository;
import com.old.silence.content.domain.repository.ContentRepository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;


@Repository
public class ContentCommonMyBatisRepository extends AbstractContentAccessorMyBatisRepository<ContentCommon>
        implements ContentCommonRepository {

    public ContentCommonMyBatisRepository(ContentRepository contentRepository) {
        super(contentRepository);
    }

    @Override
    protected int deleteSpecificContent(BigInteger id) {
        return 1;
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
        return List.of(ContentType.PDF, ContentType.VISUALIZATION_PAGE, ContentType.QUESTIONNAIRE,
                ContentType.POSTER);
    }


}
