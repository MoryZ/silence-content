package com.old.silence.content.domain.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.Food;


public interface FoodRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    <T> List<T> findByCriteria(Criteria criteria, Class<T> projectionType);

    int create(Food food);

    int update(Food food);

    int deleteById(BigInteger id);
}
