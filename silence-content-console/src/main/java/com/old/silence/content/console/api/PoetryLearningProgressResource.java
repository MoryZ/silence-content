package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryDailyStudyPlanClient;
import com.old.silence.content.console.vo.PoetryDailyStudyPlanConsoleView;
import com.old.silence.content.console.vo.PoetryLearningProgressVo;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryLearningProgressResource {

    private final PoetryDailyStudyPlanClient poetryDailyStudyPlanClient;

    public PoetryLearningProgressResource(PoetryDailyStudyPlanClient poetryDailyStudyPlanClient) {
        this.poetryDailyStudyPlanClient = poetryDailyStudyPlanClient;
    }

    @GetMapping("/poetryLearningProgress")
    public PoetryLearningProgressVo progress(@RequestParam BigInteger userId, @RequestParam BigInteger subCategoryId) {
        var poetryDailyStudyPlanView = poetryDailyStudyPlanClient.findByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, LocalDate.now(),
                PoetryDailyStudyPlanConsoleView.class).orElseThrow(ResourceNotFoundException::new);
        poetryDailyStudyPlanView.getCompletedNewItems();
        poetryDailyStudyPlanView.getCompletedReviewItems();

        return new PoetryLearningProgressVo(poetryDailyStudyPlanView.getNewItemIds(), poetryDailyStudyPlanView.getReviewItemIds(),
                poetryDailyStudyPlanView.getCompletedNewItems(), poetryDailyStudyPlanView.getCompletedReviewItems(), poetryDailyStudyPlanView.getCompletionRate());


    }
}
