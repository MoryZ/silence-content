package com.old.silence.code.generator.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.code.generator.config.GeneratorConfig;
import com.old.silence.code.generator.dto.ApiDocGenerationRequest;
import com.old.silence.code.generator.dto.DatabaseGenerationRequest;
import com.old.silence.code.generator.dto.SQLGenerationRequest;
import com.old.silence.code.generator.orchestrator.CodeGenerationOrchestrator;

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

    @PostMapping("/from-sql")
    public ResponseEntity<?> generateFromSQL(@RequestBody SQLGenerationRequest request) {
        GeneratorConfig config = createConfig();
        CodeGenerationOrchestrator.GenerationResult result = orchestrator.generateFromSQL(request.getSql(), config);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/from-database")
    public ResponseEntity<?> generateFromDatabase(@RequestBody DatabaseGenerationRequest request) {
        GeneratorConfig config = createConfig();
        CodeGenerationOrchestrator.GenerationResult result = orchestrator.generateFromDatabase(request.getTableName(), config);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/from-api")
    public ResponseEntity<?> generateFromApiDoc(@RequestBody ApiDocGenerationRequest request) {
        GeneratorConfig config = createConfig();
        CodeGenerationOrchestrator.GenerationResult result = orchestrator.generateFromApiDocument(request.getApiDoc() , config);
        return ResponseEntity.ok(result);
    }

/*    @PostMapping("/from-requirements")
    public ResponseEntity<?> generateFromRequirements(@RequestBody DatabaseGenerationRequest request) {
        GeneratorConfig config = createConfig();
        CodeGenerationOrchestrator.GenerationResult result = orchestrator.generateFromDatabase(request.getTableName(), config);
        return ResponseEntity.ok(result);
    }*/


    /**
     * 创建配置,后续从参数中读取
     */
    private static GeneratorConfig createConfig() {
        GeneratorConfig config = new GeneratorConfig();
        config.setDbUrl("jdbc:mysql://localhost:3306/silence-content");
        config.setUsername("root");
        config.setPassword("admin123456");
        config.setPersistencePackage("jakarta");
        config.setUseLombok(false);
        config.setBasePackage("com.old.silence.content");

        config.setServiceOutputDir("silence-content-service/src/main/java");
        config.setInterfaceOutputDir("silence-content-service-api/src/main/java");
        config.setConsoleOutputDir("silence-content-console/src/main/java");
        config.setApiDocOutputDir("silence-content-api-docs");
        config.setFrontendOutputDir("silence-content-web");
        config.setStrategyType("LLM");
        return config;
    }

}
