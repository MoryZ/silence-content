package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.application.api.dto.PoetryUserStudyNoteApplicationCommand;
import com.old.silence.content.application.api.vo.PoetryUserStudyNoteApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserStudyNote服务接口
 */
interface PoetryUserStudyNoteApplicationService {

    @GetMapping(value = "/poetryUserStudyNotes/{contentId}/{userId}")
    <T> List<T> findByContentIdAndUserId(@PathVariable BigInteger contentId, @PathVariable BigInteger userId,
                                         @ProjectedPayloadType(PoetryUserStudyNoteApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserStudyNotes")
    BigInteger create(@RequestBody @Validated PoetryUserStudyNoteApplicationCommand command);

    @PutJsonMapping(value = "/poetryUserStudyNotes/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserStudyNoteApplicationCommand command);

    @DeleteMapping("/poetryUserStudyNotes/{id}")
    void deleteById(@PathVariable BigInteger id);
}