package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.Food;
import com.old.silence.content.domain.repository.FoodRepository;
import com.old.silence.content.infrastructure.persistence.dao.FoodDao;


@Repository
public class FoodMyBatisRepository implements FoodRepository {

    private final FoodDao foodDao;

    public FoodMyBatisRepository(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return foodDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return foodDao.findByCriteria(criteria, pageable, projectionType);
    }


    @Override
    public <T> List<T> findByCriteria(Criteria criteria, Class<T> projectionType) {
        return foodDao.findByCriteria(criteria, projectionType);
    }

    @Override
    public int create(Food food) {
        return foodDao.insert(food);
    }

    @Override
    public int update(Food food) {
        return foodDao.update(food);
    }

    @Override
    public int deleteById(BigInteger id) {
        return foodDao.deleteById(id);
    }
}
