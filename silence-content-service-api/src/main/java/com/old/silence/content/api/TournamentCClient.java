package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "tournamentC", path = "/api/v1")
public interface TournamentCClient extends TournamentCService {
}
