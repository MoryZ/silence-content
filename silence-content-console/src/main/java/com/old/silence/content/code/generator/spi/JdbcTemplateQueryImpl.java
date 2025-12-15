package com.old.silence.content.code.generator.spi;

import org.springframework.stereotype.Component;
import com.old.silence.content.api.CodeFileTemplateClient;
import com.old.silence.content.console.service.CodeFileTemplateConsoleService;
import com.old.silence.core.exception.ResourceNotFoundException;

@Component
public class JdbcTemplateQueryImpl implements TemplateQuery {

    private final CodeFileTemplateConsoleService codeFileTemplateConsoleService;

    public JdbcTemplateQueryImpl(CodeFileTemplateConsoleService codeFileTemplateConsoleService) {
        this.codeFileTemplateConsoleService = codeFileTemplateConsoleService;
    }

    @Override
    public CodeFileTemplateRecord load(String name) {
        return codeFileTemplateConsoleService.findByTemplateName(name);
    }
}
