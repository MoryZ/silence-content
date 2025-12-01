package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.old.silence.content.api.vo.ContentArticleView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
interface ContentArticleService {

    @GetMapping(value = "/contentArticles/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(ContentArticleView.class) Class<T> projectionType);


}
