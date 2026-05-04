package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.data.jdbc.repository.JdbcRepository;
import com.old.silence.data.jdbc.repository.query.Modifying;
import com.old.silence.data.jdbc.repository.query.Query;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PoetryUserStudySetting数据访问接口
 */
public interface PoetryUserStudySettingDao extends JdbcRepository<PoetryUserStudySetting, BigInteger> {


    <T> Optional<T> findBySubCategoryIdAndGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType);

    <T> Optional<T> findBySubCategoryIdAndUserId(BigInteger subCategoryId, BigInteger userId, Class<T> projectionType);

    @Query("UPDATE poetry_user_study_setting SET status = :expectedStatus WHERE id = :id AND status= :status ")
    @Modifying
    int updateStatusByIdAndStatus(StudyStatus status, BigInteger id, StudyStatus expectedStatus);
}