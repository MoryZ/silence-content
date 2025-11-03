package com.old.silence.code.generator.service;

import org.springframework.stereotype.Service;

import com.old.silence.code.generator.ApiDocHHH;
import com.old.silence.code.generator.model.ApiDocument;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author moryzang
 */
@Service
public class ApiDocumentGeneratorService {


    /**
     *  生成接口文档
     * @param apiDocument 接口文档
     * @param apiDocOutputDir 接口文档输出路径
     */
    public void generateApiDocs(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
        var tableName = apiDocument.getTableName();
        ApiDocHHH apiDocHHH = new ApiDocHHH();

        // 生成Markdown文档
        String markdown = apiDocHHH.generateMarkdownDocument(apiDocument);
        generateApiDocToFile(markdown, tableName, ".md", apiDocOutputDir);

        // 生成HTML文档
        String html = apiDocHHH.generateHtmlDocument(apiDocument);

        generateApiDocToFile(html, tableName, ".html", apiDocOutputDir);

        // 生成JSON文档
        String json = apiDocHHH.generateJsonDocument(apiDocument);

        generateApiDocToFile(json, tableName, ".json", apiDocOutputDir);

    }

    private void generateApiDocToFile(String content, String tableName, String suffix, String apiDocOutputDir) throws IOException {
        File outputFile = new File(apiDocOutputDir, tableName + suffix);
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
        }
    }
}
