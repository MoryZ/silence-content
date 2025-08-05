package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.BookContentTagId;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface BookContentTagDao extends JdbcRepository<BookContentTagId, BigInteger> {
}
