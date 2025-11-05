package com.old.silence.content.application.api;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryAnswerRecordsClient;
import com.old.silence.content.application.api.assembler.PoetryAnswerRecordsApplicationMapper;
import com.old.silence.content.application.api.assembler.PoetryAnswerRecordsApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryAnswerRecordsApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryAnswerRecordsApplicationQuery;


/**
* PoetryAnswerRecords资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryAnswerRecordsResource implements PoetryAnswerRecordsApplicationClient {

    private final PoetryAnswerRecordsClient poetryAnswerRecordsClient;
    private final PoetryAnswerRecordsApplicationMapper poetryAnswerRecordsApplicationMapper;
    private final PoetryAnswerRecordsApplicationQueryMapper poetryAnswerRecordsApplicationQueryMapper;

    public PoetryAnswerRecordsResource(PoetryAnswerRecordsClient poetryAnswerRecordsClient,
                                       PoetryAnswerRecordsApplicationMapper poetryAnswerRecordsApplicationMapper,
                                       PoetryAnswerRecordsApplicationQueryMapper poetryAnswerRecordsApplicationQueryMapper) {
        this.poetryAnswerRecordsClient = poetryAnswerRecordsClient;
        this.poetryAnswerRecordsApplicationMapper = poetryAnswerRecordsApplicationMapper;
        this.poetryAnswerRecordsApplicationQueryMapper = poetryAnswerRecordsApplicationQueryMapper;
    }

    @Override
    public <T> Page<T> query(PoetryAnswerRecordsApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryAnswerRecordsQuery = poetryAnswerRecordsApplicationQueryMapper.convert(query);
        return poetryAnswerRecordsClient.query(poetryAnswerRecordsQuery, pageable,  projectionType);
    }

    @Override
    public BigInteger create(PoetryAnswerRecordsApplicationCommand command) {
        var poetryAnswerRecordsCommand = poetryAnswerRecordsApplicationMapper.convert(command);
        return poetryAnswerRecordsClient.create(poetryAnswerRecordsCommand);
    }
}