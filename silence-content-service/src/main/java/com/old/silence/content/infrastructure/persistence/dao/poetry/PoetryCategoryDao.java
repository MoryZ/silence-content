package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryCategory;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryCategory数据访问接口
*/
public interface PoetryCategoryDao extends JdbcRepository<PoetryCategory, BigInteger> {

    <T> List<T> findByParentId(BigInteger parentId, Class<T> projectionType);
}