package com.old.silence.content.console.api.codegen;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeFileSpecClient;
import com.old.silence.content.api.CodeGenDatabaseClient;
import com.old.silence.content.api.CodeGenProjectClient;
import com.old.silence.content.api.dto.CodeFileSpecQuery;
import com.old.silence.content.code.generator.dto.CodeFileSpecConfig;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.StepService;
import com.old.silence.content.code.generator.vo.Step3CodePreviewResponse;
import com.old.silence.content.console.vo.CodeFileSpecConsoleView;
import com.old.silence.content.console.vo.CodeGenDatabaseConsoleView;
import com.old.silence.content.console.vo.CodeGenProjectConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

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
    private final CodeFileSpecClient codeFileSpecClient;

    public StepCodeGeneratorResource(StepService stepService,
                                     CodeGenDatabaseClient codeGenDatabaseClient,
                                     CodeGenProjectClient codeGenProjectClient,
                                     CodeFileSpecClient codeFileSpecClient) {
        this.stepService = stepService;
        this.codeGenDatabaseClient = codeGenDatabaseClient;
        this.codeGenProjectClient = codeGenProjectClient;
        this.codeFileSpecClient = codeFileSpecClient;
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
        var codeGenModuleConfigs = getCodeGenModuleConfigs();

        return stepService.validateStep3PreviewCode(apiDocument, codeGenModuleConfigs);
    }

    /**
     * 生成代码文件到指定目录
     * 实际生成代码，参数和预览逻辑一致，区别在于实际写入文件而不是返回预览
     *
     * @param apiDocument 代码生成请求（包含表名、全局配置、可选的自定义API文档）
     */
    @PostMapping("/steps/generate-code")
    public void generateCode(@RequestBody ApiDocument apiDocument) {
        log.info("生成代码 - {}", apiDocument.getTableName());

        var codeGenModuleConfigs = getCodeGenModuleConfigs();

        // 调用生成服务
        stepService.generateCode(apiDocument, codeGenModuleConfigs);

    }

    private List<CodeGenModuleConfig> getCodeGenModuleConfigs() {
        var codeGenProject = codeGenProjectClient.findById(BigInteger.ONE, CodeGenProjectConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);

        List<CodeFileSpecConsoleView> allCodeFileSpecConfigs = codeFileSpecClient.query(new CodeFileSpecQuery(), PageRequest.of(1, 50),
                CodeFileSpecConsoleView.class).getContent();


        var stringCollectionMap = CollectionUtils.groupingBy(allCodeFileSpecConfigs, CodeFileSpecConsoleView::getModuleType);
        return CollectionUtils.transformToList(codeGenProject.getCodeGenProjectModules(),
                codeGenProjectModule -> {
                    var codeFileSpecConfigs = CollectionUtils.transformToList(
                            stringCollectionMap.get(String.valueOf(codeGenProjectModule.getCodeGenModule().getModuleType()
                            )), Function.identity());
                    return new CodeGenModuleConfig(
                            codeGenProject.getBaseDirectory(),
                            codeGenProjectModule.getCodeGenModule().getModuleName(),
                            codeGenProjectModule.getCodeGenModule().getModulePackageName(),
                            codeGenProjectModule.getCodeGenModule().getModuleType(),
                            CodeGenerateToolType.TEMPLATE,
                            CollectionUtils.transformToList(codeFileSpecConfigs, codeFileSpecConsoleView -> new CodeFileSpecConfig(
                                    codeFileSpecConsoleView.getTemplateName(),
                                    codeFileSpecConsoleView.getModuleType(),
                                    codeFileSpecConsoleView.getPackageSuffix(),
                                    codeFileSpecConsoleView.getRelativeDir(),
                                    codeFileSpecConsoleView.getFileNameSuffix(),
                                    codeFileSpecConsoleView.getFileTypeTag(),
                                    codeFileSpecConsoleView.getGenerationCondition(),
                                            StringUtils.isNotBlank(codeFileSpecConsoleView.getEndpointNames()) ?
                                                    JacksonMapper.getSharedInstance().fromCollectionJson(codeFileSpecConsoleView.getEndpointNames(), String.class)
                                                    : List.of(),
                                    codeFileSpecConsoleView.getDisplayName(),
                                    codeFileSpecConsoleView.getDescription()
                            )
                            ));
                }
        );
    }
}
