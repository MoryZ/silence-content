package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PoetryLearningContent;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PoetryLearningContent仓储接口
*/
public interface PoetryLearningContentRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType);

    <T> List<T> findByGradeIdAndSubCategoryId(BigInteger gradeId, BigInteger subCategoryId, Class<T> projectionType);

    long countByCriteria(Criteria criteria);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryLearningContent poetryLearningContent);

    int bulkCreate(List<PoetryLearningContent> poetryLearningContents);


    int update(PoetryLearningContent poetryLearningContent);

    int deleteById(BigInteger id);

}