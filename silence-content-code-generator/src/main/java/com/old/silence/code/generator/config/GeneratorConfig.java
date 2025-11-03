package com.old.silence.code.generator.config;

/**
 * @author moryzang
 */
public class GeneratorConfig {

    private String dbUrl;
    private String username;
    private String password;
    private String persistencePackage;
    private Boolean isUseLombok;
    private String basePackage;

    private String apiDocOutputDir;
    private String interfaceOutputDir;
    private String serviceOutputDir;
    private String consoleOutputDir;
    private String rulesConfigPath;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersistencePackage() {
        return persistencePackage;
    }

    public void setPersistencePackage(String persistencePackage) {
        this.persistencePackage = persistencePackage;
    }

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

    public String getRulesConfigPath() {
        return rulesConfigPath;
    }

    public void setRulesConfigPath(String rulesConfigPath) {
        this.rulesConfigPath = rulesConfigPath;
    }
}
