package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.ContentInteractionAccumulation;
import com.old.silence.content.domain.model.ContentVideo;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentInteractionAccumulationDao extends JdbcRepository<ContentInteractionAccumulation, BigInteger> {
}
