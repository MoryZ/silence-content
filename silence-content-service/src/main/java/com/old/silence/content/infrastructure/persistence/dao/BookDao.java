package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.Book;
import com.old.silence.content.domain.model.Content;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;


public interface BookDao extends JdbcRepository<Book, BigInteger> {


}
