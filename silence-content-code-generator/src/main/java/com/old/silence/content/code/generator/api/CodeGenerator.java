package com.old.silence.content.code.generator.api;

import com.old.silence.content.code.generator.model.TableInfo;
import java.util.Map;

public interface CodeGenerator {


    String renderTemplate(TableInfo tableInfo, String owner, String basePackageName, String packageName,
                          String templateName, Map<String, Object> extras);

    /**
     *
     * @param tableInfo 表信息
     * @param owner 作者
     * @param outputDir 文件输出目录
     * @param basePackageName 基础包名
     * @param packageName 文件包名
     * @param templateName 模板文件名
     * @param suffix 后缀
     * @param extras 额外配置信息
     * @throws Exception 抛出异常
     */
    void generateFile(TableInfo tableInfo, String owner, String outputDir, String basePackageName, String packageName,
                      String templateName, String suffix, Map<String, Object> extras) throws Exception;
}
