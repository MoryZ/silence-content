package com.old.silence.content.code.generator.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.executor.SpringCodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.vo.CodePreviewResponse;

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
     * 包名转路径
     */
    private static String packageToPath(String packageName) {
        return packageName.replace('.', '/');
    }

    /**
     * 生成代码文件
     */
    public void generateCode(SpringCodeGenerator codeGenerator,
                             TableInfo tableInfo,
                             ApiDocument apiDoc,
                             CodeGenModuleConfig config) throws Exception {

        // 生成枚举类（如果配置了）
        generateEnumCode(codeGenerator, tableInfo, config);

        generateInterfaceCode(codeGenerator, tableInfo, apiDoc, config);

        generateServiceCode(codeGenerator, tableInfo, apiDoc, config);

        generateConsoleCode(codeGenerator, tableInfo, apiDoc, config);

    }



    public void generateEnumCode(SpringCodeGenerator codeGenerator,
                                 TableInfo tableInfo,
                                 CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();

        // 生成枚举类
        codeGenerator.generateFile(tableInfo, outDirectory,
                config.getBasePackage(), "",
                "enum.ftl", "Enum");


    }

    public void generateInterfaceCode(SpringCodeGenerator codeGenerator,
                                      TableInfo tableInfo,
                                      ApiDocument apiDoc,
                                      CodeGenModuleConfig config) throws Exception {

        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 根据ApiDoc决定是否生成Command（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成Command");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "command.ftl", "Command");
        }

        // 根据ApiDoc决定是否生成Query（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成Query");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "query.ftl", "Query");
        }

        // 根据ApiDoc决定是否生成View（根据主键查询或分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成View");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/vo",
                    config.getBasePackage(), ".api.vo",
                    "view.ftl", "View");
        }

        // 生成接口定义类（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Service接口定义");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "service.ftl", "Service");
        }

        // 生成feign client（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Feign Client");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "client.ftl", "Client");
        }
    }

    public void generateServiceCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 实体类始终生成（基础数据模型）
        log.info("生成实体类Model");
        codeGenerator.generateFile(tableInfo, outDirectory + "/domain/model",
                config.getBasePackage(), ".domain.model",
                "model.ftl", "");

        // 根据ApiDoc决定是否生成Mapper转换类（有创建、更新或查询接口时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新") ||
                hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到CRUD接口，生成Mapper转换类");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/assembler",
                    config.getBasePackage(), ".api.assembler",
                    "mapper.ftl", "Mapper");
        }

        // DAO/Repository始终生成（数据访问层）
        log.info("生成DAO层");
        codeGenerator.generateFile(tableInfo, outDirectory + "/infrastructure/persistence/dao",
                config.getBasePackage(), ".infrastructure.persistence.dao",
                "dao.ftl", "Dao");

        log.info("生成Repository接口");
        codeGenerator.generateFile(tableInfo, outDirectory + "/domain/repository",
                config.getBasePackage(), ".domain.repository",
                "repository.ftl", "Repository");

        log.info("生成Repository实现类");
        codeGenerator.generateFile(tableInfo, outDirectory + "/infrastructure/persistence",
                config.getBasePackage(), ".infrastructure.persistence",
                "repository-impl.ftl", "MyBatisRepository");

        // 根据ApiDoc决定是否生成Resource（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成REST Resource");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "resource.ftl", "Resource");
        }
    }

    public void generateConsoleCode(SpringCodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        // 根据ApiDoc决定是否生成ConsoleCommand（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成ConsoleCommand");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleCommand.ftl", "ConsoleCommand");
        }

        // 根据ApiDoc决定是否生成ConsoleQuery（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成ConsoleQuery");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleQuery.ftl", "ConsoleQuery");
        }

        // 根据ApiDoc决定是否生成ConsoleView（根据主键查询或分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成ConsoleView");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/vo",
                    config.getBasePackage(), ".console.vo",
                    "consoleView.ftl", "ConsoleView");
        }

        // 根据ApiDoc决定是否生成CommandMapper（创建或更新接口存在时生成）
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成CommandMapper");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "commandMapper.ftl", "CommandMapper");
        }

        // 根据ApiDoc决定是否生成QueryMapper（分页查询接口存在时生成）
        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成QueryMapper");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "queryMapper.ftl", "QueryMapper");
        }

        // 根据ApiDoc决定是否生成ConsoleResource（至少有一个endpoint就生成）
        if (!endpoints.isEmpty()) {
            log.info("生成Console Resource");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api",
                    config.getBasePackage(), ".console.api",
                    "consoleResource.ftl", "Resource");
        }
    }

    /**
     * 预览代码（不生成文件，返回内容）
     */
    public CodePreviewResponse previewCode(SpringCodeGenerator codeGenerator,
                                           ApiDocument apiDoc) {
        var tableName = apiDoc.getTableName();
        CodePreviewResponse response = new CodePreviewResponse();
        response.setTableName(tableName);

        // 预览枚举类
        previewEnumCode(codeGenerator, apiDoc, response);

        // 预览Interface层
        previewInterfaceCode(codeGenerator, apiDoc, response);

        // 预览Service层
        previewServiceCode(codeGenerator, apiDoc, response);

        // 预览Console层
        previewConsoleCode(codeGenerator, apiDoc, response);

        return response;
    }

    private void previewEnumCode(SpringCodeGenerator codeGenerator,
                                 ApiDocument apiDoc,
                                 CodePreviewResponse response) {
        var tableInfo = apiDoc.getTableInfo();

        tableInfo.getColumnInfos().stream().filter(ColumnInfo::getEnum)
                .forEach(columnInfo -> {
                    String prefixFileName = codeGenerator.getFileName(tableInfo, "");
                    var enumFileName = prefixFileName + StringUtils.capitalize(columnInfo.getFieldName());
                    Map<String, Object> customerDataModel = Map.of("enumName", enumFileName);
                    String content = codeGenerator.renderTemplate(tableInfo, "",
                            ".domain.enums", "enum.ftl", customerDataModel);
                    response.addFile("enum", enumFileName, "domain/enums/" + enumFileName, content, "enum");
                });

    }

    private void previewInterfaceCode(SpringCodeGenerator codeGenerator,
                                      ApiDocument apiDoc,
                                      CodePreviewResponse response) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api.dto", "command.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "Command");
            response.addFile("interface", fileName, "api/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api.dto", "query.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "Query");
            response.addFile("interface", fileName, "api/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api.vo", "view.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "View");
            response.addFile("interface", fileName, "api/vo/" + fileName, content, "vo");
        }

        if (!endpoints.isEmpty()) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api", "service.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "Service");
            response.addFile("interface", fileName, "api/" + fileName, content, "service");

            content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api", "client.ftl");
            fileName = codeGenerator.getFileName(tableInfo, "Client");
            response.addFile("interface", fileName, "api/" + fileName, content, "client");
        }
    }

    private void previewServiceCode(SpringCodeGenerator codeGenerator,
                                    ApiDocument apiDoc,
                                    CodePreviewResponse response) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();

        // Model
        String content = codeGenerator.renderTemplate(tableInfo, "",
                ".domain.model", "model.ftl");
        String fileName = codeGenerator.getFileName(tableInfo, "");
        response.addFile("service", fileName, "domain/model/" + fileName, content, "model");

        // Mapper
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新") ||
                hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api.assembler", "mapper.ftl");
            fileName = codeGenerator.getFileName(tableInfo, "Mapper");
            response.addFile("service", fileName, "api/assembler/" + fileName, content, "mapper");
        }

        // DAO
        content = codeGenerator.renderTemplate(tableInfo, "",
                ".infrastructure.persistence.dao", "dao.ftl");
        fileName = codeGenerator.getFileName(tableInfo, "Dao");
        response.addFile("service", fileName, "infrastructure/persistence/dao/" + fileName, content, "dao");

        // Repository
        content = codeGenerator.renderTemplate(tableInfo, "",
                ".domain.repository", "repository.ftl");
        fileName = codeGenerator.getFileName(tableInfo, "Repository");
        response.addFile("service", fileName, "domain/repository/" + fileName, content, "repository");

        content = codeGenerator.renderTemplate(tableInfo, "",
                ".infrastructure.persistence", "repository-impl.ftl");
        fileName = codeGenerator.getFileName(tableInfo, "MyBatisRepository");
        response.addFile("service", fileName, "infrastructure/persistence/" + fileName, content, "repository-impl");

        // Resource
        if (!endpoints.isEmpty()) {
            content = codeGenerator.renderTemplate(tableInfo, "",
                    ".api", "resource.ftl");
            fileName = codeGenerator.getFileName(tableInfo, "Resource");
            response.addFile("service", fileName, "api/" + fileName, content, "resource");
        }
    }

    private void previewConsoleCode(SpringCodeGenerator codeGenerator,
                                    ApiDocument apiDoc, CodePreviewResponse response) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.dto", "consoleCommand.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "ConsoleCommand");
            response.addFile("console", fileName, "console/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.dto", "consoleQuery.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "ConsoleQuery");
            response.addFile("console", fileName, "console/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.vo", "consoleView.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "ConsoleView");
            response.addFile("console", fileName, "console/vo/" + fileName, content, "vo");
        }

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.api.assembler", "commandMapper.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "CommandMapper");
            response.addFile("console", fileName, "console/api/assembler/" + fileName, content, "mapper");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.api.assembler", "queryMapper.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "QueryMapper");
            response.addFile("console", fileName, "console/api/assembler/" + fileName, content, "mapper");
        }

        if (!endpoints.isEmpty()) {
            String content = codeGenerator.renderTemplate(tableInfo, "",
                    ".console.api", "consoleResource.ftl");
            String fileName = codeGenerator.getFileName(tableInfo, "Resource");
            response.addFile("console", fileName, "console/api/" + fileName, content, "resource");
        }
    }

    /**
     * 检查ApiDocument中是否存在指定的endpoint
     *
     * @param endpoints    endpoint映射
     * @param endpointName endpoint名称（如："创建"、"更新"、"分页查询"等）
     * @return 是否存在该endpoint
     */
    private boolean hasEndpoint(Map<String, ApiEndpoint> endpoints, String endpointName) {
        if (endpoints == null || endpoints.isEmpty()) {
            return false;
        }
        return endpoints.containsKey(endpointName);
    }

}
