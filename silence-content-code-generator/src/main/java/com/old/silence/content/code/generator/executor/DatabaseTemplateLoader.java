package com.old.silence.content.code.generator.executor;

import freemarker.cache.TemplateLoader;

import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.old.silence.content.code.generator.spi.CodeFileTemplateRecord;
import com.old.silence.content.code.generator.spi.TemplateQuery;
import com.old.silence.content.code.generator.model.CacheEntry;

/**
 * @author moryzang
 */
public class DatabaseTemplateLoader implements TemplateLoader {

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final TemplateQuery templatesRepository;

    public DatabaseTemplateLoader(TemplateQuery templatesRepository) {
        this.templatesRepository = templatesRepository;
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

        // 从外部仓库（例如数据库）重新加载
        CodeFileTemplateRecord resource = templatesRepository.load(name);
        if (resource != null) {
            entry = new CacheEntry(resource.content(), System.currentTimeMillis(),
                convertWithJodaTime(resource.updatedDate()));
            cache.put(name, entry);
        }

        return entry;
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((CacheEntry) templateSource).lastModified();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) {
        CacheEntry entry = (CacheEntry) templateSource;
        return new StringReader(entry.content());
    }

    @Override
    public void closeTemplateSource(Object templateSource) {

    }

    private static long convertWithJodaTime(String dateTimeStr) {
        DateTimeFormatter formatter =
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = formatter.parseDateTime(dateTimeStr);
        return dateTime.getMillis();
    }
}
