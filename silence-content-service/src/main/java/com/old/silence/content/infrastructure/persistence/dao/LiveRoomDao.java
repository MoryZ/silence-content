package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.LiveRoom;
import com.old.silence.data.jdbc.repository.JdbcRepository;


/**
* LiveRoom数据访问接口
*/
public interface LiveRoomDao extends JdbcRepository<LiveRoom, BigInteger> {

}