package com.old.silence.content.code.generator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.dto.BatchGenerationRequest;
import com.old.silence.content.code.generator.dto.BatchGenerationResult;
import com.old.silence.content.code.generator.dto.PreviewGenerationRequest;
import com.old.silence.content.code.generator.dto.PreviewGenerationResult;
import com.old.silence.content.code.generator.dto.ValidationRequest;
import com.old.silence.content.code.generator.dto.Step1TableInfoResponse;
import com.old.silence.content.code.generator.dto.Step2ApiDocResponse;
import com.old.silence.content.code.generator.dto.Step3CodePreviewResponse;
import com.old.silence.content.code.generator.service.BatchGenerationService;
import com.old.silence.content.code.generator.service.ValidationService;

/**
 * 代码生成器资源接口
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class GeneratorResource {

    private static final Logger log = LoggerFactory.getLogger(GeneratorResource.class);

    private final BatchGenerationService batchGenerationService;
    private final ValidationService validationService;

    public GeneratorResource(BatchGenerationService batchGenerationService,
                             ValidationService validationService) {
        this.batchGenerationService = batchGenerationService;
        this.validationService = validationService;
    }

    /**
     * 批量生成代码（标准CRUD接口）
     * 支持指定表名列表，如果不指定则生成所有表
     * API文档会从表结构自动生成标准的CRUD接口
     *
     * @param request 批量生成请求
     * @return 生成结果
     */
    @PostMapping("/generate/batch")
    public BatchGenerationResult generateBatch(@RequestBody BatchGenerationRequest request) {
        // 参数验证
        if (request.getConfig() == null) {
                return BatchGenerationResult.failure("配置信息不能为空");
        }

        if (!validateConfig(request.getConfig())) {
            return BatchGenerationResult.failure("配置信息不完整，请检查数据库连接和输出目录配置");
        }
        return batchGenerationService.generateAPI(request.getConfig(), request.getTableNames());
    }

    /**
     * 使用自定义API文档生成代码（支持复杂接口）
     * 适用于标准CRUD无法满足需求的场景
     *
     * @param request 批量生成请求，必须包含customApiDocs
     * @return 生成结果
     */
    @PostMapping("/generate/batch-with-custom-api")
    public BatchGenerationResult generateBatchWithCustomApi(@RequestBody BatchGenerationRequest request) {
        // 参数验证
        if (request.getConfig() == null) {
                return BatchGenerationResult.failure("配置信息不能为空");
        }

        if (!validateConfig(request.getConfig())) {
                return BatchGenerationResult.failure("配置信息不完整，请检查数据库连接和输出目录配置");
        }

        if (request.getCustomApiDocs() == null || request.getCustomApiDocs().isEmpty()) {
            return BatchGenerationResult.failure("自定义API文档不能为空");
        }
        return batchGenerationService.generateAPIWithCustomDoc(request.getConfig(), request.getCustomApiDocs());
    }

    /**
     * 预览将生成的代码文件名，按模块分组（interface/service/console/enum）
     */
    @PostMapping("/generate/preview")
    public PreviewGenerationResult preview(@RequestBody PreviewGenerationRequest request) {
        if (request.getConfig() == null) {
            return PreviewGenerationResult.of(java.util.Collections.emptyMap());
        }
        if (!validateConfig(request.getConfig())) {
            return PreviewGenerationResult.of(java.util.Collections.emptyMap());
        }
        return batchGenerationService.previewFilenames(request.getConfig(), request.getTableNames());
    }

    /**
     * 预览代码内容（标准CRUD模式）
     * 返回所有将生成的文件及其内容，前端可根据module和fileType筛选
     */
    @PostMapping("/generate/preview-code")
    public com.old.silence.content.code.generator.dto.CodePreviewResponse previewCode(
            @RequestBody com.old.silence.content.code.generator.dto.CodePreviewRequest request) {
        if (request.getConfig() == null || request.getTableName() == null) {
            throw new IllegalArgumentException("配置和表名不能为空");
        }
        if (!validateConfig(request.getConfig())) {
            throw new IllegalArgumentException("配置信息不完整");
        }
        return batchGenerationService.previewCode(request.getConfig(), request.getTableName());
    }

    /**
     * 预览代码内容（自定义API模式）
     * 支持复杂接口场景，返回所有将生成的文件及其内容
     */
    @PostMapping("/generate/preview-code-with-custom-api")
    public com.old.silence.content.code.generator.dto.CodePreviewResponse previewCodeWithCustomApi(
            @RequestBody com.old.silence.content.code.generator.dto.CodePreviewRequest request) {
        if (request.getConfig() == null || request.getTableName() == null || request.getCustomApiDoc() == null) {
            throw new IllegalArgumentException("配置、表名和自定义API文档不能为空");
        }
        if (!validateConfig(request.getConfig())) {
            throw new IllegalArgumentException("配置信息不完整");
        }
        return batchGenerationService.previewCodeWithCustomApi(
                request.getConfig(), request.getTableName(), request.getCustomApiDoc());
    }

    /**
     * 验证配置
     */
    private boolean validateConfig(GeneratorConfig config) {
        return StringUtils.hasText(config.getDbUrl())
                && StringUtils.hasText(config.getUsername())
                && StringUtils.hasText(config.getBasePackage())
                && (StringUtils.hasText(config.getServiceOutputDir())
                || StringUtils.hasText(config.getInterfaceOutputDir())
                || StringUtils.hasText(config.getConsoleOutputDir()));
    }


    /**
     * 步骤1：查看表结构信息
     * 在生成代码前，先验证表结构是否正确
     *
     * @param request 验证请求（包含表名和全局配置）
     * @return 表信息响应
     */
    @PostMapping("/validate/step1-table-info")
    public Step1TableInfoResponse validateStep1TableInfo(@RequestBody ValidationRequest request) {
        if (request.getTableName() == null || request.getGlobalConfig() == null) {
            throw new IllegalArgumentException("表名和配置不能为空");
        }
        if (!validateConfig(request.getGlobalConfig())) {
            throw new IllegalArgumentException("配置信息不完整");
        }
        log.info("步骤1：验证表信息 - {}", request.getTableName());
        return validationService.validateStep1TableInfo(request.getTableName(), request.getGlobalConfig());
    }

    /**
     * 步骤2：查看生成的API文档
     * 验证生成的API文档是否符合预期
     *
     * @param request 验证请求（包含表名、全局配置、可选的自定义API文档）
     * @return API文档响应
     */
    @PostMapping("/validate/step2-api-doc")
    public Step2ApiDocResponse validateStep2ApiDoc(@RequestBody ValidationRequest request) {
        if (request.getTableName() == null || request.getGlobalConfig() == null) {
            throw new IllegalArgumentException("表名和配置不能为空");
        }
        if (!validateConfig(request.getGlobalConfig())) {
            throw new IllegalArgumentException("配置信息不完整");
        }
        log.info("步骤2：验证API文档 - {}", request.getTableName());
        return validationService.validateStep2ApiDoc(
                request.getTableName(),
                request.getGlobalConfig(),
                request.getCustomApiDoc()
        );
    }

    /**
     * 步骤3：预览生成的代码（包含导入分析和排序建议）
     * 在最终生成前预览代码，检查导入、排序等问题
     *
     * @param request 验证请求（包含表名、全局配置、可选的自定义API文档）
     * @return 代码预览响应（包含导入建议和排序建议）
     */
    @PostMapping("/validate/step3-preview-code")
    public Step3CodePreviewResponse validateStep3PreviewCode(@RequestBody ValidationRequest request) {
        if (request.getTableName() == null || request.getGlobalConfig() == null) {
            throw new IllegalArgumentException("表名和配置不能为空");
        }
        if (!validateConfig(request.getGlobalConfig())) {
            throw new IllegalArgumentException("配置信息不完整");
        }
        log.info("步骤3：预览代码 - {}", request.getTableName());
        return validationService.validateStep3PreviewCode(
                request.getTableName(),
                request.getGlobalConfig(),
                request.getCustomApiDoc()
        );
    }


}
