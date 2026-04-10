package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * TournamentTask Feign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "tournamentTask", path = "/api/v1")
public interface TournamentTaskClient extends TournamentTaskService {
}
