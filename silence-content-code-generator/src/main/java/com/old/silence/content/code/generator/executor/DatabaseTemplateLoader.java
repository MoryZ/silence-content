package com.old.silence.content.code.generator.executor;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import com.old.silence.content.code.generator.infrastructure.persistent.dao.FreemarkerTemplatesDao;
import com.old.silence.content.code.generator.infrastructure.persistent.dao.support.FreemarkerTemplatesResult;
import com.old.silence.content.code.generator.model.CacheEntry;

/**
 * @author moryzang
 */
public class DatabaseTemplateLoader implements TemplateLoader {

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    private final FreemarkerTemplatesDao freemarkerTemplatesDao;

    public DatabaseTemplateLoader(FreemarkerTemplatesDao freemarkerTemplatesDao) {
        this.freemarkerTemplatesDao = freemarkerTemplatesDao;
    }

    @Override
    public Object findTemplateSource(String name) {
        CacheEntry entry = cache.get(name);
        // 缓存1分钟
        long cacheTimeout = 60000;
        if (entry != null &&
                (System.currentTimeMillis() - entry.lastModified()) < cacheTimeout) {
            return entry;
        }

        // 从数据库重新加载
        FreemarkerTemplatesResult freemarkerTemplatesResult = freemarkerTemplatesDao.loadFromDatabase(name);
        if (freemarkerTemplatesResult != null) {
            entry = new CacheEntry(freemarkerTemplatesResult.getContent(), System.currentTimeMillis(),
                    convertWithJodaTime(freemarkerTemplatesResult.getUpdatedDate()));
            cache.put(name, entry);
        }

        return entry;
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((CacheEntry) templateSource).lastModified();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return null;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }

    private static long convertWithJodaTime(String dateTimeStr) {
        org.joda.time.format.DateTimeFormatter formatter =
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = formatter.parseDateTime(dateTimeStr);
        return dateTime.getMillis();
    }
}
