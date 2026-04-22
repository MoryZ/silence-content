package com.old.silence.content.api.tournament.tournament.service;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.tournament.tournament.vo.TournamentScoreRecordView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;

public interface TournamentScoreRecordService {

    @GetMapping(value = "/tournament/scoreRecord/globalScoreList", params = {"pageNo", "pageSize"})
    <T> List<T> queryGlobalScoreList(@RequestParam BigInteger eventGameId,
                                     @RequestParam BigInteger id, Pageable pageable,
                      @ProjectedPayloadType(TournamentScoreRecordView.class) Class<T> projectionType);

    @PostMapping(value = "/tournament/scoreRecord/calculate")
    void calculateTournamentScoreByEventGameId(@RequestParam BigInteger eventGameId);
}
