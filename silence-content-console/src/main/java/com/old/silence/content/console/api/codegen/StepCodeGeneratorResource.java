package com.old.silence.content.console.api.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeGenDatabaseClient;
import com.old.silence.content.api.CodeGenProjectClient;
import com.old.silence.content.code.generator.dto.CodeFileSpecConfig;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.vo.Step3CodePreviewResponse;
import com.old.silence.content.code.generator.service.StepService;
import com.old.silence.content.console.vo.CodeGenDatabaseConsoleView;
import com.old.silence.content.console.vo.CodeGenProjectConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class StepCodeGeneratorResource {


    private static final Logger log = LoggerFactory.getLogger(StepCodeGeneratorResource.class);
    private final StepService stepService;
    private final CodeGenDatabaseClient codeGenDatabaseClient;
    private final CodeGenProjectClient codeGenProjectClient;

    public StepCodeGeneratorResource(StepService stepService,
                                     CodeGenDatabaseClient codeGenDatabaseClient,
                                     CodeGenProjectClient codeGenProjectClient) {
        this.stepService = stepService;
        this.codeGenDatabaseClient = codeGenDatabaseClient;
        this.codeGenProjectClient = codeGenProjectClient;
    }

    /**
     * 步骤1：查看表结构信息
     * 在生成代码前，先验证表结构是否正确
     *
     * @param databaseId 数据库连接id
     * @return 表信息响应
     */
    @GetMapping(value = "/steps/table-info/{databaseId}", params = {"pageNo", "pageSize"})
    public Page<TableInfo> validateStep1TableInfo(@PathVariable BigInteger databaseId, Pageable pageable) {
        log.info("步骤1,数据库连接id, - {}", databaseId);
        var codeGenDatabaseConsoleView = codeGenDatabaseClient.findById(databaseId, CodeGenDatabaseConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
        var databaseConfig = new DatabaseConfig(codeGenDatabaseConsoleView.getDatabaseUrl(),
                codeGenDatabaseConsoleView.getUsername(), codeGenDatabaseConsoleView.getPassword());
        var allTableInfos = stepService.validateStep1TableInfo(databaseConfig);

        List<TableInfo> tableInfos = allTableInfos.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(tableInfos, pageable, CollectionUtils.size(allTableInfos));
    }

    /**
     * 步骤2：查看生成的API文档
     * 验证生成的API文档是否符合预期
     *
     * @param tableInfo 验证请求（包含表名、全局配置、可选的自定义API文档）
     * @return API文档响应
     */
    @PostMapping("/steps/api-doc")
    public ApiDocument validateStep2ApiDoc(@RequestBody TableInfo tableInfo) {
        log.info("步骤2：验证API文档 - {}", tableInfo.getTableName());
        return stepService.validateStep2ApiDoc(tableInfo);
    }

    /**
     * 步骤3：预览生成的代码（包含导入分析和排序建议）
     * 在最终生成前预览代码，检查导入、排序等问题
     *
     * @param apiDocument 验证请求（包含表名、全局配置、可选的自定义API文档）
     * @return 代码预览响应（包含导入建议和排序建议）
     */
    @PostMapping("/steps/preview-code")
    public Step3CodePreviewResponse validateStep3PreviewCode(@RequestBody ApiDocument apiDocument) {
        log.info("步骤3：预览代码 - {}", apiDocument.getTableName());

        var codeGenProject = codeGenProjectClient.findById(BigInteger.ONE, CodeGenProjectConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);

        var codeFileSpecConfigsJson = "[{\"id\":1,\"templateName\":\"command.ftl\",\"moduleType\":\"SERVICE_API\",\"packageSuffix\":\".api.dto\",\"relativeDir\":\"api/dto\",\"fileNameSuffix\":\"Command\",\"fileTypeTag\":\"dto\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"创建\",\"更新\"],\"displayName\":\"创建/更新命令对象\",\"description\":\"用于创建和更新操作的DTO对象\"},{\"id\":2,\"templateName\":\"query.ftl\",\"moduleType\":\"SERVICE_API\",\"packageSuffix\":\".api.dto\",\"relativeDir\":\"api/dto\",\"fileNameSuffix\":\"Query\",\"fileTypeTag\":\"dto\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"分页查询\"],\"displayName\":\"分页查询对象\",\"description\":\"用于分页查询的DTO对象\"},{\"id\":3,\"templateName\":\"view.ftl\",\"moduleType\":\"SERVICE_API\",\"packageSuffix\":\".api.vo\",\"relativeDir\":\"api/vo\",\"fileNameSuffix\":\"View\",\"fileTypeTag\":\"vo\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"根据主键查询\",\"分页查询\"],\"displayName\":\"响应视图对象\",\"description\":\"用于API响应的视图对象\"},{\"id\":4,\"templateName\":\"service.ftl\",\"moduleType\":\"SERVICE_API\",\"packageSuffix\":\".api\",\"relativeDir\":\"api\",\"fileNameSuffix\":\"Service\",\"fileTypeTag\":\"service\",\"generationCondition\":\"HAS_ANY_ENDPOINT\",\"endpointNames\":null,\"displayName\":\"服务接口\",\"description\":\"定义业务服务的接口契约\"},{\"id\":5,\"templateName\":\"client.ftl\",\"moduleType\":\"SERVICE_API\",\"packageSuffix\":\".api\",\"relativeDir\":\"api\",\"fileNameSuffix\":\"Client\",\"fileTypeTag\":\"client\",\"generationCondition\":\"HAS_ANY_ENDPOINT\",\"endpointNames\":null,\"displayName\":\"Feign客户端\",\"description\":\"用于服务间通信的Feign客户端\"},{\"id\":6,\"templateName\":\"model.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".domain.model\",\"relativeDir\":\"domain/model\",\"fileNameSuffix\":\"\",\"fileTypeTag\":\"model\",\"generationCondition\":\"ALWAYS\",\"endpointNames\":null,\"displayName\":\"领域模型\",\"description\":\"领域层的核心实体模型\"},{\"id\":7,\"templateName\":\"mapper.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".api.assembler\",\"relativeDir\":\"api/assembler\",\"fileNameSuffix\":\"Mapper\",\"fileTypeTag\":\"mapper\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"创建\",\"更新\",\"根据主键查询\",\"分页查询\"],\"displayName\":\"数据转换器\",\"description\":\"用于DTO和领域模型之间的转换\"},{\"id\":8,\"templateName\":\"dao.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".infrastructure.persistence.dao\",\"relativeDir\":\"infrastructure/persistence/dao\",\"fileNameSuffix\":\"Dao\",\"fileTypeTag\":\"dao\",\"generationCondition\":\"ALWAYS\",\"endpointNames\":null,\"displayName\":\"数据访问层\",\"description\":\"直接操作数据库的DAO层\"},{\"id\":9,\"templateName\":\"repository.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".domain.repository\",\"relativeDir\":\"domain/repository\",\"fileNameSuffix\":\"Repository\",\"fileTypeTag\":\"repository\",\"generationCondition\":\"ALWAYS\",\"endpointNames\":null,\"displayName\":\"仓储接口\",\"description\":\"定义领域对象的仓储契约\"},{\"id\":10,\"templateName\":\"repository-impl.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".infrastructure.persistence\",\"relativeDir\":\"infrastructure/persistence\",\"fileNameSuffix\":\"MyBatisRepository\",\"fileTypeTag\":\"repository-impl\",\"generationCondition\":\"ALWAYS\",\"endpointNames\":null,\"displayName\":\"仓储实现\",\"description\":\"使用MyBatis实现的仓储\"},{\"id\":11,\"templateName\":\"resource.ftl\",\"moduleType\":\"SERVICE\",\"packageSuffix\":\".api\",\"relativeDir\":\"api\",\"fileNameSuffix\":\"Resource\",\"fileTypeTag\":\"resource\",\"generationCondition\":\"HAS_ANY_ENDPOINT\",\"endpointNames\":null,\"displayName\":\"REST资源\",\"description\":\"REST API资源类\"},{\"id\":12,\"templateName\":\"consoleCommand.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.dto\",\"relativeDir\":\"console/dto\",\"fileNameSuffix\":\"ConsoleCommand\",\"fileTypeTag\":\"dto\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"创建\",\"更新\"],\"displayName\":\"控制台命令对象\",\"description\":\"控制台层的创建/更新命令对象\"},{\"id\":13,\"templateName\":\"consoleQuery.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.dto\",\"relativeDir\":\"console/dto\",\"fileNameSuffix\":\"ConsoleQuery\",\"fileTypeTag\":\"dto\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"分页查询\"],\"displayName\":\"控制台查询对象\",\"description\":\"控制台层的分页查询对象\"},{\"id\":14,\"templateName\":\"consoleView.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.vo\",\"relativeDir\":\"console/vo\",\"fileNameSuffix\":\"ConsoleView\",\"fileTypeTag\":\"vo\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"根据主键查询\",\"分页查询\"],\"displayName\":\"控制台视图对象\",\"description\":\"控制台层的响应视图对象\"},{\"id\":15,\"templateName\":\"commandMapper.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.api.assembler\",\"relativeDir\":\"console/api/assembler\",\"fileNameSuffix\":\"CommandMapper\",\"fileTypeTag\":\"mapper\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"创建\",\"更新\"],\"displayName\":\"控制台命令转换器\",\"description\":\"控制台命令对象与领域模型的转换\"},{\"id\":16,\"templateName\":\"queryMapper.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.api.assembler\",\"relativeDir\":\"console/api/assembler\",\"fileNameSuffix\":\"QueryMapper\",\"fileTypeTag\":\"mapper\",\"generationCondition\":\"HAS_ENDPOINT\",\"endpointNames\":[\"分页查询\"],\"displayName\":\"控制台查询转换器\",\"description\":\"控制台查询对象与领域模型的转换\"},{\"id\":17,\"templateName\":\"consoleResource.ftl\",\"moduleType\":\"CONSOLE\",\"packageSuffix\":\".console.api\",\"relativeDir\":\"console/api\",\"fileNameSuffix\":\"Resource\",\"fileTypeTag\":\"resource\",\"generationCondition\":\"HAS_ANY_ENDPOINT\",\"endpointNames\":null,\"displayName\":\"控制台资源\",\"description\":\"控制台的REST API资源类\"},{\"id\":18,\"templateName\":\"enum.ftl\",\"moduleType\":\"ENUM\",\"packageSuffix\":\"\",\"relativeDir\":\"domain/enums\",\"fileNameSuffix\":\"\",\"fileTypeTag\":\"enum\",\"generationCondition\":\"ALWAYS\",\"endpointNames\":null,\"displayName\":\"枚举类\",\"description\":\"根据表字段动态生成的枚举类\"}]\n";
        List<CodeFileSpecConfig> codeFileSpecConfigs = JacksonMapper.getSharedInstance().fromCollectionJson(
                codeFileSpecConfigsJson,
                CodeFileSpecConfig.class
        );
        var codeGenModuleConfigs = CollectionUtils.transformToList(codeGenProject.getCodeGenProjectModules(),
                codeGenProjectModule -> new CodeGenModuleConfig(
                        codeGenProject.getBaseDirectory(),
                        codeGenProjectModule.getCodeGenModule().getModuleName(),
                        codeGenProjectModule.getCodeGenModule().getModuleType(),
                        CodeGenerateToolType.TEMPLATE,
                        codeFileSpecConfigs
                ));
        return stepService.validateStep3PreviewCode(apiDocument, codeGenModuleConfigs);
    }
}
