package com.old.silence.content.code.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.dto.CodeFileSpecConfig;
import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.NameConverterUtils;
import com.old.silence.content.code.generator.vo.CodePreviewResponse;
import com.old.silence.content.domain.enums.codegen.ModuleType;

import java.util.List;
import java.util.Map;

/**
 * 重构后的代码生成服务
 * 
 * <p>核心改进：
 * <ul>
 *   <li>使用 CodeFileSpec 元数据驱动生成，消除硬编码</li>
 *   <li>统一 generate 和 preview 逻辑，避免重复代码</li>
 *   <li>通过注册表集中管理所有文件规格</li>
 * </ul>
 * 
 * @author moryzang
 */
@Service
public class RefactoredCodeGeneratorService {
    
    private static final Logger log = LoggerFactory.getLogger(RefactoredCodeGeneratorService.class);
    
    /**
     * 生成代码文件（统一入口）
     * 
     * @param codeGenerator 代码生成器
     * @param tableInfo 表信息
     * @param apiDoc API文档
     * @param config 模块配置
     */
    public void generateCode(CodeGenerator codeGenerator,
                            TableInfo tableInfo,
                            ApiDocument apiDoc,
                            CodeGenModuleConfig config) throws Exception {
        
        log.info("开始生成 {} 层代码", config.getModuleType());
        
        // 获取该模块类型对应的所有文件规格
        List<CodeFileSpecConfig> specs = config.getCodeFileSpecConfigs();
        
        // 计算基础输出目录
        String baseOutputDir = config.getProjectPath() + "/" 
                             + config.getModulePath();
        
        // 遍历每个文件规格，生成文件
        for (CodeFileSpecConfig spec : specs) {
            generateFileBySpec(codeGenerator, tableInfo, baseOutputDir, spec);
        }
        
        log.info("完成生成 {} 层代码，共 {} 个文件", config.getModuleType(), specs.size());
    }
    
    /**
     * 预览代码（统一入口）
     * 
     * @param codeGenerator 代码生成器
     * @param apiDoc API文档
     * @param response 预览响应对象
     * @param config 模块配置
     */
    public void previewCode(CodeGenerator codeGenerator,
                           ApiDocument apiDoc,
                           CodePreviewResponse response,
                           CodeGenModuleConfig config) {
        
        TableInfo tableInfo = apiDoc.getTableInfo();
        
        // 获取该模块类型对应的所有文件规格
        List<CodeFileSpecConfig> specs = config.getCodeFileSpecConfigs();
        
        String className = NameConverterUtils.toCamelCase(tableInfo.getTableName(), true);

        // 计算基础输出目录
        String baseOutputDir = config.getProjectPath() + "/"
                + config.getModulePath();
        
        // 遍历每个文件规格，渲染预览
        for (CodeFileSpecConfig spec : specs) {
            if (spec.getModuleType().equals(String.valueOf(ModuleType.ENUM))) {
                if (tableInfo.getColumnInfos().stream().anyMatch(ColumnInfo::getEnum)) {
                    Map<String, Object> customerDataModel = Map.of("enumName", "TestEnum");
                    previewFileBySpec(codeGenerator, tableInfo, config.getModuleType(), baseOutputDir, className, spec, response, customerDataModel);
                }
            } else {
                previewFileBySpec(codeGenerator, tableInfo, config.getModuleType(), baseOutputDir, className, spec, response, null);
            }

        }
    }
    
    /**
     * 预览多模块代码（批量入口）
     * 
     * @param codeGenerator 代码生成器
     * @param apiDoc API文档
     * @param configs 多个模块配置
     * @return 合并后的预览响应
     */
    public CodePreviewResponse previewCode(CodeGenerator codeGenerator,
                                          ApiDocument apiDoc,
                                          List<CodeGenModuleConfig> configs) {
        
        CodePreviewResponse response = new CodePreviewResponse();
        response.setTableName(apiDoc.getTableName());
        
        log.info("开始预览多模块代码，共 {} 个模块", configs.size());
        
        // 为每个模块配置生成预览
        for (CodeGenModuleConfig config : configs) {
            previewCode(codeGenerator, apiDoc, response, config);
        }
        
        log.info("完成预览多模块代码，共 {} 个文件", response.getFiles().size());
        
        return response;
    }
    
    /**
     * 根据文件规格生成单个文件
     */
    private void generateFileBySpec(CodeGenerator codeGenerator,
                                    TableInfo tableInfo,
                                    String baseOutputDir,
                                    CodeFileSpecConfig spec) throws Exception {
        
        String outputDir = baseOutputDir + "/" + spec.getRelativeDir();
        
        log.debug("生成文件: template={}, outputDir={}, suffix={}", 
                 spec.getTemplateName(), outputDir, spec.getFileNameSuffix());
        
        codeGenerator.generateFile(
                tableInfo,
                outputDir,
                baseOutputDir,
                spec.getPackageSuffix(),
                spec.getTemplateName(),
                spec.getFileNameSuffix()
        );
    }
    
    /**
     * 根据文件规格预览单个文件
     */
    private void previewFileBySpec(CodeGenerator codeGenerator,
                                   TableInfo tableInfo,
                                   ModuleType moduleType,
                                   String baseOutputDir,
                                   String className,
                                   CodeFileSpecConfig spec,
                                   CodePreviewResponse response,
                                   Map<String, Object> customDataModel) {
        
        // 渲染模板内容
        String content = codeGenerator.renderTemplate(
                tableInfo,
                baseOutputDir,
                spec.getPackageSuffix(),
                spec.getTemplateName(),
                customDataModel
        );
        
        // 构建文件名
        String fileName = className + spec.getFileNameSuffix();
        
        // 构建相对路径
        String relativePath = spec.getRelativeDir() + "/" + fileName;
        
        // 添加到预览响应
        response.addFile(
                moduleType.name().toLowerCase(),
                fileName,
                relativePath,
                content,
                spec.getFileTypeTag()
        );
    }
}
