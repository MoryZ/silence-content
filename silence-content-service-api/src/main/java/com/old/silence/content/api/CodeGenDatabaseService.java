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
import com.old.silence.content.api.dto.CodeGenDatabaseCommand;
import com.old.silence.content.api.dto.CodeGenDatabaseQuery;
import com.old.silence.content.api.dto.FoodCommand;
import com.old.silence.content.api.dto.FoodQuery;
import com.old.silence.content.api.vo.CodeGenDatabaseView;
import com.old.silence.content.api.vo.FoodView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface CodeGenDatabaseService {

    @GetMapping(value = "/codeGenDatabases/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(CodeGenDatabaseView.class) Class<T> projectionType);

    @GetMapping(value = "/codeGenDatabases", params = {"pageNo", "pageSize"})
    <T> Page<T> queryPage(@Validated @SpringQueryMap CodeGenDatabaseQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeGenDatabaseView.class) Class<T> projectionType);

    @PostJsonMapping("/codeGenDatabases")
    BigInteger create(@RequestBody @Validated CodeGenDatabaseCommand command);

    @PutJsonMapping(value = "/codeGenDatabases/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated CodeGenDatabaseCommand command);

    @DeleteMapping("/codeGenDatabases/{id}")
    void deleteById(@PathVariable BigInteger id);
}
