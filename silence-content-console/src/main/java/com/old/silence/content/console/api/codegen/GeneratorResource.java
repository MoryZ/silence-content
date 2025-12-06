package com.old.silence.content.console.api.codegen;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.code.generator.dto.PreviewGenerationResult;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.BatchGenerationService;

/**
 * 代码生成器资源接口
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class GeneratorResource {

    private final BatchGenerationService batchGenerationService;

    public GeneratorResource(BatchGenerationService batchGenerationService) {
        this.batchGenerationService = batchGenerationService;
    }

    /**
     * 使用自定义API文档生成代码（支持复杂接口）
     * 适用于标准CRUD无法满足需求的场景
     *
     * @param request 批量生成请求，必须包含customApiDocs
     * @return 生成结果
     */
 /*   @PostMapping("/generate/batch-with-custom-api")
    public BatchGenerationResult generateBatchWithCustomApi(@RequestBody BatchGenerationRequest request) {
        return batchGenerationService.generateAPIWithCustomDoc(request.getConfig(), request.getCustomApiDocs());
    }*/

    /**
     * 预览将生成的代码文件名，按模块分组（interface/service/console/enum）
     */
    @PostMapping("/generate/preview")
    public PreviewGenerationResult preview(@RequestBody TableInfo tableInfo) {
        return batchGenerationService.previewFilenames(tableInfo);
    }

  /*  *//**
     * 预览代码内容（标准CRUD模式）
     * 返回所有将生成的文件及其内容，前端可根据module和fileType筛选
     *//*
    @PostMapping("/generate/preview-code")
    public CodePreviewResponse previewCode(
            @RequestBody CodePreviewRequest request) {
        return batchGenerationService.previewCode(request.getConfig(), request.getTableName());
    }

    *//**
     * 预览代码内容（自定义API模式）
     * 支持复杂接口场景，返回所有将生成的文件及其内容
     *//*
    @PostMapping("/generate/preview-code-with-custom-api")
    public CodePreviewResponse previewCodeWithCustomApi(
            @RequestBody CodePreviewRequest request) {
        return batchGenerationService.previewCodeWithCustomApi(request.getCustomApiDoc());
    }*/

}
