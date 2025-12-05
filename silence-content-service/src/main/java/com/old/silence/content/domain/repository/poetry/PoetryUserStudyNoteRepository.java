package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.poetry.PoetryUserStudyNote;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryUserStudyNote仓储接口
*/
public interface PoetryUserStudyNoteRepository {

    <T> List<T> findByContentIdAndUserId(BigInteger contentId, BigInteger userId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryUserStudyNote poetryUserStudyNote);

    int update(PoetryUserStudyNote poetryUserStudyNote);

    int deleteById(BigInteger id);
}