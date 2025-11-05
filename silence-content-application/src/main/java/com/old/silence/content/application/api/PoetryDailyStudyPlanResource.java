package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryDailyStudyPlanClient;
import com.old.silence.content.application.api.assembler.PoetryDailyStudyPlanApplicationMapper;
import com.old.silence.content.application.api.assembler.PoetryDailyStudyPlanApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationQuery;

/**
 * PoetryDailyStudyPlan资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryDailyStudyPlanResource implements PoetryDailyStudyPlanApplicationService{

    private final PoetryDailyStudyPlanClient poetryDailyStudyPlanClient;
    private final PoetryDailyStudyPlanApplicationMapper poetryDailyStudyPlanApplicationMapper;
    private final PoetryDailyStudyPlanApplicationQueryMapper  poetryDailyStudyPlanApplicationQueryMapper;

    public PoetryDailyStudyPlanResource(PoetryDailyStudyPlanClient poetryDailyStudyPlanClient,
                                        PoetryDailyStudyPlanApplicationMapper poetryDailyStudyPlanApplicationMapper,
                                        PoetryDailyStudyPlanApplicationQueryMapper poetryDailyStudyPlanApplicationQueryMapper) {
        this.poetryDailyStudyPlanClient = poetryDailyStudyPlanClient;
        this.poetryDailyStudyPlanApplicationMapper = poetryDailyStudyPlanApplicationMapper;
        this.poetryDailyStudyPlanApplicationQueryMapper = poetryDailyStudyPlanApplicationQueryMapper;
    }

    @Override
    public <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType) {
        return poetryDailyStudyPlanClient.findByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, planDate, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryDailyStudyPlanApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryDailyStudyPlanQuery = poetryDailyStudyPlanApplicationQueryMapper.convert(query);
        return poetryDailyStudyPlanClient.query(poetryDailyStudyPlanQuery, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryDailyStudyPlanApplicationCommand command) {
        var poetryDailyStudyPlanCommand = poetryDailyStudyPlanApplicationMapper.convert(command);
        return poetryDailyStudyPlanClient.create(poetryDailyStudyPlanCommand);
    }

    @Override
    public void update(BigInteger id, PoetryDailyStudyPlanApplicationCommand command) {
        var poetryDailyStudyPlanCommand = poetryDailyStudyPlanApplicationMapper.convert(command);
        poetryDailyStudyPlanClient.update(id, poetryDailyStudyPlanCommand);
    }
}