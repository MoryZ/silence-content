package com.old.silence.content.code.generator.model;

import com.old.silence.content.domain.enums.codegen.ModuleType;

import java.util.function.Predicate;

/**
 * 代码文件规格定义
 * 
 * <p>描述一个代码文件生成所需的所有元数据：
 * <ul>
 *   <li>模板文件名</li>
 *   <li>所属模块</li>
 *   <li>包名后缀</li>
 *   <li>目录路径</li>
 *   <li>文件名后缀</li>
 *   <li>生成条件（基于 ApiDocument）</li>
 * </ul>
 * 
 * @author moryzang
 */
public class CodeFileSpec {
    
    /**
     * 模板文件名（如 command.ftl）
     */
    private final String templateName;
    
    /**
     * 所属模块类型
     */
    private final ModuleType moduleType;
    
    /**
     * 包名后缀（如 .api.dto）
     */
    private final String packageSuffix;
    
    /**
     * 相对输出目录（如 api/dto）
     */
    private final String relativeDir;
    
    /**
     * 文件名后缀（如 Command, Query, View）
     */
    private final String fileNameSuffix;
    
    /**
     * 文件类型标签（用于预览分组，如 dto, vo, service）
     */
    private final String fileTypeTag;
    
    /**
     * 生成条件断言（基于 ApiDocument 判断是否生成此文件）
     */
    private final Predicate<ApiDocument> generateCondition;
    
    private CodeFileSpec(Builder builder) {
        this.templateName = builder.templateName;
        this.moduleType = builder.moduleType;
        this.packageSuffix = builder.packageSuffix;
        this.relativeDir = builder.relativeDir;
        this.fileNameSuffix = builder.fileNameSuffix;
        this.fileTypeTag = builder.fileTypeTag;
        this.generateCondition = builder.generateCondition;
    }
    
    /**
     * 判断是否应该生成此文件
     */
    public boolean shouldGenerate(ApiDocument apiDoc) {
        if (generateCondition == null) {
            return true; // 默认总是生成
        }
        return generateCondition.test(apiDoc);
    }
    
    // Getters
    
    public String getTemplateName() {
        return templateName;
    }
    
    public ModuleType getModuleType() {
        return moduleType;
    }
    
    public String getPackageSuffix() {
        return packageSuffix;
    }
    
    public String getRelativeDir() {
        return relativeDir;
    }
    
    public String getFileNameSuffix() {
        return fileNameSuffix;
    }
    
    public String getFileTypeTag() {
        return fileTypeTag;
    }
    
    public Predicate<ApiDocument> getGenerateCondition() {
        return generateCondition;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String templateName;
        private ModuleType moduleType;
        private String packageSuffix = "";
        private String relativeDir = "";
        private String fileNameSuffix = "";
        private String fileTypeTag = "";
        private Predicate<ApiDocument> generateCondition;
        
        public Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }
        
        public Builder moduleType(ModuleType moduleType) {
            this.moduleType = moduleType;
            return this;
        }
        
        public Builder packageSuffix(String packageSuffix) {
            this.packageSuffix = packageSuffix;
            return this;
        }
        
        public Builder relativeDir(String relativeDir) {
            this.relativeDir = relativeDir;
            return this;
        }
        
        public Builder fileNameSuffix(String fileNameSuffix) {
            this.fileNameSuffix = fileNameSuffix;
            return this;
        }
        
        public Builder fileTypeTag(String fileTypeTag) {
            this.fileTypeTag = fileTypeTag;
            return this;
        }
        
        public Builder generateCondition(Predicate<ApiDocument> condition) {
            this.generateCondition = condition;
            return this;
        }
        
        /**
         * 设置"总是生成"条件
         */
        public Builder always() {
            this.generateCondition = null;
            return this;
        }
        
        /**
         * 设置"当有任何端点时生成"条件
         */
        public Builder whenHasAnyEndpoint() {
            this.generateCondition = apiDoc -> 
                apiDoc.getEndpoints() != null && !apiDoc.getEndpoints().isEmpty();
            return this;
        }
        
        /**
         * 设置"当有指定端点时生成"条件
         */
        public Builder whenHasEndpoint(String... endpointNames) {
            this.generateCondition = apiDoc -> {
                if (apiDoc.getEndpoints() == null || apiDoc.getEndpoints().isEmpty()) {
                    return false;
                }
                for (String name : endpointNames) {
                    if (apiDoc.getEndpoints().containsKey(name)) {
                        return true;
                    }
                }
                return false;
            };
            return this;
        }
        
        public CodeFileSpec build() {
            if (templateName == null || templateName.isEmpty()) {
                throw new IllegalArgumentException("templateName 不能为空");
            }
            if (moduleType == null) {
                throw new IllegalArgumentException("moduleType 不能为空");
            }
            return new CodeFileSpec(this);
        }
    }
    
    @Override
    public String toString() {
        return "CodeFileSpec{" +
                "templateName='" + templateName + '\'' +
                ", moduleType=" + moduleType +
                ", packageSuffix='" + packageSuffix + '\'' +
                ", relativeDir='" + relativeDir + '\'' +
                ", fileNameSuffix='" + fileNameSuffix + '\'' +
                ", fileTypeTag='" + fileTypeTag + '\'' +
                '}';
    }
}
