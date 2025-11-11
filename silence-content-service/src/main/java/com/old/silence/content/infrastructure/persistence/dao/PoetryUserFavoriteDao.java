package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserFavorite;
import com.old.silence.content.infrastructure.persistence.dao.support.NumberStatsVo;
import com.old.silence.data.jdbc.repository.JdbcRepository;
import com.old.silence.data.jdbc.repository.query.Query;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryUserFavorite数据访问接口
*/
public interface PoetryUserFavoriteDao extends JdbcRepository<PoetryUserFavorite, BigInteger> {


    @Query(name = "PoetryUserFavoriteDao.findUserFavoriteStats", value = "select user_id userId, count(*) indicatorAccumulation from poetry_user_favorite " +
            "group by user_id order by indicatorAccumulation desc")
    List<NumberStatsVo> findUserFavoriteStats();


}