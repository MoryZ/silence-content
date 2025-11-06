package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserStudyNoteClient;
import com.old.silence.content.console.api.assembler.PoetryUserStudyNoteQueryMapper;
import com.old.silence.content.console.dto.PoetryUserStudyNoteConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserStudyNoteConsoleView;


/**
 * PoetryUserStudyNote资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudyNoteResource {
    private final PoetryUserStudyNoteClient poetryUserStudyNoteClient;
    private final PoetryUserStudyNoteQueryMapper poetryUserStudyNoteQueryMapper;

    public PoetryUserStudyNoteResource(PoetryUserStudyNoteClient poetryUserStudyNoteClient,
                                       PoetryUserStudyNoteQueryMapper poetryUserStudyNoteQueryMapper) {
        this.poetryUserStudyNoteClient = poetryUserStudyNoteClient;
        this.poetryUserStudyNoteQueryMapper = poetryUserStudyNoteQueryMapper;
    }

    @GetMapping(value = "/poetryUserStudyNotes", params = {"pageNo", "pageSize"})
    public Page<PoetryUserStudyNoteConsoleView> query(PoetryUserStudyNoteConsoleQuery query, Pageable pageable) {
        var poetryUserStudyNoteQuery = poetryUserStudyNoteQueryMapper.convert(query);
        return poetryUserStudyNoteClient.query(poetryUserStudyNoteQuery, pageable, PoetryUserStudyNoteConsoleView.class);
    }

}