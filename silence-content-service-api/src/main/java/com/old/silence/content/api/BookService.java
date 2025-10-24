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
import com.old.silence.content.api.dto.BookCommand;
import com.old.silence.content.api.dto.BookQuery;
import com.old.silence.content.api.vo.BookView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface BookService {

    @GetMapping(value = "/books/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                             @ProjectedPayloadType(BookView.class) Class<T> projectionType);

    @GetMapping(value = "/books", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap BookQuery query, Pageable pageable,
                      @ProjectedPayloadType(BookView.class) Class<T> projectionType);

    @PostJsonMapping("/books")
    BigInteger create(@RequestBody @Validated BookCommand command);

    @PutJsonMapping(value = "/books/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated BookCommand command);

    @DeleteMapping("/books/{id}")
    void deleteById(@PathVariable BigInteger id);
}
