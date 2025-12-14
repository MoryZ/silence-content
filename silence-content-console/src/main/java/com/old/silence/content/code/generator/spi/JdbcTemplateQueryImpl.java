package com.old.silence.content.code.generator.spi;

import org.springframework.stereotype.Component;
import com.old.silence.content.api.CodeFileTemplateClient;
import com.old.silence.core.exception.ResourceNotFoundException;

@Component
public class JdbcTemplateQueryImpl implements TemplateQuery {

    private final CodeFileTemplateClient freemarkerTemplatesClient;

    public JdbcTemplateQueryImpl(CodeFileTemplateClient freemarkerTemplatesClient) {
        this.freemarkerTemplatesClient = freemarkerTemplatesClient;
    }

    @Override
    public CodeFileTemplateRecord load(String name) {
        return freemarkerTemplatesClient.findByTemplateName(name, CodeFileTemplateRecord.class)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
