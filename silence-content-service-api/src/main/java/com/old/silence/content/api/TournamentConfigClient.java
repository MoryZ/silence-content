package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * TournamentConfigFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "tournamentConfig", path = "/api/v1")
public interface TournamentConfigClient extends TournamentConfigService {
}