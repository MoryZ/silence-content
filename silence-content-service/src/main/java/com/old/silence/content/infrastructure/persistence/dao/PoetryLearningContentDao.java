package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryLearningContent;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryLearningContent数据访问接口
*/
public interface PoetryLearningContentDao extends JdbcRepository<PoetryLearningContent, BigInteger> {

}