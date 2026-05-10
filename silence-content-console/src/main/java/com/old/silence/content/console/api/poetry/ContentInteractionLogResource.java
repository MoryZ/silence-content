package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.ContentInteractionLogClient;
import com.old.silence.content.console.api.assembler.ContentInteractionLogQueryMapper;
import com.old.silence.content.console.dto.ContentInteractionLogConsoleQuery;
import com.old.silence.content.console.vo.ContentInteractionLogConsoleView;

/**
 * ContentInteractionLog资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class ContentInteractionLogResource {
    private final ContentInteractionLogClient contentInteractionLogClient;
    private final ContentInteractionLogQueryMapper contentInteractionLogQueryMapper;

    public ContentInteractionLogResource(ContentInteractionLogClient contentInteractionLogClient,
                                         ContentInteractionLogQueryMapper contentInteractionLogQueryMapper) {
        this.contentInteractionLogClient = contentInteractionLogClient;
        this.contentInteractionLogQueryMapper = contentInteractionLogQueryMapper;
    }

    @GetMapping(value = "/contentInteractionLogs", params = {"pageNo", "pageSize"})
    public Page<ContentInteractionLogConsoleView> query(ContentInteractionLogConsoleQuery query, Pageable pageable) {
        var contentInteractionLogQuery = contentInteractionLogQueryMapper.convert(query);
        return contentInteractionLogClient.query(contentInteractionLogQuery, pageable, ContentInteractionLogConsoleView.class);
    }

}