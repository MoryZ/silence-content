package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserStudySetting;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserStudySetting数据访问接口
*/
public interface PoetryUserStudySettingDao extends JdbcRepository<PoetryUserStudySetting, BigInteger> {

}