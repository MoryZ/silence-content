package com.old.silence.content.infrastructure.persistence.dao;


import com.old.silence.content.domain.model.ContentContentTag;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentContentTagDao extends JdbcRepository<ContentContentTag, BigInteger> {

    int deleteByContentId(BigInteger contentId);

    void deleteByTagId(BigInteger tagId);
}
