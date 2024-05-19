package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.Content;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;


public interface ContentDao extends JdbcRepository<Content, BigInteger> {


}
