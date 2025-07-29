package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.Content;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author MurrayZhang
 */
public interface ContentDao extends JdbcRepository<Content, BigInteger> {
    int updateStatus(ContentStatus status, BigInteger id);
}
