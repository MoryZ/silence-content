package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryGrade;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;


/**
* PoetryGrade数据访问接口
*/
public interface PoetryGradeDao extends JdbcRepository<PoetryGrade, BigInteger> {

}