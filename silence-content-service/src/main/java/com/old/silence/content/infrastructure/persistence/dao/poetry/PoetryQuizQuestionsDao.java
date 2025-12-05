package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryQuizQuestions;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryQuizQuestions数据访问接口
*/
public interface PoetryQuizQuestionsDao extends JdbcRepository<PoetryQuizQuestions, BigInteger> {

}