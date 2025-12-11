package com.old.silence.content.code.generator.api;

import com.old.silence.content.code.generator.model.TableInfo;
import java.util.Map;

public interface CodeGenerator {

    String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName,
                          String templateName);

    String renderTemplate(TableInfo tableInfo, String basePackageName, String packageName,
                          String templateName, Map<String, Object> extras);

    void generateFile(TableInfo tableInfo, String outputDir, String basePackageName, String packageName,
                      String templateName, String suffix) throws Exception;
}
