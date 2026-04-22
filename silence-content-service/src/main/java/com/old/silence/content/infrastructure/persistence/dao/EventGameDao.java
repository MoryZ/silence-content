package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.EventGame;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface EventGameDao extends JdbcRepository<EventGame, BigInteger> {
}
