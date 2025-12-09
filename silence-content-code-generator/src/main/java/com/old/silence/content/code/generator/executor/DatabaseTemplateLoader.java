package com.old.silence.content.code.generator.executor;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author moryzang
 */
public class DatabaseTemplateLoader implements TemplateLoader {

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    static class CacheEntry {
        String content;
        long timestamp;
        long lastModified;
    }

    @Override
    public Object findTemplateSource(String name) {
        CacheEntry entry = cache.get(name);
        // 缓存1分钟
        long cacheTimeout = 60000;
        if (entry != null &&
                (System.currentTimeMillis() - entry.timestamp) < cacheTimeout) {
            return entry;
        }

        // 从数据库重新加载
       /* String content = loadFromDatabase(name);*/
        entry = new CacheEntry();
       /* entry.content = content;*/
        entry.timestamp = System.currentTimeMillis();
      /*  entry.lastModified = getLastModifiedFromDB(name);*/
        cache.put(name, entry);

        return entry;
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((CacheEntry)templateSource).lastModified;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return null;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }
}
