package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryDailyStudyPlanClient;
import com.old.silence.content.console.api.assembler.PoetryDailyStudyPlanQueryMapper;
import com.old.silence.content.console.dto.PoetryDailyStudyPlanConsoleQuery;
import com.old.silence.content.console.vo.PoetryDailyStudyPlanConsoleView;

/**
 * PoetryDailyStudyPlan资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryDailyStudyPlanResource {
    private final PoetryDailyStudyPlanClient poetryDailyStudyPlanClient;
    private final PoetryDailyStudyPlanQueryMapper poetryDailyStudyPlanQueryMapper;

    public PoetryDailyStudyPlanResource(PoetryDailyStudyPlanClient poetryDailyStudyPlanClient,
                                        PoetryDailyStudyPlanQueryMapper poetryDailyStudyPlanQueryMapper) {
        this.poetryDailyStudyPlanClient = poetryDailyStudyPlanClient;
        this.poetryDailyStudyPlanQueryMapper = poetryDailyStudyPlanQueryMapper;
    }


    @GetMapping(value = "/poetryDailyStudyPlans", params = {"pageNo", "pageSize"})
    public Page<PoetryDailyStudyPlanConsoleView> query(PoetryDailyStudyPlanConsoleQuery query, Pageable pageable) {
        var poetryDailyStudyPlanQuery = poetryDailyStudyPlanQueryMapper.convert(query);
        return poetryDailyStudyPlanClient.query(poetryDailyStudyPlanQuery, pageable, PoetryDailyStudyPlanConsoleView.class);
    }

}