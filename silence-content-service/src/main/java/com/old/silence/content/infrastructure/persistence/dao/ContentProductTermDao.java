package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.model.ContentProductTerm;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface ContentProductTermDao extends JdbcRepository<ContentProductTerm, BigInteger> {
}
