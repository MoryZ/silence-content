package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.poetry.UserInteractionLog;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* UserInteractionLog仓储接口
*/
public interface UserInteractionLogRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    List<StatsVo> findFavoriteTop5();

    int create(UserInteractionLog userInteractionLog);

    int update(UserInteractionLog userInteractionLog);

    int deleteById(BigInteger id);

}