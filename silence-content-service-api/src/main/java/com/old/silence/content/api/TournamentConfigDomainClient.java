package com.old.silence.content.api;


import org.springframework.cloud.openfeign.FeignClient;
import com.old.silence.content.api.tournament.tournament.service.TournamentConfigService;

@FeignClient(name =  ContentContextUtils.APPLICATION_NAME, contextId = "tournament-config", path = "/api/v1")
public interface TournamentConfigDomainClient extends TournamentConfigService {

}
