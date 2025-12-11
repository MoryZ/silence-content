package com.old.silence.content.code.generator.facade;

import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.engine.TemplateEngine;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.support.DataModelBuilder;
import com.old.silence.content.code.generator.support.FileOutputService;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

public class CodeGeneratorFacade implements CodeGenerator {
    private final TemplateEngine engine;
    private final DataModelBuilder dataModelBuilder;
    private final FileOutputService fileOutputService;

    public CodeGeneratorFacade(TemplateEngine engine,
                               DataModelBuilder dataModelBuilder,
                               FileOutputService fileOutputService) {
        this.engine = engine;
        this.dataModelBuilder = dataModelBuilder;
        this.fileOutputService = fileOutputService;
    }

    public static CodeGenerator ofDefault() {
        return new CodeGeneratorFacade(
                TemplateEngine.of(),
                new DataModelBuilder(),
                new FileOutputService()
        );
    }

    public static CodeGenerator ofLoaders(TemplateLoader... loaders) {
        return new CodeGeneratorFacade(
                TemplateEngine.of(loaders),
                new DataModelBuilder(),
                new FileOutputService()
        );
    }

    @Override
    public String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName, String templateName) {
        return renderTemplate(tableInfo, basePackageName, packageName, templateName, null);
    }

    @Override
    public String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName,
                                 String templateName, Map<String, Object> extras) {
        Map<String, Object> dataModel = dataModelBuilder.build(tableInfo, basePackageName, packageName, extras);
        Template template = engine.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        try {
            template.process(dataModel, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    @Override
    public void generateFile(TableInfo tableInfo, String outputDir, String basePackageName, String packageName,
                             String templateName, String suffix) throws Exception {
        String content = renderTemplate(tableInfo, basePackageName, packageName, templateName, null);
        String fileName = fileOutputService.fileName(tableInfo, suffix);
        File outputFile = new File(outputDir, fileName);
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
        }
    }
}
