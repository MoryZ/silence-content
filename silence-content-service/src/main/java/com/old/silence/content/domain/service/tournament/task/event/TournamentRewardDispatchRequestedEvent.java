package com.old.silence.content.domain.service.tournament.task.event;

import java.math.BigInteger;
import java.time.Instant;

/**
 * 赛事奖励发放请求事件
 */
public record TournamentRewardDispatchRequestedEvent(BigInteger tournamentId,
                                                     BigInteger eventGameId,
                                                     String runTraceId,
                                                     Instant occurredAt) {
}