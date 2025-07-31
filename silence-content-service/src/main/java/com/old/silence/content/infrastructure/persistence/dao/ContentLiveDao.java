package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.model.ContentVideo;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface ContentLiveDao extends JdbcRepository<ContentLive, BigInteger> {
}
