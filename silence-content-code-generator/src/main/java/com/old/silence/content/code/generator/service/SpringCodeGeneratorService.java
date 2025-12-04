package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.TableInfo;

import java.util.Map;

/**
 * Spring代码生成服务
 * 
 * <p>根据ApiDocument中的endpoints决定生成哪些代码文件：
 * <ul>
 *   <li>"分页查询" - 生成Query、QueryMapper</li>
 *   <li>"创建" - 生成Command、CommandMapper</li>
 *   <li>"根据主键查询" - 生成View、详情接口</li>
 *   <li>"更新" - 生成更新相关代码</li>
 *   <li>"删除" - 生成删除相关代码</li>
 * </ul>
 *
 * @author moryzang
 */
@Service
public class SpringCodeGeneratorService {
    
    private static final Logger log = LoggerFactory.getLogger(SpringCodeGeneratorService.class);

    /**
     * 生成代码文件
     */
    public void generateCode(SpringCodeGenerator codeGenerator,
                                      TableInfo tableInfo,
                                      ApiDocument apiDoc,
                                      GeneratorConfig config) throws Exception {

        // 生成枚举类（如果配置了）
        if (hasEnumConfig(config, tableInfo.getTableName())) {
            generateEnumCode(codeGenerator, tableInfo, apiDoc, config);
        }

        generateInterfaceCode(codeGenerator, tableInfo, apiDoc, config);

        generateServiceCode(codeGenerator, tableInfo, apiDoc, config);

        generateConsoleCode(codeGenerator, tableInfo, apiDoc, config);

    }

    /**
     * 检查是否有枚举配置
     */
    private boolean hasEnumConfig(GeneratorConfig config, String tableName) {
        if (config.getEnumConfigs() == null || config.getEnumConfigs().isEmpty()) {
            return false;
        }
        
        return config.getEnumConfigs().stream()
                .anyMatch(ec -> tableName.equals(ec.getTableName()) && 
                               Boolean.TRUE.equals(ec.getGenerateEnum()));
    }

    public void generateEnumCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
        String basePackageDir = config.getEnumOutputDir() + "/" + packageToPath(config.getBasePackage());

        // 生成枚举类
        codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/enums",
                config.getBasePackage(), ".domain.enums",
                "enum.ftl", "");


    }

    public void generateInterfaceCode(SpringCodeGenerator codeGenerator,
                                       TableInfo tableInfo,
                                       ApiDocument apiDoc,
                                       GeneratorConfig config) throws Exception {

        String basePackageDir = config.getInterfaceOutputDir() + "/" + packageToPath(config.getBasePackage());
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 根据ApiDoc决定是否生成Command（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成Command");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "command.ftl", "Command");
        }

        // 根据ApiDoc决定是否生成Query（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成Query");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "query.ftl", "Query");
        }

        // 根据ApiDoc决定是否生成View（根据主键查询或分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成View");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/vo",
                    config.getBasePackage(), ".api.vo",
                    "view.ftl", "View");
        }

        // 生成接口定义类（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Service接口定义");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "service.ftl", "Service");
        }

        // 生成feign client（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Feign Client");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "client.ftl", "Client");
        }
    }

    public void generateServiceCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
        String basePackageDir = config.getServiceOutputDir() + "/" + packageToPath(config.getBasePackage());
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 实体类始终生成（基础数据模型）
        log.info("生成实体类Model");
        codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/model",
                config.getBasePackage(), ".domain.model",
                "model.ftl", "");

        // 根据ApiDoc决定是否生成Mapper转换类（有创建、更新或查询接口时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新") || 
            hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到CRUD接口，生成Mapper转换类");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api/assembler",
                    config.getBasePackage(), ".api.assembler",
                    "mapper.ftl", "Mapper");
        }

        // DAO/Repository始终生成（数据访问层）
        log.info("生成DAO层");
        codeGenerator.generateFile(tableInfo, basePackageDir + "/infrastructure/persistence/dao",
                config.getBasePackage(), ".infrastructure.persistence.dao",
                "dao.ftl", "Dao");

        log.info("生成Repository接口");
        codeGenerator.generateFile(tableInfo, basePackageDir + "/domain/repository",
                config.getBasePackage(), ".domain.repository",
                "repository.ftl", "Repository");

        log.info("生成Repository实现类");
        codeGenerator.generateFile(tableInfo, basePackageDir + "/infrastructure/persistence",
                config.getBasePackage(), ".infrastructure.persistence",
                "repository-impl.ftl", "MyBatisRepository");

        // 根据ApiDoc决定是否生成Resource（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成REST Resource");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/api",
                    config.getBasePackage(), ".api",
                    "resource.ftl", "Resource");
        }
    }

    public void generateConsoleCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    GeneratorConfig config) throws Exception {
        String basePackageDir = config.getConsoleOutputDir() + "/" + packageToPath(config.getBasePackage());
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 根据ApiDoc决定是否生成ConsoleCommand（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成ConsoleCommand");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleCommand.ftl", "ConsoleCommand");
        }

        // 根据ApiDoc决定是否生成ConsoleQuery（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成ConsoleQuery");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleQuery.ftl", "ConsoleQuery");
        }

        // 根据ApiDoc决定是否生成ConsoleView（根据主键查询或分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成ConsoleView");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/vo",
                    config.getBasePackage(), ".console.vo",
                    "consoleView.ftl", "ConsoleView");
        }

        // 根据ApiDoc决定是否生成CommandMapper（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成CommandMapper");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "commandMapper.ftl", "CommandMapper");
        }

        // 根据ApiDoc决定是否生成QueryMapper（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成QueryMapper");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "queryMapper.ftl", "QueryMapper");
        }

        // 根据ApiDoc决定是否生成ConsoleResource（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Console Resource");
            codeGenerator.generateFile(tableInfo, basePackageDir + "/console/api",
                    config.getBasePackage(), ".console.api",
                    "consoleResource.ftl", "Resource");
        }
    }


    /**
     * 检查ApiDocument中是否存在指定的endpoint
     * 
     * @param endpoints endpoint映射
     * @param endpointName endpoint名称（如："创建"、"更新"、"分页查询"等）
     * @return 是否存在该endpoint
     */
    private boolean hasEndpoint(Map<String, ApiEndpoint> endpoints, String endpointName) {
        if (endpoints == null || endpoints.isEmpty()) {
            return false;
        }
        return endpoints.containsKey(endpointName);
    }

    /**
     * 包名转路径
     */
    private static String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }
}
