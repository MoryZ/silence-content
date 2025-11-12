package com.old.silence.content.api;


import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import com.old.silence.content.api.dto.CategoryQuery;
import com.old.silence.content.api.vo.CategoryView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface CategoryService {

    @GetMapping(value = "/categories", params = {"!pageNo", "!pageSize"})
    <T> Page<T> queryAll(@SpringQueryMap @Validated CategoryQuery categoryQuery, @ProjectedPayloadType(CategoryView.class) Class<T> projectionType);

}
