package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.FoodCommand;
import com.old.silence.content.api.dto.FoodQuery;
import com.old.silence.content.api.vo.FoodView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface CodeGenDatabaseService {

    @GetMapping(value = "/foods/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(FoodView.class) Class<T> projectionType);

    @GetMapping(value = "/foods", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap FoodQuery query, Pageable pageable,
                      @ProjectedPayloadType(FoodView.class) Class<T> projectionType);

    @GetMapping(value = "/foods", params = {"!pageNo", "!pageSize"})
    <T> List<T> query(@Validated @SpringQueryMap FoodQuery query,
                      @ProjectedPayloadType(FoodView.class) Class<T> projectionType);

    @PostJsonMapping("/foods")
    BigInteger create(@RequestBody @Validated FoodCommand command);

    @PutJsonMapping(value = "/foods/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated FoodCommand command);

    @DeleteMapping("/foods/{id}")
    void deleteById(@PathVariable BigInteger id);
}
