package com.old.silence.content.infrastructure.persistence.dao.takeout;

import com.old.silence.content.domain.model.takeout.Category;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface CategoryDao extends JdbcRepository<Category, BigInteger> {
}
