package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.LiveRoomOrganization;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* LiveRoomOrganization数据访问接口
*/
public interface LiveRoomOrganizationDao extends JdbcRepository<LiveRoomOrganization, BigInteger> {

}