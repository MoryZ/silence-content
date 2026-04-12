package com.old.silence.content.infrastructure.mq.producer.message;

import java.math.BigInteger;
import java.time.Instant;

/**
 * 赛事结算完成后的发奖消息体。
 */
public record TournamentRewardDispatchMessage(BigInteger tournamentId,
                                              BigInteger eventGameId,
                                              String runTraceId,
                                              Instant occurredAt,
                                              String source) {
}