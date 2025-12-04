package com.old.silence.content.code.generator.llm;

import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

/**
 * 大模型服务接口
 * 支持多种大模型提供商：OpenAI、Claude、Ollama等
 *
 * @author moryzang
 */
public interface LLMService {

    /**
     * 生成代码
     *
     * @param prompt  提示词
     * @param context 生成上下文
     * @return 生成的代码
     */
    String generateCode(String prompt, CodeGenerationContext context);

    /**
     * 生成API文档（增强版）
     * 可以根据业务需求生成更丰富的API文档
     *
     * @param tableInfo    表信息
     * @param requirements 业务需求描述
     * @return API文档
     */
    ApiDocument generateApiDocument(TableInfo tableInfo, String requirements);

    /**
     * 生成前端代码
     *
     * @param apiDoc       接口文档
     * @param frontendType 前端框架类型（Vue、React等）
     * @param config       前端配置
     * @return 生成的前端代码
     */
    String generateFrontendCode(ApiDocument apiDoc, String frontendType, FrontendConfig config);

    /**
     * 代码生成上下文
     */
    class CodeGenerationContext {
        private TableInfo tableInfo;
        private ApiDocument apiDocument;
        private String layer; // CONSOLE, SERVICE, SERVICE_API, ENUM, FRONTEND
        private String basePackage;
        private String className;
        private String template; // 可选的代码模板示例

        // Getters and Setters
        public TableInfo getTableInfo() {
            return tableInfo;
        }

        public void setTableInfo(TableInfo tableInfo) {
            this.tableInfo = tableInfo;
        }

        public ApiDocument getApiDocument() {
            return apiDocument;
        }

        public void setApiDocument(ApiDocument apiDocument) {
            this.apiDocument = apiDocument;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }
    }

    /**
     * 前端配置
     */
    class FrontendConfig {
        private String framework; // Vue, React, Angular等
        private String uiLibrary; // Element Plus, Ant Design等
        private String apiBaseUrl;
        private boolean useTypeScript;
        private String outputPath;

        // Getters and Setters
        public String getFramework() {
            return framework;
        }

        public void setFramework(String framework) {
            this.framework = framework;
        }

        public String getUiLibrary() {
            return uiLibrary;
        }

        public void setUiLibrary(String uiLibrary) {
            this.uiLibrary = uiLibrary;
        }

        public String getApiBaseUrl() {
            return apiBaseUrl;
        }

        public void setApiBaseUrl(String apiBaseUrl) {
            this.apiBaseUrl = apiBaseUrl;
        }

        public boolean isUseTypeScript() {
            return useTypeScript;
        }

        public void setUseTypeScript(boolean useTypeScript) {
            this.useTypeScript = useTypeScript;
        }

        public String getOutputPath() {
            return outputPath;
        }

        public void setOutputPath(String outputPath) {
            this.outputPath = outputPath;
        }
    }
}

