package com.old.silence.content.code.generator.model;

/**
 * @author moryzang
 */
public record CacheEntry(
        String content,
        long timestamp,
        long lastModified
) {

}
