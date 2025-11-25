package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.LiveBroadcasterCommandMapper;
import com.old.silence.content.console.api.assembler.LiveBroadcasterQueryMapper;
import com.old.silence.content.console.dto.LiveBroadcasterConsoleCommand;
import com.old.silence.content.console.dto.LiveBroadcasterConsoleQuery;
import com.old.silence.content.console.vo.LiveBroadcasterConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.LiveBroadcasterClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* LiveBroadcaster资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class LiveBroadcasterResource {
    private final LiveBroadcasterClient liveBroadcasterClient;
    private final LiveBroadcasterCommandMapper liveBroadcasterCommandMapper;
    private final LiveBroadcasterQueryMapper liveBroadcasterQueryMapper;

    public LiveBroadcasterResource(LiveBroadcasterClient liveBroadcasterClient,
                                LiveBroadcasterCommandMapper liveBroadcasterCommandMapper,
                                LiveBroadcasterQueryMapper liveBroadcasterQueryMapper) {
        this.liveBroadcasterClient = liveBroadcasterClient;
        this.liveBroadcasterCommandMapper = liveBroadcasterCommandMapper;
        this.liveBroadcasterQueryMapper = liveBroadcasterQueryMapper;
    }

    @GetMapping(value = "/liveBroadcasters/{id}")
    public LiveBroadcasterConsoleView findById(@PathVariable BigInteger id) {
        return liveBroadcasterClient.findById(id, LiveBroadcasterConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/liveBroadcasters", params = {"pageNo", "pageSize"})
    public Page<LiveBroadcasterConsoleView> query(LiveBroadcasterConsoleQuery query, Pageable pageable) {
        var LiveBroadcasterQuery = liveBroadcasterQueryMapper.convert(query);
        return liveBroadcasterClient.query(LiveBroadcasterQuery, pageable, LiveBroadcasterConsoleView.class);
    }

    @PostJsonMapping("/liveBroadcasters")
    public BigInteger create(@RequestBody LiveBroadcasterConsoleCommand command) {
        var liveBroadcasterCommand = liveBroadcasterCommandMapper.convert(command);
        return liveBroadcasterClient.create(liveBroadcasterCommand);
    }

    @PutJsonMapping(value = "/liveBroadcasters/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody LiveBroadcasterConsoleCommand command) {
        var liveBroadcasterCommand = liveBroadcasterCommandMapper.convert(command);
        liveBroadcasterClient.update(id, liveBroadcasterCommand);
    }

    @DeleteMapping("/liveBroadcasters/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        liveBroadcasterClient.deleteById(id);
    }
}