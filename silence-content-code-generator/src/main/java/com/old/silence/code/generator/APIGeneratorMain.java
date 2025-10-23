package com.old.silence.code.generator;

import java.io.File;

import com.old.silence.code.generator.analyzer.SQLAnalyzer;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;
import com.old.silence.code.generator.rule.RuleConfig;
import com.old.silence.code.generator.rule.RuleEngine;

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
        config.setDbUrl("jdbc:mysql://localhost:3306/test_cannal");
        config.setUsername("root");
        config.setPassword("admin123456");
        config.setPersistencePackage("jakarta");
        config.setUseLombok(false);
        config.setBasePackage("com.old.silence");
        config.setOutputDir("src");
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
            //String[] tables = getTablesToGenerate(analyzer, args);
            String[] tables = new String[]{"poetry_user"};

            System.out.println("🚀 开始生成API，共 " + tables.length + " 张表");

            // 生成代码
            generateForTables(analyzer, docGenerator, codeGenerator, ruleEngine, tables, config);

            System.out.println("\n🎉 API生成完成！输出目录: " + new File(config.getOutputDir()).getAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ API生成失败: " + e.getMessage());
            e.printStackTrace();
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
                                          String[] tables,
                                          GeneratorConfig config) {

        for (String tableName : tables) {
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

                // 4.生成代码文件
                generateCodeFiles(codeGenerator, tableInfo, apiDoc, config);

                System.out.println("   ✅ 代码生成完成");

            } catch (Exception e) {
                System.err.println("   ❌ 处理表 " + tableName + " 时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成代码文件
     */
    private static void generateCodeFiles(SpringCodeGenerator codeGenerator,
                                          TableInfo tableInfo,
                                          ApiDocument apiDoc,
                                          GeneratorConfig config) throws Exception {

        String basePackage = config.getOutputDir() + "/" + packageToPath(config.getBasePackage());


        // 生成实体类
        codeGenerator.generateFile(tableInfo, basePackage + "/domain/model", "model.ftl", "");

        // 生成请求类
        codeGenerator.generateFile(tableInfo, basePackage + "/api/dto", "command.ftl", "Command");

        // 生成mapper转换类
        codeGenerator.generateFile(tableInfo, basePackage + "/api/assembler", "mapper.ftl", "Mapper");

        // 生成查询实体类
        codeGenerator.generateFile(tableInfo, basePackage + "/api/assembler", "query.ftl", "Query");

        codeGenerator.generateFile(tableInfo, basePackage + "/api/vo", "view.ftl", "View");

        // 生成dao
        codeGenerator.generateFile(tableInfo, basePackage + "/infrastructure/persistence/dao", "dao.ftl", "Dao");

        // 生成repository
        codeGenerator.generateFile(tableInfo, basePackage + "/domain/repository", "repository.ftl", "Repository");

        // 生成repository 实现类
        codeGenerator.generateFile(tableInfo, basePackage + "/infrastructure/persistence", "repository-impl.ftl", "MyBatisRepository");

        // 生成接口定义类
        codeGenerator.generateFile(tableInfo, basePackage + "/api", "service.ftl", "Service");

        // 生成feign client
        codeGenerator.generateFile(tableInfo, basePackage + "/api", "client.ftl", "Client");

        // 生成api
        codeGenerator.generateFile(tableInfo, basePackage + "/api", "resource.ftl", "Resource");
    }


    /**
     * 包名转路径
     */
    private static String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }

}
