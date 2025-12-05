package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryUserLearningStats;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserLearningStats数据访问接口
*/
public interface PoetryUserLearningStatsDao extends JdbcRepository<PoetryUserLearningStats, BigInteger> {

}