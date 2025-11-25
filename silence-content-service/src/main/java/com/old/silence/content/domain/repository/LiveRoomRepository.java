package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.LiveRoom;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveRoom仓储接口
*/
public interface LiveRoomRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(LiveRoom liveRoom);

    int update(LiveRoom liveRoom);

    int deleteById(BigInteger id);
}