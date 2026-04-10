package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * TournamentGroupRecordFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "tournamentGroupRecord", path = "/api/v1")
public interface TournamentGroupRecordClient extends TournamentGroupRecordService {
}
