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
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.vo.Step3CodePreviewResponse;
import com.old.silence.content.console.service.ValidationService;
import com.old.silence.content.console.vo.CodeGenDatabaseConsoleView;
import com.old.silence.content.console.vo.CodeGenProjectConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;

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
    private final ValidationService validationService;
    private final CodeGenDatabaseClient codeGenDatabaseClient;
    private final CodeGenProjectClient codeGenProjectClient;

    public StepCodeGeneratorResource(ValidationService validationService,
                                     CodeGenDatabaseClient codeGenDatabaseClient,
                                     CodeGenProjectClient codeGenProjectClient) {
        this.validationService = validationService;
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
        var allTableInfos = validationService.validateStep1TableInfo(databaseConfig);

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
        return validationService.validateStep2ApiDoc(tableInfo);
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
        var codeGenModuleConfigs = CollectionUtils.transformToList(codeGenProject.getCodeGenProjectModules(),
                codeGenProjectModule -> new CodeGenModuleConfig(
                        codeGenProject.getBaseDirectory(),
                        codeGenProjectModule.getCodeGenModule().getModuleName(),
                        codeGenProjectModule.getCodeGenModule().getBasePackage(),
                        codeGenProjectModule.getCodeGenModule().getOutDirectory(),
                        codeGenProjectModule.getCodeGenModule().getModuleType(),
                        CodeGenerateToolType.TEMPLATE
                ));
        return validationService.validateStep3PreviewCode(apiDocument, codeGenModuleConfigs);
    }
}
