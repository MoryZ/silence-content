package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.old.silence.content.api.tournament.tournament.service.TournamentParticipantService;

@FeignClient(name =  ContentContextUtils.APPLICATION_NAME, contextId = "tournament-participant", path = "/api/v1")
public interface TournamentParticipantClient extends TournamentParticipantService {
}
