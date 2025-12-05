package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.enums.PromptFormatType;
import com.old.silence.content.domain.model.poetry.PromptCommonFormat;

import java.math.BigInteger;
import java.util.Optional;

/**
* PromptCommonFormat仓储接口
*/
public interface PromptCommonFormatRepository {

    <T> Optional<T> findByFormatType(PromptFormatType formatType, Class<T> projectionType);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PromptCommonFormat promptCommonFormat);

    int update(PromptCommonFormat promptCommonFormat);

    int deleteById(BigInteger id);
}