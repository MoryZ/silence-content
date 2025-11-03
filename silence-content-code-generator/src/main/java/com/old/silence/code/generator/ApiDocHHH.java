package com.old.silence.code.generator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.ApiEndpoint;
import com.old.silence.code.generator.model.Parameter;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
public class ApiDocHHH {

    /**
     * 生成Markdown格式的API文档
     */
    public String generateMarkdownDocument(ApiDocument apiDocument) {
        StringBuilder markdown = new StringBuilder();

        // 文档标题
        markdown.append("# API接口文档\n\n");
        markdown.append("**数据表：** ").append(apiDocument.getTableName()).append("\n\n");

        // 规则说明
        if (apiDocument.getRules() != null && !apiDocument.getRules().isEmpty()) {
            markdown.append("## 通用规则\n\n");
            for (Map.Entry<String, Object> rule : apiDocument.getRules().entrySet()) {
                markdown.append("- **").append(rule.getKey()).append("**: ")
                        .append(rule.getValue()).append("\n");
            }
            markdown.append("\n");
        }

        // 接口列表
        markdown.append("## 接口列表\n\n");

        if (apiDocument.getEndpoints() != null) {
            int index = 1;
            for (Map.Entry<String, ApiEndpoint> entry : apiDocument.getEndpoints().entrySet()) {
                String endpointName = entry.getKey();
                ApiEndpoint endpoint = entry.getValue();

                markdown.append("### ").append(index++).append(". ").append(endpointName).append("\n\n");
                markdown.append(generateEndpointMarkdown(endpoint));
            }
        }

        return markdown.toString();
    }

    /**
     * 生成单个接口的Markdown文档
     */
    private String generateEndpointMarkdown(ApiEndpoint endpoint) {
        StringBuilder endpointDoc = new StringBuilder();

        // 基础信息
        endpointDoc.append("**请求方法：** `").append(endpoint.getMethod()).append("`  \n");
        endpointDoc.append("**请求路径：** `").append(endpoint.getPath()).append("`  \n");
        endpointDoc.append("**接口描述：** ").append(endpoint.getDescription()).append("\n\n");

        // 请求参数
        if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
            endpointDoc.append("#### 请求参数\n\n");
            endpointDoc.append("| 参数名 | 类型 | 是否必填 | 描述 | 示例 |\n");
            endpointDoc.append("|--------|------|----------|------|------|\n");

            for (Parameter param : endpoint.getParameters()) {
                endpointDoc.append("| ").append(param.getName())
                        .append(" | ").append(param.getType())
                        .append(" | ").append(param.isRequired() ? "是" : "否")
                        .append(" | ").append(param.getDescription() != null ? param.getDescription() : "")
                        .append(" | ").append(param.getExample() != null ? param.getExample() : "")
                        .append(" |\n");
            }
            endpointDoc.append("\n");
        }

        // 响应信息
        if (endpoint.getResponse() != null) {
            endpointDoc.append("#### 响应信息\n\n");
            endpointDoc.append("**状态码：** ").append(endpoint.getResponse().getCode()).append("  \n");
            endpointDoc.append("**提示信息：** ").append(endpoint.getResponse().getMessage()).append("  \n");

            if (endpoint.getResponse().getData() != null) {
                endpointDoc.append("**响应数据结构：**\n\n");
                try {
                    String jsonData = JacksonMapper.getSharedInstance().unwrap().writeValueAsString(endpoint.getResponse().getData());
                    endpointDoc.append("```json\n").append(jsonData).append("\n```\n\n");
                } catch (Exception e) {
                    endpointDoc.append("无法序列化响应数据\n\n");
                }
            }
        }

        // 元数据
        if (endpoint.getMetadata() != null && !endpoint.getMetadata().isEmpty()) {
            endpointDoc.append("#### 元数据\n\n");
            for (Map.Entry<String, Object> meta : endpoint.getMetadata().entrySet()) {
                endpointDoc.append("- **").append(meta.getKey()).append("**: ").append(meta.getValue()).append("\n");
            }
            endpointDoc.append("\n");
        }

