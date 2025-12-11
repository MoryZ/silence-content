package com.old.silence.content.console.api.codegen;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.code.generator.dto.DatabaseConfig;
import com.old.silence.content.code.generator.dto.RequirementGenerationRequest;
import com.old.silence.content.code.generator.dto.SQLGenerationRequest;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.service.CodeGenerationOrchestrator;
import com.old.silence.content.console.vo.GenerationResult;

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
    public ApiDocument generateFromSQL(@RequestBody SQLGenerationRequest request) {
        return orchestrator.generateFromSQL(request.getSql());
    }

    @PostMapping("/generate/from-database")
    public GenerationResult generateFromDatabase(@RequestBody DatabaseConfig databaseConfig) {
        return orchestrator.generateFromDatabase(databaseConfig, null);
    }

    @PostMapping("/generate/from-api")
    public GenerationResult generateFromApiDoc(@RequestBody ApiDocument apiDocument) {
        return orchestrator.generateFromApiDocument(apiDocument);
    }

    @PostMapping("/generate/from-requirement")
    public GenerationResult generateFromRequirement(@RequestBody RequirementGenerationRequest request) {
        return orchestrator.generateFromRequirement(request.getRequirement());
    }

}
