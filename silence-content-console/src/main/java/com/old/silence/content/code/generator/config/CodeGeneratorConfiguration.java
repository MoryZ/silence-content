package com.old.silence.content.code.generator.config;

import com.old.silence.content.code.generator.executor.DatabaseTemplateLoader;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.spi.TemplatesRepository;
import com.old.silence.content.code.generator.support.DataModelBuilder;
import com.old.silence.content.code.generator.support.FileOutputService;
import freemarker.cache.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeGeneratorConfiguration {

    @Bean
    public TemplateLoader databaseTemplateLoader(TemplatesRepository templatesRepository) {
        return new DatabaseTemplateLoader(templatesRepository);
    }

    @Bean
    public DataModelBuilder dataModelBuilder() {
        return new DataModelBuilder();
    }

    @Bean
    public FileOutputService fileOutputService() {
        return new FileOutputService();
    }

    @Bean
    public CodeGenerator codeGenerator(TemplateLoader databaseTemplateLoader,
                                       DataModelBuilder dataModelBuilder,
                                       FileOutputService fileOutputService) {
        return new SpringCodeGenerator(databaseTemplateLoader, dataModelBuilder, fileOutputService);
    }
}
