package com.old.silence.code.generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.old.silence.code.generator.analyzer.SQLAnalyzer;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.rule.RuleConfig;
import com.old.silence.code.generator.rule.RuleEngine;
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
        config.setDbUrl("jdbc:mysql://localhost:3306/test-cannal");
        config.setUsername("root");
        config.setPassword("123456");
        config.setPersistencePackage("jakarta");
        config.setUseLombok(false);
        config.setBasePackage("com.old.silence");

        config.setImplOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");

        config.setRulesConfigPath("rules/default-rules.json");
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
            RuleEngine ruleEngine = new RuleEngine();

            // 加载规则配置
            loadRules(ruleEngine, config.getRulesConfigPath());

            // 获取要生成的表


            System.out.println("🚀 开始生成API，共 " + CollectionUtils.size(analyzer.getTablesWithComments()) + " 张表");

            analyzer.getTablesWithComments().forEach((tableName, tableComment) -> {
                // 生成代码
                generateForTables(analyzer, docGenerator, codeGenerator, ruleEngine, tableName, config);
                System.out.println("🚀 代码生成成功," + "表名：" + tableName + "注释:" + tableComment);
            });


        } catch (Exception e) {
            System.err.println("❌ API生成失败: " + e.getMessage());
        }
    }

    /**
     * 加载规则配置
     */
    private static void loadRules(RuleEngine ruleEngine, String rulesConfigPath) {
        try {
            RuleConfig ruleConfig = RuleConfig.loadFromFile(rulesConfigPath);
            ruleEngine.loadRulesFromConfig(ruleConfig);
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
                                          RuleEngine ruleEngine,
                                          String tableName,
                                          GeneratorConfig config) {

        System.out.println("\n📋 处理表: " + tableName);

        try {
            // 1.分析表结构
            TableInfo tableInfo = analyzer.analyzeTable(tableName);
            System.out.println("   ✅ 表结构分析完成，共 " + tableInfo.getColumns().size() + " 个字段");

            // 2.生成接口文档
            ApiDocument apiDoc = docGenerator.generateDocument(tableInfo);
            System.out.println("   ✅ 接口文档生成完成，共 " + apiDoc.getEndpoints().size() + " 个接口");

            // 3.应用规则
            ruleEngine.applyRules(tableInfo, apiDoc);
            System.out.println("   ✅ 规则应用完成");

            // 4.生成接口文件
            generateCodeFiles(codeGenerator, tableInfo, apiDoc, config, true);

            // 5.生成实现类文件
            generateCodeFiles(codeGenerator, tableInfo, apiDoc, config, false);

            System.out.println("   ✅ 代码生成完成");

        } catch (Exception e) {
            System.err.println("   ❌ 处理表 " + tableName + " 时出错: " + e.getMessage());
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
            String basePackageDir = config.getImplOutputDir() + "/" + packageToPath(config.getBasePackage());

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
