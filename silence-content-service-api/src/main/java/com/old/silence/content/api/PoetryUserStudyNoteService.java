package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.api.dto.PoetryUserStudyNoteQuery;
import com.old.silence.content.api.vo.PoetryUserStudyNoteView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserStudyNote服务接口
 */
interface PoetryUserStudyNoteService {

    @GetMapping(value = "/poetryUserStudyNotes/{contentId}/{userId}")
    <T> List<T> findByContentIdAndUserId(@PathVariable BigInteger contentId, @PathVariable BigInteger userId,
                                         @ProjectedPayloadType(PoetryUserStudyNoteView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUserStudyNotes", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserStudyNoteQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserStudyNoteView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserStudyNotes")
    BigInteger create(@RequestBody @Validated PoetryUserStudyNoteCommand command);

    @PutJsonMapping(value = "/poetryUserStudyNotes/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserStudyNoteCommand command);

    @DeleteMapping("/poetryUserStudyNotes/{id}")
    void deleteById(@PathVariable BigInteger id);
}