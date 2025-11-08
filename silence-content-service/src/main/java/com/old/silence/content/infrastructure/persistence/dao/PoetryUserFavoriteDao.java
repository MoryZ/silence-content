package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserFavorite;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserFavorite数据访问接口
*/
public interface PoetryUserFavoriteDao extends JdbcRepository<PoetryUserFavorite, BigInteger> {


}