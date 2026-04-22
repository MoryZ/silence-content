package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.old.silence.content.api.tournament.tournament.service.TournamentGroupService;

/**
 * TournamentGroupFeign客户端
 */
@FeignClient(name =  ContentContextUtils.APPLICATION_NAME, contextId = "tournament-group", path = "/api/v1")
public interface TournamentGroupClient extends TournamentGroupService {
}
