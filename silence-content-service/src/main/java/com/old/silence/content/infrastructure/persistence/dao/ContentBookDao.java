package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.ContentBook;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 * @Description
 */
public interface ContentBookDao extends JdbcRepository<ContentBook, BigInteger> {
}
