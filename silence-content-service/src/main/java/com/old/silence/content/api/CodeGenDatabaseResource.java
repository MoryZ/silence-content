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
 * @author ruoyi
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class FoodResource implements FoodService {

    private final FoodRepository contentRepository;
    private final FoodMapper foodMapper;

    public FoodResource(FoodRepository contentRepository,
                        FoodMapper foodMapper) {
        this.contentRepository = contentRepository;
        this.foodMapper = foodMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(FoodQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Food.class);
        return contentRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> List<T> query(FoodQuery query, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Food.class);
        return contentRepository.findByCriteria(criteria, projectionType);
    }

    @Override
    public BigInteger create(FoodCommand command) {
        var Food = foodMapper.convert(command);
        contentRepository.create(Food);
        return Food.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, FoodCommand command) {
        var Food = foodMapper.convert(command);
        Food.setId(id); // NOSONAR
        validateModifyingResult(contentRepository.update(Food));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(contentRepository.deleteById(id));
    }
}
