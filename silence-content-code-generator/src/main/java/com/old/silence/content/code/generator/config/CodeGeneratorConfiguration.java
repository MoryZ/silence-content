package com.old.silence.content.code.generator.config;

import com.old.silence.content.code.generator.executor.DatabaseTemplateLoader;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.spi.TemplateQuery;
import com.old.silence.content.code.generator.support.DataModelBuilder;
import com.old.silence.content.code.generator.support.FileOutputService;
import freemarker.cache.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码生成器配置 - code-generator 模块独立配置
 * 
 * 设计原则：
 * 1. 此配置类仅依赖 code-generator 模块内的类
 * 2. 不依赖任何外部 FeignClient，使用 SPI 模式解耦
 * 3. 默认提供 NoOpTemplateQuery，上层模块可以注册自己的实现
 * 4. 通过 ObjectProvider 注入，支持可选的 TemplateQuery 覆盖
 * 
 * @author moryzang
 */
@Configuration
public class CodeGeneratorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CodeGeneratorConfiguration.class);


    /**
     * DatabaseTemplateLoader bean
     * 使用注入的 TemplateQuery（可能是默认的或被覆盖的）
     */
    @Bean
    public TemplateLoader databaseTemplateLoader(TemplateQuery templateQuery) {
        return new DatabaseTemplateLoader(templateQuery);
    }

    @Bean
    public DataModelBuilder dataModelBuilder() {
        return new DataModelBuilder();
    }

    @Bean
    public FileOutputService fileOutputService() {
        return new FileOutputService();
    }

    /**
     * CodeGenerator bean
     */
    @Bean
    public CodeGenerator codeGenerator(TemplateLoader databaseTemplateLoader,
                                       DataModelBuilder dataModelBuilder,
                                       FileOutputService fileOutputService) {
        return new SpringCodeGenerator(databaseTemplateLoader, dataModelBuilder, fileOutputService);
    }
}
