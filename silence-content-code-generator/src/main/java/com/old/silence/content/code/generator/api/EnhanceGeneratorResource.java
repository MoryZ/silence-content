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
    public ResponseEntity<?> generateFromSQL(@RequestBody SQLGenerationRequest request) {
        GenerationResult result = orchestrator.generateFromSQL(request.getSql(), request.getConfig());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate/from-database")
    public ResponseEntity<?> generateFromDatabase(@RequestBody DatabaseGenerationRequest request) {
        GenerationResult result = orchestrator.generateFromDatabase(request.getTableName(), request.getConfig());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate/from-api")
    public ResponseEntity<?> generateFromApiDoc(@RequestBody ApiDocGenerationRequest request) {
        GenerationResult result = orchestrator.generateFromApiDocument(request.getApiDoc(), request.getConfig());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate/from-requirements")
    public ResponseEntity<?> generateFromRequirements(@RequestBody RequirementGenerationRequest request) {
        GenerationResult result = orchestrator.generateFromRequirement(request.getRequirement(), request.getConfig());
        return ResponseEntity.ok(result);
    }

}
