package com.old.silence.content.api;


import org.springframework.cloud.openfeign.FeignClient;
import com.old.silence.content.api.tournament.tournament.service.TournamentScoreRecordService;

@FeignClient(name =  ContentContextUtils.APPLICATION_NAME, contextId = "tournament-score-record", path = "/api/v1")
public interface TournamentScoreRecordClient extends TournamentScoreRecordService {
}
