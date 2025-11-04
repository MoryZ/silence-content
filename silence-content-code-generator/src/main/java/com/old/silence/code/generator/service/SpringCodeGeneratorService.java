package com.old.silence.code.generator.service;

import org.springframework.stereotype.Service;
import com.old.silence.code.generator.executor.SpringCodeGenerator;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;

/**
 * @author moryzang
 */
@Service
public class SpringCodeGeneratorService {


    /**
     * 生成代码文件
     */
    public void generateCode(SpringCodeGenerator codeGenerator,
                                      TableInfo tableInfo,
                                      ApiDocument apiDoc,
                                      GeneratorConfig config) throws Exception {

        //generateEnumCode(codeGenerator, tableInfo, apiDoc, config);

        generateInterfaceCode(codeGenerator, tableInfo, apiDoc, config);

        generateServiceCode(codeGenerator, tableInfo, apiDoc, config);

        generateConsoleCode(codeGenerator, tableInfo, apiDoc, config);

    }

    public void generateEnumCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
        String basePackageDir = config.getEnumOutputDir() + "/" + packageToPath(config.getBasePackage());

        // 生成请求类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/enums",
                config.getBasePackage(), ".domain.enums",
                "enum.ftl", "");


    }

    private void generateInterfaceCode(SpringCodeGenerator codeGenerator,
                                       TableInfo tableInfo,
                                       ApiDocument apiDoc,
                                       GeneratorConfig config) throws Exception {

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
    }

    public void generateServiceCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
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

    public void generateConsoleCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
        String basePackageDir = config.getConsoleOutputDir() + "/" + packageToPath(config.getBasePackage());


        // 生成请求类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/dto",
                config.getBasePackage(), ".console.dto",
                "consoleCommand.ftl", "ConsoleCommand");

        // 生成查询实体类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/dto",
                config.getBasePackage(), ".console.dto",
                "consoleQuery.ftl", "ConsoleQuery");

        // 生成View
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/vo",
                config.getBasePackage(), ".console.vo",
                "consoleView.ftl", "ConsoleView");

        // 生成commandMapper转换类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api/assembler",
                config.getBasePackage(), ".console.api.assembler",
                "commandMapper.ftl", "CommandMapper");

        // 生成QueryMapper转换类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api/assembler",
                config.getBasePackage(), ".console.api.assembler",
                "queryMapper.ftl", "QueryMapper");


        // 生成api
        codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api",
                config.getBasePackage(), ".console.api",
                "consoleResource.ftl", "Resource");

    }


    /**
     * 包名转路径
     */
    private static String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }
}
