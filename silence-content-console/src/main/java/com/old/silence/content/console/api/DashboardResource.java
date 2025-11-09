package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryAnswerRecordsClient;
import com.old.silence.content.api.PoetryUserFavoriteClient;
import com.old.silence.content.api.vo.StatsVo;

import java.util.List;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class DashboardResource {


    private final PoetryAnswerRecordsClient poetryAnswerRecordsClient;
    private final PoetryUserFavoriteClient poetryUserFavoriteClient;

    public DashboardResource(PoetryAnswerRecordsClient poetryAnswerRecordsClient,
                             PoetryUserFavoriteClient poetryUserFavoriteClient) {
        this.poetryAnswerRecordsClient = poetryAnswerRecordsClient;
        this.poetryUserFavoriteClient = poetryUserFavoriteClient;
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
        return poetryUserFavoriteClient.findFavoriteTop5();
    }
}
