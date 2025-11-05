package com.old.silence.content.application.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.PoetryQuizQuestionsClient;
import com.old.silence.content.application.api.assembler.PoetryQuizQuestionsApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryQuizQuestionsApplicationQuery;


/**
* PoetryQuizQuestions资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryQuizQuestionsResource implements PoetryQuizQuestionsApplicationService {

    private final PoetryQuizQuestionsClient poetryQuizQuestionsClient;
    private final PoetryQuizQuestionsApplicationQueryMapper poetryQuizQuestionsApplicationQueryMapper;

    public PoetryQuizQuestionsResource(PoetryQuizQuestionsClient poetryQuizQuestionsClient,
                                       PoetryQuizQuestionsApplicationQueryMapper poetryQuizQuestionsApplicationQueryMapper) {
        this.poetryQuizQuestionsClient = poetryQuizQuestionsClient;
        this.poetryQuizQuestionsApplicationQueryMapper = poetryQuizQuestionsApplicationQueryMapper;
    }

    @Override
    public <T> Page<T> query(PoetryQuizQuestionsApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryQuizQuestionsQuery = poetryQuizQuestionsApplicationQueryMapper.convert(query);
        return poetryQuizQuestionsClient.query(poetryQuizQuestionsQuery, pageable, projectionType);
    }
}