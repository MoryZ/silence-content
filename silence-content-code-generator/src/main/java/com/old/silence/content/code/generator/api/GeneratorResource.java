package com.old.silence.content.code.generator.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
import com.old.silence.content.code.generator.service.BatchGenerationService;

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

    public GeneratorResource(BatchGenerationService batchGenerationService) {
        this.batchGenerationService = batchGenerationService;
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
     * 生成API代码的核心方法
     *
     * @param config     生成配置
     * @param tableNames 要生成的表名列表，为null或空则生成所有表
     * @return 生成结果
     */
    // 所有业务逻辑移至Service，Resource不承载生成实现

    /**
     * 为单个表生成代码（标准CRUD模式）
     *
     * @param analyzer  SQL分析器
     * @param tableName 表名
     * @param config    生成配置
     */
    

    /**
     * 使用自定义API文档为单个表生成代码（独立方法，无耦合）
     *
     * @param analyzer     SQL分析器
     * @param tableName    表名
     * @param config       生成配置
     * @param customApiDoc 自定义API文档
     */
    

    /**
     * 使用自定义API文档生成代码
     *
     * @param config        生成配置
     * @param customApiDocs 自定义API文档映射 (tableName -> ApiDocument)
     * @return 生成结果
     */
    


}
