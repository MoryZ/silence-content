package com.old.silence.bp.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.ContentInteractionAccumulationClient;
import com.old.silence.content.api.dto.ContentInteractionAccumulationCommand;
import com.old.silence.content.domain.enums.ContentInteractionRedisKey;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.job.common.client.dto.ExecuteResult;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author moryzang
 */
@Service
public class ContentInteractionSyncService {

    private static final String COUNT_FIELD = "count";
    private static final int BATCH_SIZE = 500;

    private final StringRedisTemplate stringRedisTemplate;
    private final ContentInteractionAccumulationClient contentInteractionAccumulationClient;

    public ContentInteractionSyncService(StringRedisTemplate stringRedisTemplate,
                                     ContentInteractionAccumulationClient contentInteractionAccumulationClient) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.contentInteractionAccumulationClient = contentInteractionAccumulationClient;
    }

    public ExecuteResult syncContent() {
        String dirtyKey = ContentInteractionRedisKey.DIRTY_SET.key(LocalDateTime.now().minusHours(1).withMinute(0).withSecond(0).withNano(0));
        String processingKey = ContentInteractionRedisKey.DIRTY_SET.key("processing");

        if (!stringRedisTemplate.hasKey(dirtyKey)) {
            return ExecuteResult.success("no dirty data: " + dirtyKey);
        }

        stringRedisTemplate.rename(dirtyKey, processingKey);
        Set<String> resourceIds = stringRedisTemplate.opsForSet().members(processingKey);
        if (resourceIds == null || resourceIds.isEmpty()) {
            stringRedisTemplate.delete(processingKey);
            return ExecuteResult.success("empty dirty set: " + processingKey);
        }

        int totalSynced = 0;
        List<String> resourceIdList = new ArrayList<>(new LinkedHashSet<>(resourceIds));
        for (int fromIndex = 0; fromIndex < resourceIdList.size(); fromIndex += BATCH_SIZE) {
            int toIndex = Math.min(fromIndex + BATCH_SIZE, resourceIdList.size());
            List<String> batchResourceIds = resourceIdList.subList(fromIndex, toIndex);
            List<ContentInteractionAccumulationCommand> batchCommands = new ArrayList<>();

            for (String resourceId : batchResourceIds) {
                batchCommands.addAll(readResourceDelta(resourceId));
            }

            if (batchCommands.isEmpty()) {
                continue;
            }

            int synced = contentInteractionAccumulationClient.batchUpsert(batchCommands);
            totalSynced += synced;
            clearAppliedDelta(batchCommands);
        }

        stringRedisTemplate.delete(processingKey);
        return ExecuteResult.success("synced=" + totalSynced);
    }

    private List<ContentInteractionAccumulationCommand> readResourceDelta(String resourceId) {
        List<ContentInteractionAccumulationCommand> commands = new ArrayList<>();
        for (InteractionType interactionType : List.of(InteractionType.LIKE, InteractionType.COLLECT, InteractionType.PREVIEW, InteractionType.SHARE)) {
            String countKey = ContentInteractionRedisKey.COUNT.key(interactionType, resourceId);
            HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
            Object rawValue = hashOperations.get(countKey, COUNT_FIELD);
            long delta = rawValue == null ? 0L : Long.parseLong(rawValue.toString());
            if (delta <= 0) {
                continue;
            }

            ContentInteractionAccumulationCommand command = new ContentInteractionAccumulationCommand();
            command.setResourceId(new BigInteger(resourceId));
            command.setResourceType(ResourceType.CONTENT);
            command.setInteractionType(interactionType);
            command.setAccumulation(BigInteger.valueOf(delta));
            commands.add(command);
        }
        return commands;
    }

    private void clearAppliedDelta(List<ContentInteractionAccumulationCommand> commands) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        for (ContentInteractionAccumulationCommand command : commands) {
            String countKey = ContentInteractionRedisKey.COUNT.key(command.getInteractionType(), command.getResourceId());
            hashOperations.increment(countKey, COUNT_FIELD, command.getAccumulation().negate().longValue());
        }
    }
}
