package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.PromptTemplateCommand;
import com.old.silence.content.api.dto.PromptTemplateQuery;
import com.old.silence.content.api.vo.PromptTemplateView;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PromptTemplate服务接口
*/
interface PromptTemplateService {

    @GetMapping(value = "/promptTemplates/{subCategoryId}/{templateType}")
    <T> Optional<T> findBySubCategoryIdAndTemplateType(@PathVariable BigInteger subCategoryId, @PathVariable PromptTemplateType templateType, @ProjectedPayloadType(PromptTemplateView.class) Class<T> projectionType);

    @GetMapping(value = "/promptTemplates", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PromptTemplateQuery query, Pageable pageable,
                        @ProjectedPayloadType(PromptTemplateView.class) Class<T> projectionType);

    @PostJsonMapping("/promptTemplates")
    BigInteger create(@RequestBody @Validated PromptTemplateCommand command);

    @PutJsonMapping(value = "/promptTemplates/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PromptTemplateCommand command);

    @DeleteMapping("/promptTemplates/{id}")
    void deleteById(@PathVariable BigInteger id);
}