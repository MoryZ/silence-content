package com.old.silence.content.console.api;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserStudyNoteClient;
import com.old.silence.content.console.api.assembler.PoetryUserStudyNoteCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserStudyNoteQueryMapper;
import com.old.silence.content.console.dto.PoetryUserStudyNoteConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserStudyNoteConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserStudyNoteConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;


/**
 * PoetryUserStudyNote资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudyNoteResource {
    private final PoetryUserStudyNoteClient poetryUserStudyNoteClient;
    private final PoetryUserStudyNoteCommandMapper poetryUserStudyNoteCommandMapper;
    private final PoetryUserStudyNoteQueryMapper poetryUserStudyNoteQueryMapper;

    public PoetryUserStudyNoteResource(PoetryUserStudyNoteClient poetryUserStudyNoteClient,
                                       PoetryUserStudyNoteCommandMapper poetryUserStudyNoteCommandMapper,
                                       PoetryUserStudyNoteQueryMapper poetryUserStudyNoteQueryMapper) {
        this.poetryUserStudyNoteClient = poetryUserStudyNoteClient;
        this.poetryUserStudyNoteCommandMapper = poetryUserStudyNoteCommandMapper;
        this.poetryUserStudyNoteQueryMapper = poetryUserStudyNoteQueryMapper;
    }


    @GetMapping("/poetryUserStudyNotes/{id}")
    public PoetryUserStudyNoteConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserStudyNoteClient.findById(id, PoetryUserStudyNoteConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUserStudyNotes", params = {"pageNo", "pageSize"})
    public Page<PoetryUserStudyNoteConsoleView> query(PoetryUserStudyNoteConsoleQuery query, Pageable pageable) {
        var poetryUserStudyNoteQuery = poetryUserStudyNoteQueryMapper.convert(query);
        return poetryUserStudyNoteClient.query(poetryUserStudyNoteQuery, pageable, PoetryUserStudyNoteConsoleView.class);
    }

    @PostMapping(value = "/poetryUserStudyNotes")
    public String create(@RequestBody PoetryUserStudyNoteConsoleCommand command) {
        var poetryUserStudyNoteCommand = poetryUserStudyNoteCommandMapper.convert(command);
        return String.valueOf(poetryUserStudyNoteClient.create(poetryUserStudyNoteCommand));
    }

    @PutMapping("/poetryUserStudyNotes/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserStudyNoteConsoleCommand command) {
        var poetryUserStudyNoteCommand = poetryUserStudyNoteCommandMapper.convert(command);
        poetryUserStudyNoteClient.update(id, poetryUserStudyNoteCommand);
    }

    @DeleteMapping("/poetryUserStudyNotes/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserStudyNoteClient.deleteById(id);
    }
}