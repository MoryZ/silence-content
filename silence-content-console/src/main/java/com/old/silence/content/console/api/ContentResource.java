package com.old.silence.content.console.api;

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
import com.old.silence.content.api.ContentClient;
import com.old.silence.content.console.api.assembler.ContentCommandMapper;
import com.old.silence.content.console.api.assembler.ContentQueryMapper;
import com.old.silence.content.console.dto.ContentConsoleCommand;
import com.old.silence.content.console.dto.ContentConsoleQuery;
import com.old.silence.content.console.service.ContentConsoleService;
import com.old.silence.content.console.vo.ContentConsoleView;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentResource {

    private final ContentConsoleService contentConsoleService;
    private final ContentClient contentClient;
    private final ContentCommandMapper contentCommandMapper;
    private final ContentQueryMapper contentQueryMapper;

    public ContentResource(ContentConsoleService contentConsoleService,
                           ContentClient contentClient,
                           ContentCommandMapper contentCommandMapper,
                           ContentQueryMapper contentQueryMapper) {
        this.contentConsoleService = contentConsoleService;
        this.contentClient = contentClient;
        this.contentCommandMapper = contentCommandMapper;
        this.contentQueryMapper = contentQueryMapper;
    }

    @GetMapping("/contents/{id}")
    public ContentConsoleView findById(@PathVariable BigInteger id) {
        return contentClient.findById(id, ContentConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/contents", params = {"pageNo", "pageSize"})
    public Page<ContentConsoleView> query(ContentConsoleQuery query, Pageable pageable) {
        var contentQuery = contentQueryMapper.convert(query);
        return contentClient.query(contentQuery, pageable, ContentConsoleView.class);
    }

    @PostMapping("/contents")
    public BigInteger create(@RequestBody ContentConsoleCommand command) {
        var contentCommand = contentCommandMapper.convert(command);
        return contentConsoleService.create(contentCommand);
    }

    @PutMapping("/contents/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody ContentConsoleCommand command) {
        var contentCommand = contentCommandMapper.convert(command);
        contentConsoleService.update(id, contentCommand);
    }

    @PutMapping("/contents/{id}/stickyTop")
    public void stickyTop(@PathVariable BigInteger id) {
        contentClient.updateStickyTopStatus(id, true);
    }

    @PutMapping("/contents/{id}/cancelStickyTop")
    public void cancelStickyTop(@PathVariable BigInteger id) {
        contentClient.updateStickyTopStatus(id, false);
    }

    @PutMapping("/contents/{id}/published")
    public void published(@PathVariable BigInteger id) {
        contentClient.updateStatus(id, ContentStatus.PUBLISHED);
    }

    @PutMapping("/contents/{id}/unpublished")
    public void unpublished(@PathVariable BigInteger id) {
        contentClient.updateStatus(id, ContentStatus.UNPUBLISHED);
    }

    @DeleteMapping("/contents/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        contentClient.deleteById(id);
    }
}
