package com.old.silence.content.code.generator.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.code.generator.dto.ApiDocGenerationRequest;
import com.old.silence.content.code.generator.dto.DatabaseGenerationRequest;
import com.old.silence.content.code.generator.dto.RequirementGenerationRequest;
import com.old.silence.content.code.generator.dto.SQLGenerationRequest;
import com.old.silence.content.code.generator.orchestrator.CodeGenerationOrchestrator;
import com.old.silence.content.code.generator.orchestrator.GenerationResult;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class EnhanceGeneratorResource {

    private final CodeGenerationOrchestrator orchestrator;

    public EnhanceGeneratorResource(CodeGenerationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping("/generate/from-sql")
    public GenerationResult generateFromSQL(@RequestBody SQLGenerationRequest request) {
        return orchestrator.generateFromSQL(request.getSql(), request.getConfig());
    }

    @PostMapping("/generate/from-database")
    public GenerationResult generateFromDatabase(@RequestBody DatabaseGenerationRequest request) {
        return orchestrator.generateFromDatabase(request.getTableName(), request.getConfig());
    }

    @PostMapping("/generate/from-api")
    public GenerationResult generateFromApiDoc(@RequestBody ApiDocGenerationRequest request) {
        return orchestrator.generateFromApiDocument(request.getApiDoc(), request.getConfig());
    }

    @PostMapping("/generate/from-requirements")
    public GenerationResult generateFromRequirements(@RequestBody RequirementGenerationRequest request) {
        return orchestrator.generateFromRequirement(request.getRequirement(), request.getConfig());
    }

}