        return endpointDoc.toString();
    }

    /**
     * 生成HTML格式的API文档
     */
    public String generateHtmlDocument(ApiDocument apiDocument) {
        StringBuilder html = new StringBuilder();

        html.append("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>API接口文档</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    h1 { color: #333; border-bottom: 2px solid #333; }
                    h2 { color: #666; margin-top: 30px; }
                    h3 { color: #888; }
                    table { border-collapse: collapse; width: 100%; margin: 20px 0; }
                    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
                    th { background-color: #f5f5f5; }
                    code { background-color: #f4f4f4; padding: 2px 4px; border-radius: 3px; }
                    pre { background-color: #f8f8f8; padding: 15px; border-radius: 5px; overflow-x: auto; }
                </style>
            </head>
            <body>
            """);

        html.append("<h1>API接口文档</h1>");
        html.append("<p><strong>数据表：</strong>").append(apiDocument.getTableName()).append("</p>");

        // 规则说明
        if (apiDocument.getRules() != null && !apiDocument.getRules().isEmpty()) {
            html.append("<h2>通用规则</h2><ul>");
            for (Map.Entry<String, Object> rule : apiDocument.getRules().entrySet()) {
                html.append("<li><strong>").append(rule.getKey()).append("</strong>: ")
                        .append(rule.getValue()).append("</li>");
            }
            html.append("</ul>");
        }

        // 接口列表
        html.append("<h2>接口列表</h2>");

        if (apiDocument.getEndpoints() != null) {
            int index = 1;
            for (Map.Entry<String, ApiEndpoint> entry : apiDocument.getEndpoints().entrySet()) {
                String endpointName = entry.getKey();
                ApiEndpoint endpoint = entry.getValue();

                html.append("<h3>").append(index++).append(". ").append(endpointName).append("</h3>");
                html.append(generateEndpointHtml(endpoint));
            }
        }

        html.append("</body></html>");
        return html.toString();
    }

    private String generateEndpointHtml(ApiEndpoint endpoint) {
        StringBuilder html = new StringBuilder();

        html.append("<p><strong>请求方法：</strong><code>").append(endpoint.getMethod()).append("</code></p>");
        html.append("<p><strong>请求路径：</strong><code>").append(endpoint.getPath()).append("</code></p>");
        html.append("<p><strong>接口描述：</strong>").append(endpoint.getDescription()).append("</p>");

        // 请求参数表格
        if (endpoint.getParameters() != null && !endpoint.getParameters().isEmpty()) {
            html.append("<h4>请求参数</h4><table><tr><th>参数名</th><th>类型</th><th>是否必填</th><th>描述</th><th>示例</th></tr>");

            for (Parameter param : endpoint.getParameters()) {
                html.append("<tr>")
                        .append("<td>").append(param.getName()).append("</td>")
                        .append("<td>").append(param.getType()).append("</td>")
                        .append("<td>").append(param.isRequired() ? "是" : "否").append("</td>")
                        .append("<td>").append(param.getDescription() != null ? param.getDescription() : "").append("</td>")
                        .append("<td>").append(param.getExample() != null ? param.getExample() : "").append("</td>")
                        .append("</tr>");
            }
            html.append("</table>");
        }

        // 响应信息
        if (endpoint.getResponse() != null) {
            html.append("<h4>响应信息</h4>");
            html.append("<p><strong>状态码：</strong>").append(endpoint.getResponse().getCode()).append("</p>");
            html.append("<p><strong>提示信息：</strong>").append(endpoint.getResponse().getMessage()).append("</p>");

            if (endpoint.getResponse().getData() != null) {
                html.append("<p><strong>响应数据结构：</strong></p>");
                try {
                    String jsonData = JacksonMapper.getSharedInstance().unwrap().writeValueAsString(endpoint.getResponse().getData());
                    html.append("<pre><code>").append(jsonData).append("</code></pre>");
                } catch (Exception e) {
                    html.append("<p>无法序列化响应数据</p>");
                }
            }
        }

        return html.toString();
    }

    /**
     * 将文档保存到文件
     */
    public void saveDocumentToFile(String content, String filename, boolean isHtml) throws Exception {
        String extension = isHtml ? ".html" : ".md";
        try (java.io.FileWriter writer = new java.io.FileWriter(filename + extension)) {
            writer.write(content);
        }
    }

    /**
     * 生成JSON格式的API文档（用于程序间交互）
     */
    public String generateJsonDocument(ApiDocument apiDocument) throws Exception {
        Map<String, Object> documentMap = new HashMap<>();
        documentMap.put("tableName", apiDocument.getTableName());
        documentMap.put("rules", apiDocument.getRules());
        documentMap.put("endpoints", apiDocument.getEndpoints());
        documentMap.put("generateTime", new Date());

        return JacksonMapper.getSharedInstance().unwrap().writeValueAsString(documentMap);
    }
}
