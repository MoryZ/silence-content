package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryCategory;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryCategory数据访问接口
*/
public interface PoetryCategoryDao extends JdbcRepository<PoetryCategory, BigInteger> {

}