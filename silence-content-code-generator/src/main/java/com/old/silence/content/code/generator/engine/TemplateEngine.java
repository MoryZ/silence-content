package com.old.silence.content.code.generator.engine;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
public class TemplateEngine {
    private final Configuration configuration;

    public TemplateEngine(Configuration configuration) {
        this.configuration = configuration;
    }

    public static TemplateEngine of(TemplateLoader... loaders) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(TemplateEngine.class, "/templates");
        TemplateLoader classpath = cfg.getTemplateLoader();
        TemplateLoader[] all = loaders != null && loaders.length > 0
                ? concat(loaders, classpath)
                : new TemplateLoader[]{classpath};
        cfg.setTemplateLoader(new MultiTemplateLoader(all));
        return new TemplateEngine(cfg);
    }

    private static TemplateLoader[] concat(TemplateLoader[] loaders, TemplateLoader extra) {
        TemplateLoader[] arr = new TemplateLoader[loaders.length + 1];
        System.arraycopy(loaders, 0, arr, 0, loaders.length);
        arr[loaders.length] = extra;
        return arr;
    }

    public Template getTemplate(String name) {
        try {
            return configuration.getTemplate(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
