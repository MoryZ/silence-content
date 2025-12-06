package com.old.silence.content.code.generator.config;

import java.util.List;

import com.old.silence.content.code.generator.enums.CodeGenerateStrategyType;

/**
 * @author moryzang
 */
public class GeneratorConfig {


    private Boolean isUseLombok;
    private String basePackage;

    private String apiDocOutputDir;
    private String enumOutputDir;
    private String interfaceOutputDir;
    private String serviceOutputDir;
    private String consoleOutputDir;

    private CodeGenerateStrategyType strategyType;

    /**
     * 枚举配置列表
     */
    private List<EnumConfig> enumConfigs;

    /**
     * 渲染配置（作者名、应用名、主键类型等）
     * 如果不设置，将使用默认配置
     */
    private CodeGeneratorRenderConfig renderConfig;


    public Boolean getUseLombok() {
        return isUseLombok;
    }

    public void setUseLombok(Boolean useLombok) {
        isUseLombok = useLombok;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getApiDocOutputDir() {
        return apiDocOutputDir;
    }

    public void setApiDocOutputDir(String apiDocOutputDir) {
        this.apiDocOutputDir = apiDocOutputDir;
    }

    public String getEnumOutputDir() {
        return enumOutputDir;
    }

    public void setEnumOutputDir(String enumOutputDir) {
        this.enumOutputDir = enumOutputDir;
    }

    public String getInterfaceOutputDir() {
        return interfaceOutputDir;
    }

    public void setInterfaceOutputDir(String interfaceOutputDir) {
        this.interfaceOutputDir = interfaceOutputDir;
    }

    public String getServiceOutputDir() {
        return serviceOutputDir;
    }

    public void setServiceOutputDir(String serviceOutputDir) {
        this.serviceOutputDir = serviceOutputDir;
    }

    public String getConsoleOutputDir() {
        return consoleOutputDir;
    }

    public void setConsoleOutputDir(String consoleOutputDir) {
        this.consoleOutputDir = consoleOutputDir;
    }

    public CodeGenerateStrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(CodeGenerateStrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public List<EnumConfig> getEnumConfigs() {
        return enumConfigs;
    }

    public void setEnumConfigs(List<EnumConfig> enumConfigs) {
        this.enumConfigs = enumConfigs;
    }

    public CodeGeneratorRenderConfig getRenderConfig() {
        return renderConfig;
    }

    public void setRenderConfig(CodeGeneratorRenderConfig renderConfig) {
        this.renderConfig = renderConfig;
    }

}
