package com.old.silence.content.api;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import com.old.silence.content.api.vo.CategoryView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author MurrayZhang
 * @Description
 */
interface CategoryService {

    @GetMapping(value = "/categories", params = {"!pageNo", "!pageSize"})
    <T> List<T> queryAll(@ProjectedPayloadType(CategoryView.class) Class<T> projectionType);

}
