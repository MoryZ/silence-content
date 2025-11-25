package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.LiveRoomOrganizationCommandMapper;
import com.old.silence.content.console.api.assembler.LiveRoomOrganizationQueryMapper;
import com.old.silence.content.console.dto.LiveRoomOrganizationConsoleCommand;
import com.old.silence.content.console.dto.LiveRoomOrganizationConsoleQuery;
import com.old.silence.content.console.vo.LiveRoomOrganizationConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.LiveRoomOrganizationClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* LiveRoomOrganization资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveRoomOrganizationResource {
    private final LiveRoomOrganizationClient liveRoomOrganizationClient;
    private final LiveRoomOrganizationCommandMapper liveRoomOrganizationCommandMapper;
    private final LiveRoomOrganizationQueryMapper liveRoomOrganizationQueryMapper;

    public LiveRoomOrganizationResource(LiveRoomOrganizationClient liveRoomOrganizationClient,
                                LiveRoomOrganizationCommandMapper liveRoomOrganizationCommandMapper,
                                LiveRoomOrganizationQueryMapper liveRoomOrganizationQueryMapper) {
        this.liveRoomOrganizationClient = liveRoomOrganizationClient;
        this.liveRoomOrganizationCommandMapper = liveRoomOrganizationCommandMapper;
        this.liveRoomOrganizationQueryMapper = liveRoomOrganizationQueryMapper;
    }

    @GetMapping(value = "/liveRoomOrganizations/{id}")
    public LiveRoomOrganizationConsoleView findById(@PathVariable BigInteger id) {
        return liveRoomOrganizationClient.findById(id, LiveRoomOrganizationConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/liveRoomOrganizations", params = {"pageNo", "pageSize"})
    public Page<LiveRoomOrganizationConsoleView> query(LiveRoomOrganizationConsoleQuery query, Pageable pageable) {
        var LiveRoomOrganizationQuery = liveRoomOrganizationQueryMapper.convert(query);
        return liveRoomOrganizationClient.query(LiveRoomOrganizationQuery, pageable, LiveRoomOrganizationConsoleView.class);
    }

    @PostJsonMapping("/liveRoomOrganizations")
    public BigInteger create(LiveRoomOrganizationConsoleCommand command) {
        var liveRoomOrganizationCommand = liveRoomOrganizationCommandMapper.convert(command);
        return liveRoomOrganizationClient.create(liveRoomOrganizationCommand);
    }

    @PutJsonMapping(value = "/liveRoomOrganizations/{id}")
    public void update(@PathVariable BigInteger id, LiveRoomOrganizationConsoleCommand command) {
        var liveRoomOrganizationCommand = liveRoomOrganizationCommandMapper.convert(command);
        liveRoomOrganizationClient.update(id, liveRoomOrganizationCommand);
    }

    @DeleteMapping("/liveRoomOrganizations/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        liveRoomOrganizationClient.deleteById(id);
    }
}