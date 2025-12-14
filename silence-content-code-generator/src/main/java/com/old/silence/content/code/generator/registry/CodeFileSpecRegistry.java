package com.old.silence.content.code.generator.registry;

import com.old.silence.content.code.generator.constants.ApiEndpointNames;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.CodeFileSpec;
import com.old.silence.content.domain.enums.codegen.ModuleType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码文件规格注册表
 * 
 * <p>集中管理所有代码文件的生成规格，避免硬编码分散在各处
 * 
 * @author moryzang
 */
public class CodeFileSpecRegistry {
    
    private static final List<CodeFileSpec> ALL_SPECS = new ArrayList<>();
    
    static {
        registerServiceApiSpecs();
        registerServiceSpecs();
        registerConsoleSpecs();
        registerEnumSpecs();
    }
    
    /**
     * 注册 Service API 层文件规格
     */
    private static void registerServiceApiSpecs() {
        // Command DTO
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE_API)
                .templateName("command.ftl")
                .packageSuffix(".api.dto")
                .relativeDir("api/dto")
                .fileNameSuffix("Command")
                .fileTypeTag("dto")
                .whenHasEndpoint(ApiEndpointNames.CREATE, ApiEndpointNames.UPDATE)
                .build());
        
        // Query DTO
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE_API)
                .templateName("query.ftl")
                .packageSuffix(".api.dto")
                .relativeDir("api/dto")
                .fileNameSuffix("Query")
                .fileTypeTag("dto")
                .whenHasEndpoint(ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // View VO
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE_API)
                .templateName("view.ftl")
                .packageSuffix(".api.vo")
                .relativeDir("api/vo")
                .fileNameSuffix("View")
                .fileTypeTag("vo")
                .whenHasEndpoint(ApiEndpointNames.QUERY_BY_KEY, ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // Service Interface
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE_API)
                .templateName("service.ftl")
                .packageSuffix(".api")
                .relativeDir("api")
                .fileNameSuffix("Service")
                .fileTypeTag("service")
                .whenHasAnyEndpoint()
                .build());
        
        // Feign Client
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE_API)
                .templateName("client.ftl")
                .packageSuffix(".api")
                .relativeDir("api")
                .fileNameSuffix("Client")
                .fileTypeTag("client")
                .whenHasAnyEndpoint()
                .build());
    }
    
    /**
     * 注册 Service 层文件规格
     */
    private static void registerServiceSpecs() {
        // Domain Model (总是生成)
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("model.ftl")
                .packageSuffix(".domain.model")
                .relativeDir("domain/model")
                .fileNameSuffix("")
                .fileTypeTag("model")
                .always()
                .build());
        
        // Mapper (有 CRUD 操作时生成)
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("mapper.ftl")
                .packageSuffix(".api.assembler")
                .relativeDir("api/assembler")
                .fileNameSuffix("Mapper")
                .fileTypeTag("mapper")
                .whenHasEndpoint(ApiEndpointNames.CREATE, ApiEndpointNames.UPDATE, 
                                ApiEndpointNames.QUERY_BY_KEY, ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // DAO
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("dao.ftl")
                .packageSuffix(".infrastructure.persistence.dao")
                .relativeDir("infrastructure/persistence/dao")
                .fileNameSuffix("Dao")
                .fileTypeTag("dao")
                .always()
                .build());
        
        // Repository Interface
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("repository.ftl")
                .packageSuffix(".domain.repository")
                .relativeDir("domain/repository")
                .fileNameSuffix("Repository")
                .fileTypeTag("repository")
                .always()
                .build());
        
        // Repository Implementation
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("repository-impl.ftl")
                .packageSuffix(".infrastructure.persistence")
                .relativeDir("infrastructure/persistence")
                .fileNameSuffix("MyBatisRepository")
                .fileTypeTag("repository-impl")
                .always()
                .build());
        
        // REST Resource
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.SERVICE)
                .templateName("resource.ftl")
                .packageSuffix(".api")
                .relativeDir("api")
                .fileNameSuffix("Resource")
                .fileTypeTag("resource")
                .whenHasAnyEndpoint()
                .build());
    }
    
    /**
     * 注册 Console 层文件规格
     */
    private static void registerConsoleSpecs() {
        // Console Command
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("consoleCommand.ftl")
                .packageSuffix(".console.dto")
                .relativeDir("console/dto")
                .fileNameSuffix("ConsoleCommand")
                .fileTypeTag("dto")
                .whenHasEndpoint(ApiEndpointNames.CREATE, ApiEndpointNames.UPDATE)
                .build());
        
        // Console Query
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("consoleQuery.ftl")
                .packageSuffix(".console.dto")
                .relativeDir("console/dto")
                .fileNameSuffix("ConsoleQuery")
                .fileTypeTag("dto")
                .whenHasEndpoint(ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // Console View
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("consoleView.ftl")
                .packageSuffix(".console.vo")
                .relativeDir("console/vo")
                .fileNameSuffix("ConsoleView")
                .fileTypeTag("vo")
                .whenHasEndpoint(ApiEndpointNames.QUERY_BY_KEY, ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // Command Mapper
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("commandMapper.ftl")
                .packageSuffix(".console.api.assembler")
                .relativeDir("console/api/assembler")
                .fileNameSuffix("CommandMapper")
                .fileTypeTag("mapper")
                .whenHasEndpoint(ApiEndpointNames.CREATE, ApiEndpointNames.UPDATE)
                .build());
        
        // Query Mapper
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("queryMapper.ftl")
                .packageSuffix(".console.api.assembler")
                .relativeDir("console/api/assembler")
                .fileNameSuffix("QueryMapper")
                .fileTypeTag("mapper")
                .whenHasEndpoint(ApiEndpointNames.PAGINATED_QUERY)
                .build());
        
        // Console Resource
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.CONSOLE)
                .templateName("consoleResource.ftl")
                .packageSuffix(".console.api")
                .relativeDir("console/api")
                .fileNameSuffix("Resource")
                .fileTypeTag("resource")
                .whenHasAnyEndpoint()
                .build());
    }
    
    /**
     * 注册枚举文件规格
     */
    private static void registerEnumSpecs() {
        // Enum (特殊处理，根据字段动态生成)
        ALL_SPECS.add(CodeFileSpec.builder()
                .moduleType(ModuleType.ENUM)
                .templateName("enum.ftl")
                .packageSuffix("")
                .relativeDir("domain/enums")
                .fileNameSuffix("Enum")
                .fileTypeTag("enum")
                .always()
                .build());
    }
    
    /**
     * 获取所有文件规格
     */
    public static List<CodeFileSpec> getAllSpecs() {
        return new ArrayList<>(ALL_SPECS);
    }
    
    /**
     * 获取指定模块类型的所有文件规格
     */
    public static List<CodeFileSpec> getSpecsByModule(ModuleType moduleType) {
        return ALL_SPECS.stream()
                .filter(spec -> spec.getModuleType() == moduleType)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取指定模块类型且满足生成条件的文件规格
     */
    public static List<CodeFileSpec> getSpecsByModuleAndCondition(ModuleType moduleType, ApiDocument apiDoc) {
        if (apiDoc == null) {
            throw new IllegalArgumentException("ApiDocument 不能为 null");
        }
        return ALL_SPECS.stream()
                .filter(spec -> spec.getModuleType() == moduleType)
                .filter(spec -> spec.shouldGenerate(apiDoc))
                .collect(Collectors.toList());
    }
}
