package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PoetryUserStudySetting数据访问接口
 */
public interface PoetryUserStudySettingDao extends JdbcRepository<PoetryUserStudySetting, BigInteger> {


    <T> Optional<T> findBySubCategoryIdAndGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType);
}