package com.old.silence.content.api;

import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.api.vo.ContentView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author MurrayZhang
 */
interface ContentService {

    @GetMapping(value = "/contents/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(ContentView.class) Class<T> projectionType);

    @GetMapping(value = "/contents", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated ContentQuery query, Pageable pageable,
                      @ProjectedPayloadType(ContentView.class) Class<T> projectionType);

    @PostJsonMapping( "/contents")
    BigInteger create(@RequestBody @Validated ContentCommand command) ;

    @PutJsonMapping(value = "/contents/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated ContentCommand command);

    @DeleteMapping("/contents/{id}")
    void deleteById(@PathVariable BigInteger id);
}
