package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryDailyStudyPlan数据访问接口
*/
public interface PoetryDailyStudyPlanDao extends JdbcRepository<PoetryDailyStudyPlan, BigInteger> {

}