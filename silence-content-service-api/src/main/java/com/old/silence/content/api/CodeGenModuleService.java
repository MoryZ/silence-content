package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.CodeGenModuleCommand;
import com.old.silence.content.api.dto.CodeGenModuleQuery;
import com.old.silence.content.api.vo.CodeGenModuleView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface CodeGenModuleService {

    @GetMapping(value = "/codeGenModules/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(CodeGenModuleView.class) Class<T> projectionType);

    @GetMapping(value = "/codeGenModules", params = {"pageNo", "pageSize"})
    <T> Page<T> queryPage(@Validated @SpringQueryMap CodeGenModuleQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeGenModuleView.class) Class<T> projectionType);


    @PostJsonMapping("/codeGenModules")
    BigInteger create(@RequestBody @Validated CodeGenModuleCommand command);

    @PutJsonMapping(value = "/codeGenModules/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated CodeGenModuleCommand command);

    @DeleteMapping("/codeGenModules/{id}")
    void deleteById(@PathVariable BigInteger id);
}
