package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.Food;
import com.old.silence.content.domain.model.PoetryUserLoginLog;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface PoetryUserLoginLogDao extends JdbcRepository<PoetryUserLoginLog, BigInteger> {
}
