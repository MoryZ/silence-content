package com.old.silence.content.code.generator.config;

/**
 * 表级别的配置
 * 支持从全局配置复制并客制化
 *
 * @author moryzang
 */
public class TableGeneratorConfig {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 渲染配置（如果不设置，使用全局配置）
     */
    private CodeGeneratorRenderConfig renderConfig;

    /**
     * 数据库配置（如果不设置，使用全局配置）
     */
    private DatabaseConfig databaseConfig;

    /**
     * 输出目录配置（如果不设置，使用全局配置）
     */
    private OutputDirectoryConfig outputDirectoryConfig;

    /**
     * 枚举配置列表
     */
    private java.util.List<EnumConfig> enumConfigs;

    /**
     * 从全局配置复制创建表级别配置
     */
    public static TableGeneratorConfig fromGlobalConfig(String tableName, GeneratorConfig globalConfig) {
        TableGeneratorConfig tableConfig = new TableGeneratorConfig();
        tableConfig.setTableName(tableName);
        
        // 复制渲染配置
        tableConfig.setRenderConfig(copyRenderConfig(globalConfig.getOrCreateRenderConfig()));
        
        // 复制数据库配置
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setDbUrl(globalConfig.getDbUrl());
        dbConfig.setUsername(globalConfig.getUsername());
        dbConfig.setPassword(globalConfig.getPassword());
        tableConfig.setDatabaseConfig(dbConfig);
        
        // 复制输出目录配置
        OutputDirectoryConfig outputConfig = new OutputDirectoryConfig();
        outputConfig.setApiDocOutputDir(globalConfig.getApiDocOutputDir());
        outputConfig.setEnumOutputDir(globalConfig.getEnumOutputDir());
        outputConfig.setInterfaceOutputDir(globalConfig.getInterfaceOutputDir());
        outputConfig.setServiceOutputDir(globalConfig.getServiceOutputDir());
        outputConfig.setConsoleOutputDir(globalConfig.getConsoleOutputDir());
        outputConfig.setFrontendOutputDir(globalConfig.getFrontendOutputDir());
        tableConfig.setOutputDirectoryConfig(outputConfig);
        
        // 复制枚举配置
        if (globalConfig.getEnumConfigs() != null) {
            tableConfig.setEnumConfigs(globalConfig.getEnumConfigs().stream()
                    .filter(ec -> tableName.equals(ec.getTableName()))
                    .collect(java.util.stream.Collectors.toList()));
        }
        
        return tableConfig;
    }

    private static CodeGeneratorRenderConfig copyRenderConfig(CodeGeneratorRenderConfig source) {
        CodeGeneratorRenderConfig copy = new CodeGeneratorRenderConfig();
        copy.setAuthorName(source.getAuthorName());
        copy.setApplicationName(source.getApplicationName());
        copy.setPrimaryType(source.getPrimaryType());
        copy.setUseLombok(source.isUseLombok());
        copy.setPersistencePackage(source.getPersistencePackage());
        return copy;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public CodeGeneratorRenderConfig getRenderConfig() {
        return renderConfig;
    }

    public void setRenderConfig(CodeGeneratorRenderConfig renderConfig) {
        this.renderConfig = renderConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public OutputDirectoryConfig getOutputDirectoryConfig() {
        return outputDirectoryConfig;
    }

    public void setOutputDirectoryConfig(OutputDirectoryConfig outputDirectoryConfig) {
        this.outputDirectoryConfig = outputDirectoryConfig;
    }

    public java.util.List<EnumConfig> getEnumConfigs() {
        return enumConfigs;
    }

    public void setEnumConfigs(java.util.List<EnumConfig> enumConfigs) {
        this.enumConfigs = enumConfigs;
    }

    /**
     * 数据库配置
     */
    public static class DatabaseConfig {
        private String dbUrl;
        private String username;
        private String password;

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
    }

    /**
     * 输出目录配置
     */
    public static class OutputDirectoryConfig {
        private String apiDocOutputDir;
        private String enumOutputDir;
        private String interfaceOutputDir;
        private String serviceOutputDir;
        private String consoleOutputDir;
        private String frontendOutputDir;

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

        public String getFrontendOutputDir() {
            return frontendOutputDir;
        }

        public void setFrontendOutputDir(String frontendOutputDir) {
            this.frontendOutputDir = frontendOutputDir;
        }
    }
}
