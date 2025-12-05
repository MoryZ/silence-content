package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.enums.PromptFormatType;
import com.old.silence.content.domain.model.poetry.PromptCommonFormat;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;


/**
* PromptCommonFormat数据访问接口
*/
public interface PromptCommonFormatDao extends JdbcRepository<PromptCommonFormat, BigInteger> {

    <T> Optional<T> findByFormatType(PromptFormatType formatType, Class<T> projectionType);

}