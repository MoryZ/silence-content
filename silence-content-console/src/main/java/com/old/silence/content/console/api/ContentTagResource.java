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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.ContentTagClient;
import com.old.silence.content.api.vo.ContentTagTreeVo;
import com.old.silence.content.console.api.assembler.ContentTagCommandMapper;
import com.old.silence.content.console.api.assembler.ContentTagQueryMapper;
import com.old.silence.content.console.dto.ContentTagConsoleCommand;
import com.old.silence.content.console.dto.ContentTagConsoleQuery;
import com.old.silence.content.console.vo.ContentTagConsoleView;
import com.old.silence.content.domain.enums.ContentTagType;

import java.math.BigInteger;
import java.util.List;


/**
 *
 * @author murrayZhang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentTagResource {
    private final ContentTagClient contentTagClient;
    private final ContentTagCommandMapper contentTagCommandMapper;
    private final ContentTagQueryMapper contentTagQueryMapper;

    public ContentTagResource(ContentTagClient contentTagClient,
                              ContentTagCommandMapper contentTagCommandMapper,
                              ContentTagQueryMapper contentTagQueryMapper) {
        this.contentTagClient = contentTagClient;
        this.contentTagCommandMapper = contentTagCommandMapper;
        this.contentTagQueryMapper = contentTagQueryMapper;
    }

    @GetMapping(value = "/contentTags", params = {"pageNo", "pageSize"})
    public Page<ContentTagConsoleView> findTags(ContentTagConsoleQuery query, Pageable pageable) {
        var contentTagQuery = contentTagQueryMapper.convert(query);
        return contentTagClient.query(contentTagQuery, pageable, ContentTagConsoleView.class);
    }

    @GetMapping(value = "/contentTags/{id}/tree", params = {"type", "enabled"})
    public List<ContentTagTreeVo> findTags(@PathVariable BigInteger id, @RequestParam ContentTagType type, @RequestParam Boolean enabled) {
        return contentTagClient.findTags(id, type, enabled);
    }

    @PostMapping("/contentTags")
    public BigInteger create(@RequestBody ContentTagConsoleCommand command) {
        var contentCommand = contentTagCommandMapper.convert(command);
        return contentTagClient.create(contentCommand);
    }

    @PutMapping("/contentTags/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody ContentTagConsoleCommand command) {
        var contentCommand = contentTagCommandMapper.convert(command);
        contentTagClient.update(id, contentCommand);
    }

    @DeleteMapping("/contentTags/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        contentTagClient.deleteById(id);
    }
}
