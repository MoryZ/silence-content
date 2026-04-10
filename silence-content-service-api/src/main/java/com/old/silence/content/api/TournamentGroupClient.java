package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * TournamentGroupFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "tournamentGroup", path = "/api/v1")
public interface TournamentGroupClient extends TournamentGroupService {
}
