package com.old.silence.content.code.generator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.strategy.CodeGenerationStrategy.CodeLayer;

/**
 * 默认模板检索器，从 classpath 下的 FreeMarker 模板中加载引用片段。
 */
@Component
public class ClassPathTemplateRetriever implements TemplateRetriever {

    private static final Logger log = LoggerFactory.getLogger(ClassPathTemplateRetriever.class);
    private static final String TEMPLATE_BASE_PATH = "classpath:/templates/";

    private final ResourceLoader resourceLoader;
    private final boolean hintsEnabled;
    private final int maxPreviewLines;
    private final Map<CodeLayer, List<String>> layerTemplateMapping;

    public ClassPathTemplateRetriever(ResourceLoader resourceLoader,
                                      @Value("${code-generator.prompt.hints.enabled:true}") boolean hintsEnabled,
                                      @Value("${code-generator.prompt.hints.max-lines:40}") int maxPreviewLines) {
        this.resourceLoader = resourceLoader;
        this.hintsEnabled = hintsEnabled;
        this.maxPreviewLines = Math.max(1, maxPreviewLines);
        this.layerTemplateMapping = buildLayerTemplateMapping();
    }

    @Override
    public String retrieveHints(CodeLayer layer, TableInfo tableInfo, ApiDocument apiDoc) {
        if (!hintsEnabled || layer == null) {
            return "";
        }

        List<String> templates = layerTemplateMapping.getOrDefault(layer, Collections.emptyList());
        if (templates.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("/* 模板参考（来自 FreeMarker 模板，仅供遵循结构）：\n");

        templates.forEach(templateName -> {
            String snippet = loadTemplateSnippet(templateName);
            if (StringUtils.isNotBlank(snippet)) {
                builder.append("--- ").append(templateName).append(" ---\n")
                        .append(snippet).append("\n\n");
            }
        });

        builder.append("*/\n");
        return builder.toString();
    }

    private Map<CodeLayer, List<String>> buildLayerTemplateMapping() {
        Map<CodeLayer, List<String>> mapping = new EnumMap<>(CodeLayer.class);
        mapping.put(CodeLayer.CONSOLE, List.of(
                "consoleResource.ftl",
                "consoleCommand.ftl",
                "consoleQuery.ftl",
                "consoleView.ftl",
                "commandMapper.ftl",
                "queryMapper.ftl"
        ));
        mapping.put(CodeLayer.SERVICE_API, List.of(
                "service.ftl",
                "client.ftl",
                "command.ftl",
                "query.ftl",
                "view.ftl"
        ));
        mapping.put(CodeLayer.SERVICE, List.of(
                "resource.ftl",
                "mapper.ftl",
                "repository.ftl",
                "repository-impl.ftl",
                "dao.ftl",
                "model.ftl"
        ));
        mapping.put(CodeLayer.ENUM, List.of("enum.ftl"));
        return Collections.unmodifiableMap(mapping);
    }

    private String loadTemplateSnippet(String templateName) {
        Resource resource = resourceLoader.getResource(TEMPLATE_BASE_PATH + templateName);
        if (!resource.exists()) {
            log.warn("模板文件不存在: {}", templateName);
            return "";
        }

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            return reader.lines()
                    .limit(maxPreviewLines)
                    .collect(Collectors.joining("\n"));

        } catch (IOException e) {
            log.error("读取模板文件失败: {}", templateName, e);
            return "";
        }
    }
}
