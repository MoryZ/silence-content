package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.ContentBook;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentBookDao extends JdbcRepository<ContentBook, BigInteger> {
}
