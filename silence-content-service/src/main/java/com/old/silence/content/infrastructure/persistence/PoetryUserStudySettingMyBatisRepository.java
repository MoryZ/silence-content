package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.content.domain.repository.poetry.PoetryUserStudySettingRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryUserStudySettingDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PoetryUserStudySetting仓储实现
 */
@Repository
public class PoetryUserStudySettingMyBatisRepository implements PoetryUserStudySettingRepository {


    private final PoetryUserStudySettingDao poetryUserStudySettingDao;


    public PoetryUserStudySettingMyBatisRepository(PoetryUserStudySettingDao poetryUserStudySettingDao) {
        this.poetryUserStudySettingDao = poetryUserStudySettingDao;
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType) {
        return poetryUserStudySettingDao.findBySubCategoryIdAndGradeIdAndUserId(subCategoryId, gradeId, userId, projectionType);
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdAndUserId(BigInteger subCategoryId, BigInteger userId, Class<T> projectionType) {
        return poetryUserStudySettingDao.findBySubCategoryIdAndUserId(subCategoryId, userId, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserStudySettingDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryUserStudySetting poetryUserStudySetting) {
        return poetryUserStudySettingDao.insert(poetryUserStudySetting);
    }


    @Override
    public int update(PoetryUserStudySetting poetryUserStudySetting) {
        return poetryUserStudySettingDao.update(poetryUserStudySetting);
    }

    @Override
    public int updateStatusByIdAndStatus(StudyStatus status, BigInteger id, StudyStatus expectedStatus) {
        return poetryUserStudySettingDao.updateStatusByIdAndStatus(status, id, expectedStatus);
    }


    @Override
    public int deleteById(BigInteger id) {
        return poetryUserStudySettingDao.deleteById(id);
    }
}