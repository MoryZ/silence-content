package com.old.silence.content.infrastructure.persistence.dao;


import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.content.domain.model.ContentContentTagId;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */
public interface ContentContentTagDao extends JdbcRepository<ContentContentTag, ContentContentTagId> {
}
