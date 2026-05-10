package com.old.silence.content.domain.enums;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 内容互动 Redis Key 规范
 *
 * 统一由 StringRedisTemplate / opsForHash / opsForSet / opsForValue 直接操作，
 * 不再引入 Redis DAO 层。
 */
public enum ContentInteractionRedisKey {

    /**
     * Hash: interact:count:{type}:{resId}
     */
    COUNT("interact:count:%s:%s"),

    /**
     * Bitmap: interact:stat:{type}:{resId}
     */
    STATUS("interact:stat:%s:%s"),

    /**
     * Set: interact:dirty_set:{yyyyMMddHH}
     */
    DIRTY_SET("interact:dirty_set:%s"),

    /**
     * 临时防重标记: interact:temp:{type}:{userId}:{resId}
     */
    TEMP("interact:temp:%s:%s:%s");

    private static final DateTimeFormatter HOURLY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");

    private final String pattern;

    ContentInteractionRedisKey(String pattern) {
        this.pattern = pattern;
    }

    public String key(InteractionType interactionType, Object resourceId) {
        return String.format(pattern, interactionType.name().toLowerCase(), resourceId);
    }

    public String key(InteractionType interactionType, Object userId, Object resourceId) {
        return String.format(pattern, interactionType.name().toLowerCase(), userId, resourceId);
    }

    public String key(LocalDateTime dateTime) {
        return String.format(pattern, dateTime.format(HOURLY_FORMATTER));
    }

    public String key(String suffix) {
        return String.format(pattern, suffix);
    }
}