package com.old.silence.content.application.api;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserFavoriteClient;
import com.old.silence.content.application.api.assembler.PoetryUserFavoriteApplicationMapper;
import com.old.silence.content.application.api.assembler.PoetryUserFavoriteApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationQuery;

/**
 * PoetryUserFavorite资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserFavoriteResource implements PoetryUserFavoriteApplicationService {

    private final PoetryUserFavoriteClient poetryUserFavoriteClient;
    private final PoetryUserFavoriteApplicationMapper poetryUserFavoriteApplicationMapper;
    private final PoetryUserFavoriteApplicationQueryMapper poetryUserFavoriteApplicationQueryMapper;

    public PoetryUserFavoriteResource(PoetryUserFavoriteClient poetryUserFavoriteClient,
                                      PoetryUserFavoriteApplicationMapper poetryUserFavoriteApplicationMapper,
                                      PoetryUserFavoriteApplicationQueryMapper poetryUserFavoriteApplicationQueryMapper) {
        this.poetryUserFavoriteClient = poetryUserFavoriteClient;
        this.poetryUserFavoriteApplicationMapper = poetryUserFavoriteApplicationMapper;
        this.poetryUserFavoriteApplicationQueryMapper = poetryUserFavoriteApplicationQueryMapper;
    }

    @Override
    public <T> Page<T> query(PoetryUserFavoriteApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryUserFavoriteQuery = poetryUserFavoriteApplicationQueryMapper.convert(query);
        return poetryUserFavoriteClient.query(poetryUserFavoriteQuery, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserFavoriteApplicationCommand command) {
        var poetryUserFavoriteCommand = poetryUserFavoriteApplicationMapper.convert(command);
        return poetryUserFavoriteClient.create(poetryUserFavoriteCommand);
    }

    @Override
    public void deleteById(BigInteger id) {
        poetryUserFavoriteClient.deleteById(id);
    }
}