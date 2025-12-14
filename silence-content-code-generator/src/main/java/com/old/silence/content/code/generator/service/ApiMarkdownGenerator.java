package com.old.silence.content.code.generator.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.Parameter;
import com.old.silence.content.code.generator.model.ResponseInfo;

/**
 * API Markdown 文档生成器
 * 负责将 API 文档转换为 Markdown 格式
 * 
 * @author moryzang
 */
@Component
public class ApiMarkdownGenerator {

    /**
     * 生成 Markdown 格式的 API 文档
     */
    public void generateMarkdownDocument(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
        StringBuilder markdown = new StringBuilder();

        // 文档标题
        markdown.append("# API接口文档\n\n");
        String tableName = apiDocument.getTableName();
        if (tableName == null || tableName.trim().isEmpty()) {
            tableName = "未知表";
        }
        markdown.append("**数据表：** ").append(tableName).append("\n\n");

        // 接口列表
        markdown.append("## 接口列表\n\n");

        if (apiDocument.getEndpoints() != null) {
            int index = 1;
            for (Map.Entry<String, ApiEndpoint> entry : apiDocument.getEndpoints().entrySet()) {
                String endpointName = entry.getKey();
                ApiEndpoint endpoint = entry.getValue();

                markdown.append("### ").append(index++).append(". ").append(endpointName)
                        .append(endpoint.getPath()).append("\n\n");

                // 基础信息
                markdown.append("**请求方法：** `").append(endpoint.getMethod()).append("`  \n");
                markdown.append("**接口描述：** ").append(endpoint.getDescription()).append("\n\n");

                // 根据接口类型选择不同的展示策略
                markdown.append(generateEndpointContent(endpoint));
            }
        }

        String filename = tableName + ".md";
        saveDocumentToFile(markdown.toString(), apiDocOutputDir, filename);
    }

    /**
     * 生成单个端点的内容展示
     */
    private String generateEndpointContent(ApiEndpoint endpoint) {
        StringBuilder sb = new StringBuilder();
        String method = endpoint.getMethod().toUpperCase();

        // 请求参数部分
        if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
            sb.append(generateParametersSection(endpoint, method));
        }

        // 响应信息部分
        if (endpoint.getResponse() != null) {
            sb.append(generateResponseSection(endpoint, method));
        }

