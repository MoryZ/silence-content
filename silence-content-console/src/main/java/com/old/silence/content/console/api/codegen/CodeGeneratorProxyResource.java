package com.old.silence.content.console.api.codegen;

import com.old.silence.content.code.generator.dto.BatchGenerationRequest;
import com.old.silence.content.code.generator.dto.BatchGenerationResult;
import com.old.silence.content.code.generator.dto.PreviewGenerationRequest;
import com.old.silence.content.code.generator.dto.PreviewGenerationResult;
import com.old.silence.content.code.generator.service.BatchGenerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/console/codegen")
public class CodeGeneratorProxyResource {

    private final BatchGenerationService batchGenerationService;

    public CodeGeneratorProxyResource(BatchGenerationService batchGenerationService) {
        this.batchGenerationService = batchGenerationService;
    }

    @PostMapping("/generate/batch")
    public BatchGenerationResult generateBatch(@RequestBody BatchGenerationRequest request) {
        if (request.getConfig() == null) {
            return BatchGenerationResult.failure("配置信息不能为空");
        }
        return batchGenerationService.generateAPI(request.getConfig(), request.getTableNames());
    }

    @PostMapping("/generate/batch-with-custom-api")
    public BatchGenerationResult generateBatchWithCustomApi(@RequestBody BatchGenerationRequest request) {
        if (request.getConfig() == null) {
            return BatchGenerationResult.failure("配置信息不能为空");
        }
        return batchGenerationService.generateAPIWithCustomDoc(request.getConfig(), request.getCustomApiDocs());
    }

    @PostMapping("/generate/preview")
    public PreviewGenerationResult preview(@RequestBody PreviewGenerationRequest request) {
        if (request.getConfig() == null) {
            return PreviewGenerationResult.of(java.util.Collections.emptyMap());
        }
        return batchGenerationService.previewFilenames(request.getConfig(), request.getTableNames());
    }
}
