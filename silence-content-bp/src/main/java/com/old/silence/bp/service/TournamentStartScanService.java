	
package com.old.silence.bp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.old.silence.content.api.TournamentConfigDomainClient;
import com.old.silence.content.api.TournamentTaskDomainClient;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigQuery;
import com.old.silence.data.commons.domain.BigIdOnlyView;

import java.math.BigInteger;
import java.time.Instant;

@Service
public class TournamentStartScanService {
    private static final int BATCH_SIZE = 100;
    private static final Logger logger = LoggerFactory.getLogger(TournamentStartScanService.class);
    private final TournamentConfigDomainClient tournamentConfigDomainClient;
    private final TournamentTaskDomainClient tournamentTaskDomainClient;

    public TournamentStartScanService(TournamentConfigDomainClient tournamentConfigDomainClient,
                                      TournamentTaskDomainClient tournamentTaskDomainClient) {
        this.tournamentConfigDomainClient = tournamentConfigDomainClient;
        this.tournamentTaskDomainClient = tournamentTaskDomainClient;
    }

    public void scanTournamentJobStart() {
        var now = Instant.now();
        var tournamentConfigQuery = new TournamentConfigQuery();
        tournamentConfigQuery.setTournamentStartTimeFrom(now);

        var pageRequest = PageRequest.ofSize(BATCH_SIZE);
        var page = tournamentConfigDomainClient.query(tournamentConfigQuery, pageRequest, BigIdOnlyView.class);

        if (page.isEmpty()) {
            logger.info("无初始化任务");
            return ;
        }

        long totalProcessed = 0;
        long successCount = 0;
        long failCount = 0;
        while (page.hasContent()) {
            var tournamentConfigs = page.getContent();
            for (BigIdOnlyView tournamentConfigView : tournamentConfigs) {
                try {
                    tournamentTaskDomainClient.initSettlementTasks(tournamentConfigView.getId());
                    successCount++;
                } catch (Exception e) {
                    failCount++;
                    logger.error("处理初始化task任务失败，赛事ID:{}，错误:{}", tournamentConfigView.getId(), e.getMessage());
                } finally {
                    totalProcessed++;
                }
            }
            logger.info("初始化本页task任务结束 successCount:{},failCount:{}", successCount, failCount );

            if (!page.hasNext()) {
                break;
            }
            page = tournamentConfigDomainClient.query(tournamentConfigQuery, pageRequest.next(), BigIdOnlyView.class);
        }

        logger.info("初始化本页task任务结束 totalProcessed:{}", totalProcessed );

    }

}
