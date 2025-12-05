package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.poetry.PoetryUser;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryUser仓储接口
*/
public interface PoetryUserRepository {

    <T> Optional<T> findByOpenid(String openid, Class<T> projectionType);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryUser poetryUser);

    int update(PoetryUser poetryUser);

    int updatePhone(String phone, BigInteger id);

    int deleteById(BigInteger id);

}