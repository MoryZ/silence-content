package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.LiveBroadcaster;
import com.old.silence.data.jdbc.repository.JdbcRepository;


/**
* LiveBroadcaster数据访问接口
*/
public interface LiveBroadcasterDao extends JdbcRepository<LiveBroadcaster, BigInteger> {

}