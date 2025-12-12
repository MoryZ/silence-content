package com.old.silence.content.code.generator.spi;

import org.springframework.stereotype.Component;
import com.old.silence.content.api.FreemarkerTemplatesClient;
import com.old.silence.core.exception.ResourceNotFoundException;

@Component
public class JdbcTemplatesRepository implements TemplatesRepository {

    private final FreemarkerTemplatesClient freemarkerTemplatesClient;

    public JdbcTemplatesRepository(FreemarkerTemplatesClient freemarkerTemplatesClient) {
        this.freemarkerTemplatesClient = freemarkerTemplatesClient;
    }

    @Override
    public TemplateResource load(String name) {
        return freemarkerTemplatesClient.findByTemplateName(name, TemplateResource.class)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
