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


import com.old.silence.content.api.dto.FreemarkerTemplatesCommand;
import com.old.silence.content.api.dto.FreemarkerTemplatesQuery;
import com.old.silence.content.api.vo.FreemarkerTemplatesView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * FreemarkerTemplates服务接口
 */
interface FreemarkerTemplatesService {

    @GetMapping(value = "/freemarkerTemplates/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(FreemarkerTemplatesView.class) Class<T> projectionType);

    @GetMapping(value = "/freemarkerTemplates")
    <T> Optional<T> findByTemplateName(@RequestParam String templateName, @ProjectedPayloadType(FreemarkerTemplatesView.class) Class<T> projectionType);

    @GetMapping(value = "/freemarkerTemplates", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap FreemarkerTemplatesQuery query, Pageable pageable,
                      @ProjectedPayloadType(FreemarkerTemplatesView.class) Class<T> projectionType);

    @PostJsonMapping("/freemarkerTemplates")
    BigInteger create(@RequestBody @Validated FreemarkerTemplatesCommand command);

    @PutJsonMapping(value = "/freemarkerTemplates/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated FreemarkerTemplatesCommand command);

    @DeleteMapping("/freemarkerTemplates/{id}")
    void deleteById(@PathVariable BigInteger id);
}
