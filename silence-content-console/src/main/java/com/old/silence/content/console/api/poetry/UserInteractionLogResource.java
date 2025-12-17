package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.UserInteractionLogClient;
import com.old.silence.content.console.api.assembler.UserInteractionLogQueryMapper;
import com.old.silence.content.console.dto.UserInteractionLogConsoleQuery;
import com.old.silence.content.console.vo.UserInteractionLogConsoleView;

/**
 * UserInteractionLog资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class UserInteractionLogResource {
    private final UserInteractionLogClient userInteractionLogClient;
    private final UserInteractionLogQueryMapper userInteractionLogQueryMapper;

    public UserInteractionLogResource(UserInteractionLogClient userInteractionLogClient,
                                      UserInteractionLogQueryMapper userInteractionLogQueryMapper) {
        this.userInteractionLogClient = userInteractionLogClient;
        this.userInteractionLogQueryMapper = userInteractionLogQueryMapper;
    }

    @GetMapping(value = "/userInteractionLogs", params = {"pageNo", "pageSize"})
    public Page<UserInteractionLogConsoleView> query(UserInteractionLogConsoleQuery query, Pageable pageable) {
        var userInteractionLogQuery = userInteractionLogQueryMapper.convert(query);
        return userInteractionLogClient.query(userInteractionLogQuery, pageable, UserInteractionLogConsoleView.class);
    }

}