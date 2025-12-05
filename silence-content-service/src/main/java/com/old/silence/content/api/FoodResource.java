package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.FoodMapper;
import com.old.silence.content.api.dto.FoodCommand;
import com.old.silence.content.api.dto.FoodQuery;
import com.old.silence.content.domain.model.takeout.Food;
import com.old.silence.content.domain.repository.takeout.FoodRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class FoodResource implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public FoodResource(FoodRepository foodRepository,
                        FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return foodRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> queryPage(FoodQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Food.class);
        return foodRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> List<T> query(FoodQuery query, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Food.class);
        return foodRepository.findByCriteria(criteria, projectionType);
    }

    @Override
    public BigInteger create(FoodCommand command) {
        var Food = foodMapper.convert(command);
        foodRepository.create(Food);
        return Food.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, FoodCommand command) {
        var Food = foodMapper.convert(command);
        Food.setId(id); // NOSONAR
        validateModifyingResult(foodRepository.update(Food));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(foodRepository.deleteById(id));
    }
}
