package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUser;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;


/**
* PoetryUser数据访问接口
*/
public interface PoetryUserDao extends JdbcRepository<PoetryUser, BigInteger> {

    <T> Optional<T> findByOpenid(String openid, Class<T> projectionType);

    int updatePhone(String phone, BigInteger id);
}