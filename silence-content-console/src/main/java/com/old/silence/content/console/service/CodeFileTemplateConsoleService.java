package com.old.silence.content.console.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.api.CodeFileTemplateClient;
import com.old.silence.content.code.generator.spi.CodeFileTemplateRecord;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * @author moryzang
 */
@Service
public class CodeFileTemplateConsoleService {

    private final CodeFileTemplateClient freemarkerTemplatesClient;

    public CodeFileTemplateConsoleService(CodeFileTemplateClient freemarkerTemplatesClient) {
        this.freemarkerTemplatesClient = freemarkerTemplatesClient;
    }

    public CodeFileTemplateRecord findByTemplateName(String name) {
        return freemarkerTemplatesClient.findByTemplateName(name, CodeFileTemplateRecord.class)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
