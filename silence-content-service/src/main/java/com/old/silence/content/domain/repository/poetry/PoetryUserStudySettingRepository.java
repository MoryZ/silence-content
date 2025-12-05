package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryUserStudySetting仓储接口
*/
public interface PoetryUserStudySettingRepository {

    <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryUserStudySetting poetryUserStudySetting);

    int update(PoetryUserStudySetting poetryUserStudySetting);

    int deleteById(BigInteger id);
}