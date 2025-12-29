package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.CodeFileTemplateCommand;
import com.old.silence.content.api.dto.CodeFileTemplateQuery;
import com.old.silence.content.api.vo.CodeFileTemplatesView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * CodeFileTemplate服务接口
 */
interface CodeFileTemplateService {

    @GetMapping(value = "/codeFileTemplates/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(CodeFileTemplatesView.class) Class<T> projectionType);

    @GetMapping(value = "/codeFileTemplates")
    <T> Optional<T> findByTemplateName(@RequestParam String templateName, @ProjectedPayloadType(CodeFileTemplatesView.class) Class<T> projectionType);

    @GetMapping(value = "/codeFileTemplates", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap CodeFileTemplateQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeFileTemplatesView.class) Class<T> projectionType);

    @PostJsonMapping("/codeFileTemplates")
    BigInteger create(@RequestBody @Validated CodeFileTemplateCommand command);

    @PutJsonMapping(value = "/codeFileTemplates/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated CodeFileTemplateCommand command);

    @DeleteMapping("/codeFileTemplates/{id}")
    void deleteById(@PathVariable BigInteger id);
}
