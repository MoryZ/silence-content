package com.old.silence.code.generator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

import com.old.silence.code.generator.executor.SpringCodeGenerator;
import com.old.silence.code.generator.executor.SQLAnalyzer;
import com.old.silence.code.generator.service.ApiDocumentGeneratorService;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.service.RuleProcessorService;
import com.old.silence.code.generator.service.SpringCodeGeneratorService;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class GeneratorResource {

    private static final Logger log = LoggerFactory.getLogger(GeneratorResource.class);

    private final ApiDocumentGeneratorService apiDocumentGeneratorService;
    private final SpringCodeGeneratorService springCodeGeneratorService;
    private final RuleProcessorService ruleProcessorService;

    public GeneratorResource(ApiDocumentGeneratorService apiDocumentGeneratorService,
                             SpringCodeGeneratorService springCodeGeneratorService,
                             RuleProcessorService ruleProcessorService) {
        this.apiDocumentGeneratorService = apiDocumentGeneratorService;
        this.springCodeGeneratorService = springCodeGeneratorService;
        this.ruleProcessorService = ruleProcessorService;
    }

    @PostMapping("/generate")
    public String generateCode() {
        // 创建配置
        GeneratorConfig config = createConfig();

        // 运行生成器
        generateAPI(config);

        return "OK";
    }

    /**
     * 创建配置,后续从参数中读取
     */
    private static GeneratorConfig createConfig() {
        GeneratorConfig config = new GeneratorConfig();
        config.setDbUrl("jdbc:mysql://localhost:3306/silence-content");
        config.setUsername("root");
        config.setPassword("admin123456");
        config.setPersistencePackage("jakarta");
        config.setUseLombok(false);
        config.setBasePackage("com.old.silence.content");

        config.setServiceOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");
        config.setConsoleOutputDir("silence-content-console/src/main/java");
        config.setApiDocOutputDir("silence-content-api-docs");
        return config;
    }

    /**
     * 生成的主方法
     */
    public void generateAPI(GeneratorConfig config) {
        try {
            // 1.初始化sql 分析器
            SQLAnalyzer analyzer = new SQLAnalyzer(config.getDbUrl(), config.getUsername(), config.getPassword());

            // 获取要生成的表
            log.info("🚀 开始生成API，共 {} 张表", + CollectionUtils.size(analyzer.getTablesWithComments()));

            analyzer.getTablesWithComments().forEach((tableName, tableComment) -> {
                try {
                    doGenerate(analyzer, tableName, config);
                } catch (SQLException e) {
                    log.info("🚀 生成服务运行失败：，原因：{}", e.getMessage());
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                log.info("🚀 生成服务运行成功," + "表名：{} 注释:{}", tableComment , tableComment);
            });

        } catch (Exception e) {
            System.err.println("❌ API生成失败: " + e.getMessage());
        }
    }

    /**
     * 为所有表生成代码
     */
    private void doGenerate(SQLAnalyzer analyzer, String tableName, GeneratorConfig config) throws Exception {
        try {
            log.info("\n📋 处理表: {}" ,tableName);

            // 1.分析表结构
            TableInfo tableInfo = analyzer.analyzeTable(tableName);
            log.info("   ✅ 表结构分析完成，共 {} 个字段", tableInfo.getColumnInfos().size());


            // 根据规则转化数据库字段到 java类型字段
            tableInfo.getColumnInfos().forEach(ruleProcessorService::convertToJavaField);

            // 2. 生成接口文档
            ApiDocument apiDoc = apiDocumentGeneratorService.generateDocument(tableInfo);

            // 生成markdown文件
            apiDocumentGeneratorService.generateApiDocs(apiDoc, config.getApiDocOutputDir());

            log.info("   ✅ 接口文档生成完成，共  {} 个接口",  apiDoc.getEndpoints().size());

            /*// 3.初始化 代码生成器
            SpringCodeGenerator codeGenerator = new SpringCodeGenerator(
                    config.getPersistencePackage(), config.getUseLombok());
            // 4.生成代码
            springCodeGeneratorService.generateCode(codeGenerator, tableInfo, apiDoc, config);*/

            log.info("   ✅ 代码生成完成");

        } catch (Exception e) {
            log.error("   ❌ 处理表 :{} 时出错: {}" , tableName, e.getMessage());
        }
    }


}
