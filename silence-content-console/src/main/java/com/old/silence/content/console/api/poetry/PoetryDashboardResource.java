package com.old.silence.content.console.api.poetry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryAnswerRecordsClient;
import com.old.silence.content.api.UserInteractionLogClient;
import com.old.silence.content.api.vo.StatsVo;

import java.util.List;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryDashboardResource {


    private final PoetryAnswerRecordsClient poetryAnswerRecordsClient;
    private final UserInteractionLogClient userInteractionLogClient;

    public PoetryDashboardResource(PoetryAnswerRecordsClient poetryAnswerRecordsClient,
                                   UserInteractionLogClient userInteractionLogClient) {
        this.poetryAnswerRecordsClient = poetryAnswerRecordsClient;
        this.userInteractionLogClient = userInteractionLogClient;
    }

    // 正确率最高Top5
    @GetMapping("/dashboard/accuracy/top5")
    public List<StatsVo> getMaxAccuracyTop5() {
        return poetryAnswerRecordsClient.findMaxAccuracyTop5();
    }

    // 答题数Top5
    @GetMapping("/dashboard/answer/top5")
    public List<StatsVo> getMaxAnswerTop5() {
        return poetryAnswerRecordsClient.findMaxAnswerTop5();
    }

    // 收藏榜最高Top5
    @GetMapping("/dashboard/favorite/top5")
    public List<StatsVo> getFavoriteTop5() {
        return userInteractionLogClient.findFavoriteTop5();
    }
}
