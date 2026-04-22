package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.old.silence.content.api.tournament.tournament.service.TournamentTaskService;

/**
 * TournamentTask Feign客户端
 */
@FeignClient(name =  ContentContextUtils.APPLICATION_NAME, contextId = "tournament-task", path = "/api/v1")
public interface TournamentTaskDomainClient extends TournamentTaskService {
}
