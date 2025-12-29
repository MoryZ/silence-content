package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.CodeGenProjectCommand;
import com.old.silence.content.api.dto.CodeGenProjectQuery;
import com.old.silence.content.api.vo.CodeGenProjectView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
interface CodeGenProjectService {

    @GetMapping(value = "/codeGenProjects/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(CodeGenProjectView.class) Class<T> projectionType);

    @GetMapping(value = "/codeGenProjects", params = {"pageNo", "pageSize"})
    <T> Page<T> queryPage(@Validated @SpringQueryMap CodeGenProjectQuery query, Pageable pageable,
                          @ProjectedPayloadType(CodeGenProjectView.class) Class<T> projectionType);


    @PostJsonMapping("/codeGenProjects")
    BigInteger create(@RequestBody @Validated CodeGenProjectCommand command);

    @PutJsonMapping("/codeGenProjects/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated CodeGenProjectCommand command);

    @DeleteMapping("/codeGenProjects/{id}")
    void deleteById(@PathVariable BigInteger id);
}
