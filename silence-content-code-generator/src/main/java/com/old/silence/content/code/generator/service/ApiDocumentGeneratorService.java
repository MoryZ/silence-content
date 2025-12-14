package com.old.silence.content.code.generator.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

/**
 * API 文档生成服务（协调器）
 * 负责编排各个生成器，根据表结构生成完整的 API 文档
 * 
 * <p>职责：
 * <ul>
 *   <li>协调各个生成器完成文档生成流程</li>
 *   <li>生成 API 文档对象（包含端点定义）</li>
 *   <li>生成 Markdown 格式的文档文件</li>
 * </ul>
 * 
 * @author moryzang
 */
@Service
public class ApiDocumentGeneratorService {

    private final ApiEndpointGenerator endpointGenerator;
    private final ApiMarkdownGenerator markdownGenerator;

    public ApiDocumentGeneratorService(ApiEndpointGenerator endpointGenerator,
                                       ApiMarkdownGenerator markdownGenerator) {
        this.endpointGenerator = endpointGenerator;
        this.markdownGenerator = markdownGenerator;
    }

    /**
     * 生成 API 文档对象
     */
    public ApiDocument generateDocument(TableInfo tableInfo) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());
        document.setEndpoints(endpointGenerator.generateEndpoints(tableInfo));
        document.setTableInfo(tableInfo);
        return document;
    }

    /**
     * 生成 Markdown 格式的 API 文档
     */
    public void generateApiDocs(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
        markdownGenerator.generateMarkdownDocument(apiDocument, apiDocOutputDir);
    }
}
