package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.UserInteractionLog;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * UserInteractionLog数据访问接口
 */
public interface UserInteractionLogDao extends JdbcRepository<UserInteractionLog, BigInteger> {


}