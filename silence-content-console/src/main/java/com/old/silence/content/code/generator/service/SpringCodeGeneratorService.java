package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ApiEndpoint;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.vo.CodePreviewResponse;

import java.util.List;
import java.util.Map;

@Service
public class SpringCodeGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(SpringCodeGeneratorService.class);

    public void generateCode(CodeGenerator codeGenerator,
                             TableInfo tableInfo,
                             ApiDocument apiDoc,
                             CodeGenModuleConfig config) throws Exception {
        generateEnumCode(codeGenerator, tableInfo, config);
        generateInterfaceCode(codeGenerator, tableInfo, apiDoc, config);
        generateServiceCode(codeGenerator, tableInfo, apiDoc, config);
        generateConsoleCode(codeGenerator, tableInfo, apiDoc, config);
    }

    public void generateEnumCode(CodeGenerator codeGenerator,
                                 TableInfo tableInfo,
                                 CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        codeGenerator.generateFile(tableInfo, outDirectory,
                config.getBasePackage(), "",
                "enum.ftl", "Enum");
    }

    public void generateInterfaceCode(CodeGenerator codeGenerator,
                                      TableInfo tableInfo,
                                      ApiDocument apiDoc,
                                      CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成Command");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "command.ftl", "Command");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成Query");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/dto",
                    config.getBasePackage(), ".api.dto",
                    "query.ftl", "Query");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成View");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/vo",
                    config.getBasePackage(), ".api.vo",
                    "view.ftl", "View");
        }

        if (!endpoints.isEmpty()) {
            log.info("生成Service接口定义");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "service.ftl", "Service");
            log.info("生成Feign Client");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "client.ftl", "Client");
        }
    }

    public void generateServiceCode(CodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        log.info("生成实体类Model");
        codeGenerator.generateFile(tableInfo, outDirectory + "/domain/model",
                config.getBasePackage(), ".domain.model",
                "model.ftl", "");

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新") ||
                hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到CRUD接口，生成Mapper转换类");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api/assembler",
                    config.getBasePackage(), ".api.assembler",
                    "mapper.ftl", "Mapper");
        }

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

        if (!endpoints.isEmpty()) {
            log.info("生成REST Resource");
            codeGenerator.generateFile(tableInfo, outDirectory + "/api",
                    config.getBasePackage(), ".api",
                    "resource.ftl", "Resource");
        }
    }

    public void generateConsoleCode(CodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    ApiDocument apiDoc,
                                    CodeGenModuleConfig config) throws Exception {
        String outDirectory = config.getProjectPath() + "/" + config.getModulePath() + "/" + config.getOutDirectory();
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成ConsoleCommand");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleCommand.ftl", "ConsoleCommand");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成ConsoleQuery");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/dto",
                    config.getBasePackage(), ".console.dto",
                    "consoleQuery.ftl", "ConsoleQuery");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到查询接口，生成ConsoleView");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/vo",
                    config.getBasePackage(), ".console.vo",
                    "consoleView.ftl", "ConsoleView");
        }

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            log.info("检测到创建/更新接口，生成CommandMapper");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "commandMapper.ftl", "CommandMapper");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            log.info("检测到分页查询接口，生成QueryMapper");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api/assembler",
                    config.getBasePackage(), ".console.api.assembler",
                    "queryMapper.ftl", "QueryMapper");
        }

        if (!endpoints.isEmpty()) {
            log.info("生成Console Resource");
            codeGenerator.generateFile(tableInfo, outDirectory + "/console/api",
                    config.getBasePackage(), ".console.api",
                    "consoleResource.ftl", "Resource");
        }
    }

    public CodePreviewResponse previewCode(CodeGenerator codeGenerator,
                                           ApiDocument apiDoc, List<CodeGenModuleConfig> codeGenModuleConfigs) {
        var tableName = apiDoc.getTableName();
        CodePreviewResponse response = new CodePreviewResponse();
        response.setTableName(tableName);

        previewEnumCode(codeGenerator, apiDoc, response, codeGenModuleConfigs.getFirst());
        previewInterfaceCode(codeGenerator, apiDoc, response, codeGenModuleConfigs.getFirst());
        previewServiceCode(codeGenerator, apiDoc, response, codeGenModuleConfigs.getFirst());
        previewConsoleCode(codeGenerator, apiDoc, response, codeGenModuleConfigs.getFirst());

        return response;
    }

    private void previewEnumCode(CodeGenerator codeGenerator,
                                 ApiDocument apiDoc,
                                 CodePreviewResponse response, CodeGenModuleConfig codeGenModuleConfig) {
        var tableInfo = apiDoc.getTableInfo();
        tableInfo.getColumnInfos().stream().filter(ColumnInfo::getEnum)
                .forEach(columnInfo -> {
                    var fieldName = columnInfo.getFieldName();
                    Map<String, Object> customerDataModel = Map.of("enumName", fieldName);
                    String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                            "", "enum.ftl", customerDataModel);
                    response.addFile("enum", fieldName, "domain/enums/" + fieldName, content, "enum");
                });
    }

    private void previewInterfaceCode(CodeGenerator codeGenerator,
                                      ApiDocument apiDoc,
                                      CodePreviewResponse response, CodeGenModuleConfig codeGenModuleConfig) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api.dto", "command.ftl");
            String fileName = fileName(tableInfo, "Command");
            response.addFile("interface", fileName, "api/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api.dto", "query.ftl");
            String fileName = fileName(tableInfo, "Query");
            response.addFile("interface", fileName, "api/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api.vo", "view.ftl");
            String fileName = fileName(tableInfo, "View");
            response.addFile("interface", fileName, "api/vo/" + fileName, content, "vo");
        }

        if (!endpoints.isEmpty()) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api", "service.ftl");
            String fileName = fileName(tableInfo, "Service");
            response.addFile("interface", fileName, "api/" + fileName, content, "service");

            content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api", "client.ftl");
            fileName = fileName(tableInfo, "Client");
            response.addFile("interface", fileName, "api/" + fileName, content, "client");
        }
    }

    private void previewServiceCode(CodeGenerator codeGenerator,
                                    ApiDocument apiDoc,
                                    CodePreviewResponse response, CodeGenModuleConfig codeGenModuleConfig) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();

        String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                ".domain.model", "model.ftl");
        String fileName = fileName(tableInfo, "");
        response.addFile("service", fileName, "domain/model/" + fileName, content, "model");

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新") ||
                hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api.assembler", "mapper.ftl");
            fileName = fileName(tableInfo, "Mapper");
            response.addFile("service", fileName, "api/assembler/" + fileName, content, "mapper");
        }

        content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                ".infrastructure.persistence.dao", "dao.ftl");
        fileName = fileName(tableInfo, "Dao");
        response.addFile("service", fileName, "infrastructure/persistence/dao/" + fileName, content, "dao");

        content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                ".domain.repository", "repository.ftl");
        fileName = fileName(tableInfo, "Repository");
        response.addFile("service", fileName, "domain/repository/" + fileName, content, "repository");

        content = codeGenerator.renderTemplate(tableInfo, "",
                ".infrastructure.persistence", "repository-impl.ftl");
        fileName = fileName(tableInfo, "MyBatisRepository");
        response.addFile("service", fileName, "infrastructure/persistence/" + fileName, content, "repository-impl");

        if (!endpoints.isEmpty()) {
            content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".api", "resource.ftl");
            fileName = fileName(tableInfo, "Resource");
            response.addFile("service", fileName, "api/" + fileName, content, "resource");
        }
    }

    private void previewConsoleCode(CodeGenerator codeGenerator,
                                    ApiDocument apiDoc, CodePreviewResponse response, CodeGenModuleConfig codeGenModuleConfig) {
        Map<String, ApiEndpoint> endpoints = apiDoc.getEndpoints();
        var tableInfo = apiDoc.getTableInfo();
        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.dto", "consoleCommand.ftl");
            String fileName = fileName(tableInfo, "ConsoleCommand");
            response.addFile("console", fileName, "console/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.dto", "consoleQuery.ftl");
            String fileName = fileName(tableInfo, "ConsoleQuery");
            response.addFile("console", fileName, "console/dto/" + fileName, content, "dto");
        }

        if (hasEndpoint(endpoints, "根据主键查询") || hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.vo", "consoleView.ftl");
            String fileName = fileName(tableInfo, "ConsoleView");
            response.addFile("console", fileName, "console/vo/" + fileName, content, "vo");
        }

        if (hasEndpoint(endpoints, "创建") || hasEndpoint(endpoints, "更新")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.api.assembler", "commandMapper.ftl");
            String fileName = fileName(tableInfo, "CommandMapper");
            response.addFile("console", fileName, "console/api/assembler/" + fileName, content, "mapper");
        }

        if (hasEndpoint(endpoints, "分页查询")) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.api.assembler", "queryMapper.ftl");
            String fileName = fileName(tableInfo, "QueryMapper");
            response.addFile("console", fileName, "console/api/assembler/" + fileName, content, "mapper");
        }

        if (!endpoints.isEmpty()) {
            String content = codeGenerator.renderTemplate(tableInfo, codeGenModuleConfig.getBasePackage(),
                    ".console.api", "consoleResource.ftl");
            String fileName = fileName(tableInfo, "Resource");
            response.addFile("console", fileName, "console/api/" + fileName, content, "resource");
        }
    }

    private boolean hasEndpoint(Map<String, ApiEndpoint> endpoints, String endpointName) {
        if (endpoints == null || endpoints.isEmpty()) {
            return false;
        }
        return endpoints.containsKey(endpointName);
    }

    private String fileName(TableInfo tableInfo, String suffix) {
        String className = com.old.silence.content.code.generator.util.NameConverterUtils.toCamelCase(tableInfo.getTableName(), true);
        return className + suffix + ".java";
    }
}
