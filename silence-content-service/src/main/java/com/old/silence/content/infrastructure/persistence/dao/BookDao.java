package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.Book;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * Book数据访问接口
 */
public interface BookDao extends JdbcRepository<Book, BigInteger> {

}