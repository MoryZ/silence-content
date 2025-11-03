package com.old.silence.code.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.old.silence.code.generator.analyzer.SQLAnalyzer;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.ColumnInfo;
import com.old.silence.code.generator.model.Rule;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.rule.ConfigurableRule;
import com.old.silence.code.generator.rule.ExpressionRuleEngine;
import com.old.silence.code.generator.rule.JsonRuleLoader;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
public class APIGeneratorMain {

    public static void main(String[] args) {
        // 创建配置
        GeneratorConfig config = createConfig();

        // 运行生成器
        generateAPI(config);
    }

    /**
     * 创建配置
     */
    private static GeneratorConfig createConfig() {
        GeneratorConfig config = new GeneratorConfig();
        config.setDbUrl("jdbc:mysql://localhost:3306/silence-content");
        config.setUsername("root");
        config.setPassword("admin123456");
        config.setPersistencePackage("jakarta");
        config.setUseLombok(false);
        config.setBasePackage("com.old.silence");

        config.setServiceOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");
        config.setConsoleOutputDir("silence-content-console/src/main/java");
        config.setApiDocOutputDir("silence-content-api-doc");

        config.setRulesConfigPath("rules/rules.json");
        return config;
    }

    /**
     * 生成API的主方法
     */
    public static void generateAPI(GeneratorConfig config) {
        try {
            // 初始化组件
            SQLAnalyzer analyzer = new SQLAnalyzer(config.getDbUrl(), config.getUsername(), config.getPassword());
            ApiDocumentGenerator docGenerator = new ApiDocumentGenerator();
            SpringCodeGenerator codeGenerator = new SpringCodeGenerator(
                    config.getPersistencePackage(), config.getUseLombok());

            //创建创建规则引擎
            ExpressionRuleEngine expressionRuleEngine = new ExpressionRuleEngine();

            // 加载规则配置
            loadRules(expressionRuleEngine, config.getRulesConfigPath());

            // 获取要生成的表
            System.out.println("🚀 开始生成API，共 " + CollectionUtils.size(analyzer.getTablesWithComments()) + " 张表");

            analyzer.getTablesWithComments().forEach((tableName, tableComment) -> {
                // 生成代码
                generateForTables(analyzer, docGenerator, codeGenerator, expressionRuleEngine, tableName, config);
                System.out.println("🚀 代码生成成功," + "表名：" + tableName + "注释:" + tableComment);
            });




        } catch (Exception e) {
            System.err.println("❌ API生成失败: " + e.getMessage());
        }
    }

    /**
     * 加载规则配置
     */
    private static void loadRules(ExpressionRuleEngine expressionRuleEngine, String rulesConfigPath) {
        try {
            // 创建规则加载器
            JsonRuleLoader ruleLoader = new JsonRuleLoader();

            // 从文件加载规则配置
            List<ConfigurableRule> defaultRules = ruleLoader.loadRules(rulesConfigPath);

            // 将规则添加到引擎
            expressionRuleEngine.addRules(defaultRules);
            System.out.println("✅ 规则配置加载成功");
        } catch (Exception e) {
            System.out.println("⚠️  规则配置文件加载失败，使用默认规则: " + e.getMessage());
        }
    }

    /**
     * 为所有表生成代码
     */
    private static void generateForTables(SQLAnalyzer analyzer,
                                          ApiDocumentGenerator docGenerator,
                                          SpringCodeGenerator codeGenerator,
                                          ExpressionRuleEngine expressionRuleEngine,
                                          String tableName,
                                          GeneratorConfig config) {

        System.out.println("\n📋 处理表: " + tableName);

        try {
            // 1.分析表结构
            TableInfo tableInfo = analyzer.analyzeTable(tableName);
            System.out.println("   ✅ 表结构分析完成，共 " + tableInfo.getColumns().size() + " 个字段");


            // 3.应用规则
            List<ColumnInfo> processedColumns = tableInfo.getColumns().stream()
                    .map(column -> expressionRuleEngine.executeRules(column, new HashMap<>()))
                    .collect(Collectors.toList());

            // 2.生成接口文档
            ApiDocument apiDoc = docGenerator.generateDocument(tableInfo);

            tableInfo.setColumns(processedColumns); // 替换整个列表

            System.out.println("   ✅ 规则应用完成");

            generateApiDOc(apiDoc, config.getApiDocOutputDir());
            System.out.println("   ✅ 接口文档生成完成，共 " + apiDoc.getEndpoints().size() + " 个接口");

            // 4.生成接口文件
           /* generateCodeFiles(codeGenerator, tableInfo, apiDoc, config, true);

            // 5.生成实现类文件
            generateCodeFiles(codeGenerator, tableInfo, apiDoc, config, false);*/

            System.out.println("   ✅ 代码生成完成");

        } catch (Exception e) {
            System.err.println("   ❌ 处理表 " + tableName + " 时出错: " + e.getMessage());
        }
    }

    /**
     *  生成接口文档
     * @param apiDocument 接口文档
     * @param apiDocOutputDir 接口文档输出路径
     */
    private static void generateApiDOc(ApiDocument apiDocument, String apiDocOutputDir) throws Exception {
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

    private static void generateApiDocToFile(String content, String tableName, String suffix, String apiDocOutputDir) throws IOException {
        File outputFile = new File(apiDocOutputDir, tableName + suffix);
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);

        }

    }

    /**
     * 生成代码文件
     */
    private static void generateCodeFiles(SpringCodeGenerator codeGenerator,
                                          TableInfo tableInfo,
                                          ApiDocument apiDoc,
                                          GeneratorConfig config, boolean isInterface) throws Exception {



        // 接口
        if(isInterface) {
            String basePackageDir = config.getInterfaceOutputDir() + "/" + packageToPath(config.getBasePackage());
            // 生成请求类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "command.ftl", "Command");

            // 生成查询实体类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "query.ftl", "Query");

            // 生成View
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/vo",
                    config.getBasePackage(), ".api.vo",
                    "view.ftl", "View");

            // 生成接口定义类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "service.ftl", "Service");

            // 生成feign client
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "client.ftl", "Client");
        } else {
            String basePackageDir = config.getServiceOutputDir() + "/" + packageToPath(config.getBasePackage());

            // 生成实体类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/model",
                    config.getBasePackage(), ".domain.model",
                    "model.ftl", "");


            // 生成mapper转换类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/assembler",
                    config.getBasePackage(), ".api.assembler",
                    "mapper.ftl", "Mapper");



            // 生成dao
            codeGenerator.generateFile(tableInfo, basePackageDir + "/infrastructure/persistence/dao",
                    config.getBasePackage(), ".infrastructure.persistence.dao",
                    "dao.ftl", "Dao");

            // 生成repository
            codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/repository",
                    config.getBasePackage(), ".domain.repository",
                    "repository.ftl", "Repository");

            // 生成repository 实现类
            codeGenerator.generateFile(tableInfo, basePackageDir + "/infrastructure/persistence",
                    config.getBasePackage(), ".infrastructure.persistence",
                    "repository-impl.ftl", "MyBatisRepository");



            // 生成api
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "resource.ftl", "Resource");
        }


    }


    /**
     * 包名转路径
     */
    private static String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }



}
