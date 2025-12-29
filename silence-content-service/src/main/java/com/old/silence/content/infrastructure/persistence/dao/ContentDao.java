package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.Content;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.time.Instant;

/**
 * @author moryzang
 */
public interface ContentDao extends JdbcRepository<Content, BigInteger> {
    int updateStatus(ContentStatus status, BigInteger id);

    int updateStickyTopAndStickyTopAt(boolean stickyTopStatus, Instant now, BigInteger id);
}
