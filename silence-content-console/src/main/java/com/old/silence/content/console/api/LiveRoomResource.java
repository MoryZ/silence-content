package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.LiveRoomCommandMapper;
import com.old.silence.content.console.api.assembler.LiveRoomQueryMapper;
import com.old.silence.content.console.dto.LiveRoomConsoleCommand;
import com.old.silence.content.console.dto.LiveRoomConsoleQuery;
import com.old.silence.content.console.vo.LiveRoomConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.LiveRoomClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* LiveRoom资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveRoomResource {
    private final LiveRoomClient liveRoomClient;
    private final LiveRoomCommandMapper liveRoomCommandMapper;
    private final LiveRoomQueryMapper liveRoomQueryMapper;

    public LiveRoomResource(LiveRoomClient liveRoomClient,
                                LiveRoomCommandMapper liveRoomCommandMapper,
                                LiveRoomQueryMapper liveRoomQueryMapper) {
        this.liveRoomClient = liveRoomClient;
        this.liveRoomCommandMapper = liveRoomCommandMapper;
        this.liveRoomQueryMapper = liveRoomQueryMapper;
    }

    @GetMapping(value = "/liveRooms/{id}")
    public LiveRoomConsoleView findById(@PathVariable BigInteger id) {
        return liveRoomClient.findById(id, LiveRoomConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/liveRooms", params = {"pageNo", "pageSize"})
    public Page<LiveRoomConsoleView> query(LiveRoomConsoleQuery query, Pageable pageable) {
        var LiveRoomQuery = liveRoomQueryMapper.convert(query);
        return liveRoomClient.query(LiveRoomQuery, pageable, LiveRoomConsoleView.class);
    }

    @PostJsonMapping("/liveRooms")
    public BigInteger create(@RequestBody LiveRoomConsoleCommand command) {
        var liveRoomCommand = liveRoomCommandMapper.convert(command);
        return liveRoomClient.create(liveRoomCommand);
    }

    @PutJsonMapping(value = "/liveRooms/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody LiveRoomConsoleCommand command) {
        var liveRoomCommand = liveRoomCommandMapper.convert(command);
        liveRoomClient.update(id, liveRoomCommand);
    }

    @DeleteMapping("/liveRooms/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        liveRoomClient.deleteById(id);
    }
}