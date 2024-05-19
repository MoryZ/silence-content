package com.old.silence.content.infrastructure.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentTagCategoryMapper;


@Repository
public class ContentMyBatisRepository implements ContentRepository {
    private final ContentTagCategoryMapper contentTagCategoryMapper;

    public ContentMyBatisRepository(ContentTagCategoryMapper contentTagCategoryMapper) {
        this.contentTagCategoryMapper = contentTagCategoryMapper;
    }

    @Override
    public Optional<Content> findById(Long id) {
        return Optional.of(contentTagCategoryMapper.selectById(id));
    }

    @Override
    public IPage<Content> findByCriteria(IPage<Content> pageable,
                                         Content content) {
        LambdaQueryWrapper<Content> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(content.getName()), Content::getName, content.getName());
        return contentTagCategoryMapper.selectPage(pageable, wrapper);
    }

    @Override
    public int create(Content content) {
        return 0;
    }

    @Override
    public int update(Content content) {
        return 0;
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