        return sb.toString();
    }

    /**
     * 生成请求参数部分
     */
    private String generateParametersSection(ApiEndpoint endpoint, String method) {
        StringBuilder sb = new StringBuilder();

        sb.append("#### 请求参数\n\n");

        // 根据方法类型决定默认展开状态
        boolean defaultOpen = isCreateOrUpdateMethod(method);

        sb.append("<details").append(defaultOpen ? " open" : "").append(">\n");
        sb.append("<summary>📋 参数列表（点击").append(defaultOpen ? "折叠" : "展开").append("）</summary>\n\n");
        sb.append("| 参数名 | 类型 | 是否必填 | 描述 | 示例 |\n");
        sb.append("|--------|------|----------|------|------|\n");

        for (Parameter param : endpoint.getParameters()) {
            sb.append("| ").append(escapeMarkdown(param.getName()))
                    .append(" | ").append(escapeMarkdown(param.getType()))
                    .append(" | ").append(param.isRequired() ? "是" : "否")
                    .append(" | ").append(escapeMarkdown(param.getDescription() != null ? param.getDescription() : ""))
                    .append(" | ").append(escapeMarkdown(param.getExample() != null ? param.getExample().toString() : ""))
                    .append(" |\n");
        }
        sb.append("</details>\n\n");

        // 请求源码（默认折叠）
        sb.append("<details>\n");
        sb.append("<summary>📄 请求源码（点击展开）</summary>\n\n");
        sb.append("```").append(getLanguageByMethod(endpoint.getMethod())).append("\n");
        sb.append(generateRequestExample(endpoint));
        sb.append("\n```\n");
        sb.append("</details>\n\n");

        return sb.toString();
    }

    /**
     * 生成响应信息部分
     */
    private String generateResponseSection(ApiEndpoint endpoint, String method) {
        StringBuilder sb = new StringBuilder();

        sb.append("#### 响应信息\n\n");
        sb.append("**状态码：** ").append(endpoint.getResponse().getCode()).append("  \n");
        sb.append("**提示信息：** ").append(endpoint.getResponse().getMessage()).append("  \n\n");

        // 查询接口默认展开出参示例，其他接口默认折叠
        boolean defaultOpen = isQueryMethod(method);

        sb.append("<details").append(defaultOpen ? " open" : "").append(">\n");
        sb.append("<summary>📄 响应示例（点击").append(defaultOpen ? "折叠" : "展开").append("）</summary>\n\n");
        sb.append("```json\n");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonResponse = mapper.writeValueAsString(endpoint.getResponse().getData());
            sb.append(jsonResponse);
        } catch (Exception e) {
            sb.append("无法序列化响应数据");
        }

        sb.append("\n```\n");
        sb.append("</details>\n\n");

        // 如果是查询接口，额外添加分页响应示例
        if (isQueryMethod(method) && isListEndpoint(endpoint)) {
            sb.append("<details>\n");
            sb.append("<summary>📄 分页响应示例（点击展开）</summary>\n\n");
            sb.append("```json\n");
            sb.append(generatePaginationResponseExample(endpoint.getResponse()));
            sb.append("\n```\n");
            sb.append("</details>\n\n");
        }

        return sb.toString();
    }

    /**
     * 生成分页响应示例
     */
    private String generatePaginationResponseExample(ResponseInfo<?> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, Object> paginationResponse = new LinkedHashMap<>();
            paginationResponse.put("code", response.getCode());
            paginationResponse.put("message", response.getMessage());

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("total", 100);
            data.put("pageNo", 1);
            data.put("pageSize", 20);
            data.put("totalPages", 5);

            // 复制原始响应数据作为列表项
            if (response.getData() instanceof Map) {
                List<Object> items = new ArrayList<>();
                items.add(response.getData());
                data.put("data", items);
            } else {
                data.put("data", Collections.singletonList(response.getData()));
            }

            paginationResponse.put("data", data);

            return mapper.writeValueAsString(paginationResponse);
        } catch (Exception e) {
            return "无法生成分页响应示例";
        }
    }

    /**
     * 生成请求示例代码
     */
    private String generateRequestExample(ApiEndpoint endpoint) {
        StringBuilder example = new StringBuilder();

        String method = endpoint.getMethod().toUpperCase();
        switch (method) {
            case "GET":
                example.append("GET ").append(endpoint.getPath());
                if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
                    List<String> queryParams = new ArrayList<>();
                    for (Parameter param : endpoint.getParameters()) {
                        if (isNotPathParameter(param.getName(), endpoint.getPath()) && param.getExample() != null) {
                            queryParams.add(param.getName() + "=" + param.getExample());
                        }
                    }
                    if (!queryParams.isEmpty()) {
                        example.append("?").append(String.join("&", queryParams));
                    }
                }
                example.append("\nContent-Type: application/json");
                break;

            case "POST":
            case "PUT":
                example.append(method).append(" ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json\n\n");

                try {
                    Map<String, Object> requestBody = new LinkedHashMap<>();
                    if (endpoint.getParameters() != null) {
                        for (Parameter param : endpoint.getParameters()) {
                            if (isNotPathParameter(param.getName(), endpoint.getPath()) && param.getExample() != null) {
                                requestBody.put(param.getName(), param.getExample());
                            }
                        }
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(SerializationFeature.INDENT_OUTPUT);
                    String jsonBody = mapper.writeValueAsString(requestBody);
                    example.append(jsonBody);
                } catch (Exception e) {
                    example.append("{}");
                }
                break;

            case "DELETE":
                example.append("DELETE ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json");
                break;

            default:
                example.append(method).append(" ").append(endpoint.getPath()).append("\n");
                example.append("Content-Type: application/json");
        }

        return example.toString();
    }

    /**
     * 判断是否为创建或更新接口
     */
    private boolean isCreateOrUpdateMethod(String method) {
        return "POST".equals(method) || "PUT".equals(method) || "GET".equals(method);
    }

    /**
     * 判断是否为查询接口
     */
    private boolean isQueryMethod(String method) {
        return "GET".equals(method);
    }

    /**
     * 判断是否为列表查询接口
     */
    private boolean isListEndpoint(ApiEndpoint endpoint) {
        return endpoint.getPath() != null &&
                (endpoint.getPath().contains("list") ||
                        endpoint.getPath().contains("query") ||
                        endpoint.getPath().matches(".*[/]$") ||
                        !endpoint.getPath().contains("{"));
    }

    /**
     * 判断是否为路径参数
     */
    private boolean isNotPathParameter(String paramName, String path) {
        return !path.contains("{" + paramName + "}");
    }

    /**
     * 获取语言标签
     */
    private String getLanguageByMethod(String method) {
        return switch (method.toUpperCase()) {
            case "GET", "POST", "PUT", "DELETE" -> "http";
            default -> "json";
        };
    }

    /**
     * 转义 Markdown 特殊字符
     */
    private String escapeMarkdown(String text) {
        if (text == null) return "";
        return text.replace("|", "\\|")
                .replace("*", "\\*")
                .replace("_", "\\_")
                .replace("`", "\\`")
                .replace("#", "\\#")
                .replace("-", "\\-")
                .replace("+", "\\+")
                .replace(".", "\\.")
                .replace("!", "\\!");
    }

    /**
     * 将文档保存到文件
     */
    private void saveDocumentToFile(String content, String apiDocOutputDir, String filename) throws Exception {
        File outputFile = new File(apiDocOutputDir, filename);
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
        }
    }
}
