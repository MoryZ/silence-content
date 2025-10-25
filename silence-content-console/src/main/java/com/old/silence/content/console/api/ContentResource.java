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
import com.old.silence.content.console.vo.ContentConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentResource {

    private final ContentClient contentClient;
    private final ContentCommandMapper contentCommandMapper;
    private final ContentQueryMapper contentQueryMapper;

    public ContentResource(ContentClient contentClient,
                           ContentCommandMapper contentCommandMapper,
                           ContentQueryMapper contentQueryMapper) {
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
        return contentClient.create(contentCommand);
    }

    @PutMapping("/contents/{id}")
    public void update(@PathVariable BigInteger id, ContentConsoleCommand command) {
        var contentCommand = contentCommandMapper.convert(command);
        contentClient.update(id, contentCommand);
    }

    @DeleteMapping("/contents/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        contentClient.deleteById(id);
    }
}
